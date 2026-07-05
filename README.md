# Customer Support FAQ AI Agent

Spring Boot customer support assistant with a simple HTML/CSS/JavaScript frontend, PostgreSQL FAQ storage, Swagger API documentation, chat history, unanswered-question tracking, and optional OpenAI-powered real AI chat.

## Core Features

- Chat interface where a customer enters a question and receives an answer.
- FAQ management: add, edit, delete, list, search, and filter FAQs.
- FAQ fields: question, answer, category, status, plus optional language, priority, keywords, and feedback counters.
- FAQ matching agent that finds the closest active FAQ.
- If FAQ mode cannot find a suitable answer, it returns:

```text
Sorry, I could not find an answer for your question. Please contact support.
```

- Conversation history saves user questions, AI responses, matched FAQ, confidence score, mode, language, and support escalation link.
- Admin dashboard for FAQs, chat history, unanswered questions, tickets, feedback, and statistics.
- Swagger/OpenAPI documentation.

## Bonus Features Included

- English and Arabic support.
- FAQ categories and filtering.
- Suggested follow-up questions in FAQ mode.
- Confidence score stored in history.
- Escalation of unanswered questions to WhatsApp/human support.
- Optional Real AI mode using OpenAI or Google Gemini.
- Real AI answers that do not match an existing FAQ are saved back into the FAQ database when useful.

## Technology Stack

- Java 21
- Spring Boot 3.5.3
- Maven
- PostgreSQL
- Spring Web
- Spring Data JPA
- Spring WebFlux `WebClient`
- Validation
- Swagger/OpenAPI via Springdoc
- Simple frontend: HTML, CSS, JavaScript
- Optional OpenAI API

## Agent Modes

### FAQ Database Agent

Endpoint: `POST /api/chat/ask`

This mode is database-only. It searches active FAQs in PostgreSQL and returns the closest answer if confidence is high enough. If no match exists, it saves the unanswered question and returns the support fallback.

### Real AI Agent

Endpoint: `POST /api/ai/ask`

This mode answers more like a chat assistant. It can use the FAQ database as context, but it is not limited to exact FAQ matches. API keys stay on the backend. If the running app does not receive the configured provider key, simple greetings still work, and deeper open-ended questions return a clear configuration message.

## Database Setup

Create PostgreSQL database:

```sql
CREATE DATABASE support_faq_db;
```

Schema reference:

```text
docs/database-schema.sql
```

Hibernate is configured to update tables automatically:

```properties
spring.jpa.hibernate.ddl-auto=update
```

## Environment Variables

PowerShell example:

```powershell
$env:POSTGRES_PASSWORD="your_postgres_password"
$env:AI_PROVIDER="OPENAI"
$env:OPENAI_API_KEY="your_openai_api_key"
```

For Google AI Studio / Gemini instead:

```powershell
$env:POSTGRES_PASSWORD="your_postgres_password"
$env:AI_PROVIDER="GEMINI"
$env:GEMINI_API_KEY="your_gemini_api_key"
```

`OPENAI_API_KEY` or `GEMINI_API_KEY` is optional for FAQ mode, but one configured provider key is required for full Real AI answers.

## Run The Project

```powershell
$env:JAVA_HOME="C:\Users\a6711\.jdks\ms-21.0.11"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

Default configured port in this workspace:

```text
http://localhost:8081/index.html
```

If you change `server.port` back to `8080`, use:

```text
http://localhost:8080/index.html
```

## Swagger / OpenAPI

Swagger UI:

```text
http://localhost:8081/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8081/api-docs
```

## Main REST APIs

### FAQ Management

- `POST /api/faqs`
- `GET /api/faqs`
- `GET /api/faqs/{id}`
- `PUT /api/faqs/{id}`
- `DELETE /api/faqs/{id}`
- `GET /api/faqs/search?keyword=...`
- `GET /api/faqs/category/{category}`
- `GET /api/faqs/status/{status}`
- `GET /api/faqs/language/{language}`

### Chat

- `POST /api/chat/ask`
- `GET /api/chat/history`
- `DELETE /api/chat/history`

### Real AI

- `POST /api/ai/ask`
- `GET /api/ai/modes`

### Admin Dashboard

- `GET /api/dashboard/stats`
- `GET /api/dashboard/history`
- `GET /api/dashboard/unanswered`
- `PUT /api/dashboard/unanswered/{id}/review`
- `PUT /api/dashboard/unanswered/{id}/resolve`
- `POST /api/dashboard/unanswered/{id}/create-faq`
- `POST /api/dashboard/unanswered/{id}/ticket`

### Other Admin APIs

- `GET/POST/PUT/DELETE /api/categories`
- `GET/POST/DELETE /api/tickets`
- `POST /api/feedback`
- `GET /api/feedback`
- `GET /api/feedback/summary`

## Example Ask Question Request

```json
{
  "question": "How can I reset my password?",
  "language": "EN",
  "mode": "FAQ_ONLY",
  "sessionId": "demo-session",
  "inputType": "TEXT"
}
```

## Demo Flow

1. Open the frontend.
2. Ask a known FAQ in FAQ Database Agent mode.
3. Ask an unknown question in FAQ mode and confirm fallback + support escalation.
4. Switch to Real AI mode and ask a general question.
5. Open FAQ Management and confirm saved/generated FAQs.
6. Open Chat History and confirm questions/responses were stored.
7. Open Unanswered Questions and convert one into an FAQ.
8. Open Swagger and test the REST APIs.

## Notes

- Do not put API keys in frontend files.
- The frontend is served from `src/main/resources/static`.
- Sample FAQ/category data is inserted automatically when missing.
- For production, add authentication and role-based admin access.
