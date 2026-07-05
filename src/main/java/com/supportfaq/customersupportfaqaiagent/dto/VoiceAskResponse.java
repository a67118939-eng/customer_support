package com.supportfaq.customersupportfaqaiagent.dto;

public class VoiceAskResponse {

    private String transcript;
    private String answer;
    private double confidenceScore;
    private boolean answered;
    private String audioBase64;

    public VoiceAskResponse() {
    }

    public VoiceAskResponse(String transcript, String answer, double confidenceScore, boolean answered, String audioBase64) {
        this.transcript = transcript;
        this.answer = answer;
        this.confidenceScore = confidenceScore;
        this.answered = answered;
        this.audioBase64 = audioBase64;
    }

    public String getTranscript() {
        return transcript;
    }

    public String getAnswer() {
        return answer;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public boolean isAnswered() {
        return answered;
    }

    public String getAudioBase64() {
        return audioBase64;
    }
}
