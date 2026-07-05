package com.supportfaq.customersupportfaqaiagent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportfaq.customersupportfaqaiagent.dto.AiAskRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AiAskResponse;
import com.supportfaq.customersupportfaqaiagent.dto.VoiceAskResponse;
import com.supportfaq.customersupportfaqaiagent.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Service
public class VoiceService {

    private static final String OPENAI_BASE_URL = "https://api.openai.com/v1";
    private static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com";

    private final RealAiAgentService realAiAgentService;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Value("${openai.api.key:}")
    private String openAiApiKey;

    @Value("${openai.transcription.model:gpt-4o-mini-transcribe}")
    private String transcriptionModel;

    @Value("${openai.tts.model:gpt-4o-mini-tts}")
    private String ttsModel;

    @Value("${openai.tts.voice:coral}")
    private String ttsVoice;

    @Value("${app.ai.provider:OPENAI}")
    private String aiProvider;

    @Value("${google.gemini.api-key:}")
    private String geminiApiKey;

    @Value("${google.gemini.model:gemini-1.5-flash}")
    private String geminiModel;

    @Value("${app.security.voice.max-upload-bytes:5242880}")
    private long maxUploadBytes;

    public VoiceService(RealAiAgentService realAiAgentService, ObjectMapper objectMapper) {
        this.realAiAgentService = realAiAgentService;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    public VoiceAskResponse askWithVoice(MultipartFile audio) {
        return askWithVoice(audio, "REAL_AI", "EN", null, false);
    }

    public VoiceAskResponse askWithVoice(
            MultipartFile audio,
            String mode,
            String language,
            String sessionId,
            boolean unlimitedTokens
    ) {
        try {
            if (!hasVoiceProviderKey()) {
                return new VoiceAskResponse(
                        "",
                        "Voice chat requires a configured OpenAI or Gemini API key on the backend.",
                        0.0,
                        false,
                        ""
                );
            }

            validateAudio(audio);

            String transcript = transcribe(audio);

            if (transcript == null || transcript.isBlank()) {
                return new VoiceAskResponse(
                        "",
                        "I could not detect speech in that voice message. Please try again.",
                        0.0,
                        false,
                        ""
                );
            }

            AiAskRequest request = new AiAskRequest();
            request.setQuestion(transcript);
            request.setMode(mode == null || mode.isBlank() ? "REAL_AI" : mode);
            request.setLanguage(language == null || language.isBlank() ? "EN" : language);
            request.setSessionId(sessionId);
            request.setInputType("VOICE");

            AiAskResponse chatResponse = realAiAgentService.ask(request, unlimitedTokens);

            String audioBase64 = generateSpeechSafely(chatResponse.getAnswer());

            return new VoiceAskResponse(
                    transcript,
                    chatResponse.getAnswer(),
                    chatResponse.getConfidenceScore(),
                    chatResponse.isAnswered(),
                    audioBase64
            );

        } catch (BadRequestException exception) {
            return new VoiceAskResponse(
                    "",
                    exception.getMessage(),
                    0.0,
                    false,
                    ""
            );

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            return new VoiceAskResponse(
                    "",
                    "Voice request was interrupted. Please try again.",
                    0.0,
                    false,
                    ""
            );

        } catch (IOException exception) {
            return new VoiceAskResponse(
                    "",
                    "Could not process the uploaded audio file. Please try again.",
                    0.0,
                    false,
                    ""
            );

        } catch (RuntimeException exception) {
            return new VoiceAskResponse(
                    "",
                    "Voice service failed: " + exception.getMessage(),
                    0.0,
                    false,
                    ""
            );
        }
    }

    private String transcribe(MultipartFile audio) throws IOException, InterruptedException {
        if (useGeminiProvider() && geminiApiKey != null && !geminiApiKey.isBlank()) {
            return transcribeWithGemini(audio);
        }

        return transcribeWithOpenAi(audio);
    }

    private String transcribeWithOpenAi(MultipartFile audio) throws IOException, InterruptedException {
        if (openAiApiKey == null || openAiApiKey.isBlank()) {
            return "";
        }

        String boundary = "----faq-agent-" + UUID.randomUUID();
        byte[] body = buildMultipartBody(boundary, audio);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_BASE_URL + "/audio/transcriptions"))
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() >= 400) {
            throw new RuntimeException(
                    "OpenAI transcription request failed with status "
                            + response.statusCode()
                            + ": "
                            + response.body()
            );
        }

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode textNode = root.get("text");

        return textNode == null ? "" : textNode.asText();
    }

    private String transcribeWithGemini(MultipartFile audio) throws IOException, InterruptedException {
        byte[] audioBytes = audio.getBytes();
        String mimeType = geminiContentType(audio);

        String uploadUrl = startGeminiUpload(audioBytes.length, mimeType);

        JsonNode uploadResponse = uploadGeminiFile(uploadUrl, audioBytes, mimeType);
        JsonNode fileNode = uploadResponse.path("file");

        String fileUri = fileNode.path("uri").asText();
        String uploadedMimeType = fileNode.path("mimeType").asText(mimeType);

        if (fileUri == null || fileUri.isBlank()) {
            throw new RuntimeException("Gemini audio upload did not return a file URI.");
        }

        Map<String, Object> body = Map.of(
                "contents",
                java.util.List.of(
                        Map.of(
                                "role",
                                "user",
                                "parts",
                                java.util.List.of(
                                        Map.of(
                                                "text",
                                                "Transcribe the spoken user message. Return only the transcript text."
                                        ),
                                        Map.of(
                                                "fileData",
                                                Map.of(
                                                        "mimeType",
                                                        uploadedMimeType,
                                                        "fileUri",
                                                        fileUri
                                                )
                                        )
                                )
                        )
                ),
                "generationConfig",
                Map.of("temperature", 0)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(
                        URI.create(
                                GEMINI_BASE_URL
                                        + "/v1beta/models/"
                                        + geminiModel.trim()
                                        + ":generateContent?key="
                                        + geminiApiKey
                        )
                )
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() >= 400) {
            throw new RuntimeException(
                    "Gemini transcription request failed with status "
                            + response.statusCode()
                            + ": "
                            + response.body()
            );
        }

        JsonNode root = objectMapper.readTree(response.body());

        return extractGeminiText(root).trim();
    }

    private String startGeminiUpload(
            int byteCount,
            String mimeType
    ) throws IOException, InterruptedException {

        Map<String, Object> metadata = Map.of(
                "file",
                Map.of("display_name", "voice-message")
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_BASE_URL + "/upload/v1beta/files?key=" + geminiApiKey))
                .header("X-Goog-Upload-Protocol", "resumable")
                .header("X-Goog-Upload-Command", "start")
                .header("X-Goog-Upload-Header-Content-Length", String.valueOf(byteCount))
                .header("X-Goog-Upload-Header-Content-Type", mimeType)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(metadata)))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() >= 400) {
            throw new RuntimeException(
                    "Gemini upload initialization failed with status "
                            + response.statusCode()
                            + ": "
                            + response.body()
            );
        }

        return response.headers()
                .firstValue("x-goog-upload-url")
                .orElseThrow(() -> new RuntimeException("Gemini upload URL was not returned."));
    }

    private JsonNode uploadGeminiFile(
            String uploadUrl,
            byte[] bytes,
            String mimeType
    ) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadUrl))
                .header("Content-Type", mimeType)
                .header("X-Goog-Upload-Offset", "0")
                .header("X-Goog-Upload-Command", "upload, finalize")
                .POST(HttpRequest.BodyPublishers.ofByteArray(bytes))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() >= 400) {
            throw new RuntimeException(
                    "Gemini audio upload failed with status "
                            + response.statusCode()
                            + ": "
                            + response.body()
            );
        }

        return objectMapper.readTree(response.body());
    }

    private String extractGeminiText(JsonNode root) {
        StringBuilder text = new StringBuilder();

        JsonNode parts = root
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts");

        if (parts.isArray()) {
            for (JsonNode part : parts) {
                if (part.hasNonNull("text")) {
                    text.append(part.get("text").asText());
                }
            }
        }

        return text.toString();
    }

    private String generateSpeechSafely(String text) {
        if (!useOpenAiProvider()) {
            return "";
        }

        if (openAiApiKey == null || openAiApiKey.isBlank()) {
            return "";
        }

        try {
            return textToSpeech(text);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return "";
        } catch (IOException | RuntimeException exception) {
            return "";
        }
    }

    private String textToSpeech(String text) throws IOException, InterruptedException {
        Map<String, String> requestBody = Map.of(
                "model",
                ttsModel,
                "voice",
                ttsVoice,
                "input",
                text == null || text.isBlank() ? "No answer available." : text
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_BASE_URL + "/audio/speech"))
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                .build();

        HttpResponse<byte[]> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofByteArray()
        );

        if (response.statusCode() >= 400) {
            throw new RuntimeException(
                    "OpenAI text-to-speech request failed with status "
                            + response.statusCode()
            );
        }

        return Base64.getEncoder().encodeToString(response.body());
    }

    private byte[] buildMultipartBody(
            String boundary,
            MultipartFile audio
    ) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        writeTextPart(outputStream, boundary, "model", transcriptionModel);
        writeFilePart(
                outputStream,
                boundary,
                "file",
                defaultFilename(audio.getOriginalFilename()),
                audio
        );

        outputStream.write(
                ("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8)
        );

        return outputStream.toByteArray();
    }

    private void writeTextPart(
            ByteArrayOutputStream outputStream,
            String boundary,
            String name,
            String value
    ) throws IOException {

        outputStream.write(
                ("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8)
        );

        outputStream.write(
                ("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        outputStream.write(
                (value + "\r\n").getBytes(StandardCharsets.UTF_8)
        );
    }

    private void writeFilePart(
            ByteArrayOutputStream outputStream,
            String boundary,
            String name,
            String filename,
            MultipartFile file
    ) throws IOException {

        outputStream.write(
                ("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8)
        );

        outputStream.write(
                ("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename + "\"\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        outputStream.write(
                ("Content-Type: " + contentType(file) + "\r\n\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        outputStream.write(file.getBytes());
        outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
    }

    private String contentType(MultipartFile file) {
        if (file == null || file.getContentType() == null || file.getContentType().isBlank()) {
            return "audio/webm";
        }

        return sanitizeHeaderValue(file.getContentType());
    }

    private String geminiContentType(MultipartFile file) {
        String contentType = contentType(file).toLowerCase();

        if (contentType.contains("ogg")) {
            return "audio/ogg";
        }

        if (contentType.contains("wav")) {
            return "audio/wav";
        }

        if (contentType.contains("mpeg") || contentType.contains("mp3")) {
            return "audio/mp3";
        }

        if (contentType.contains("aac")) {
            return "audio/aac";
        }

        if (contentType.contains("flac")) {
            return "audio/flac";
        }

        if (contentType.contains("webm")) {
            return "audio/webm";
        }

        return contentType;
    }

    private String defaultFilename(String filename) {
        if (filename == null || filename.isBlank()) {
            return "audio.webm";
        }

        return sanitizeHeaderValue(filename).replaceAll("[\\\\/]", "_");
    }

    private void validateAudio(MultipartFile audio) {
        if (audio == null || audio.isEmpty()) {
            throw new BadRequestException("Audio file is required.");
        }

        if (audio.getSize() > maxUploadBytes) {
            throw new BadRequestException("Audio file is too large.");
        }

        String contentType = contentType(audio).toLowerCase();

        boolean supported =
                contentType.startsWith("audio/")
                        || contentType.startsWith("video/webm")
                        || "application/octet-stream".equals(contentType);

        if (!supported) {
            throw new BadRequestException(
                    "Audio file type is not supported: " + contentType
            );
        }
    }

    private String sanitizeHeaderValue(String value) {
        return value.replaceAll("[\\r\\n\"]", "_").trim();
    }

    private boolean useGeminiProvider() {
        return "GEMINI".equalsIgnoreCase(aiProvider)
                || "GOOGLE".equalsIgnoreCase(aiProvider);
    }

    private boolean useOpenAiProvider() {
        return !useGeminiProvider();
    }

    private boolean hasVoiceProviderKey() {
        if (useGeminiProvider()) {
            return geminiApiKey != null && !geminiApiKey.isBlank();
        }

        return openAiApiKey != null && !openAiApiKey.isBlank();
    }
}