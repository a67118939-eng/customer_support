-- Customer Support FAQ AI Agent database schema
-- PostgreSQL database: support_faq_db
-- Hibernate can create/update these tables automatically with spring.jpa.hibernate.ddl-auto=update.

CREATE TABLE IF NOT EXISTS faqs (
    id BIGSERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    category VARCHAR(255),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    language VARCHAR(20) DEFAULT 'EN',
    keywords TEXT,
    priority VARCHAR(50) DEFAULT 'NORMAL',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    match_count BIGINT DEFAULT 0,
    helpful_count BIGINT DEFAULT 0,
    unhelpful_count BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS chat_history (
    id BIGSERIAL PRIMARY KEY,
    session_id VARCHAR(255),
    user_question TEXT NOT NULL,
    ai_response TEXT NOT NULL,
    matched_faq_id BIGINT,
    confidence_score DOUBLE PRECISION DEFAULT 0,
    answered BOOLEAN DEFAULT FALSE,
    language VARCHAR(20) DEFAULT 'EN',
    input_type VARCHAR(50) DEFAULT 'TEXT',
    ai_mode VARCHAR(50) DEFAULT 'FAQ_ONLY',
    whatsapp_support_url TEXT,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS unanswered_questions (
    id BIGSERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'NEW',
    language VARCHAR(20) DEFAULT 'EN',
    category_guess VARCHAR(255),
    admin_note TEXT,
    converted_to_faq BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    reviewed_at TIMESTAMP,
    resolved_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name_english VARCHAR(255) NOT NULL,
    name_arabic VARCHAR(255),
    description TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS support_tickets (
    id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    subject VARCHAR(255),
    message TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'OPEN',
    priority VARCHAR(50) DEFAULT 'MEDIUM',
    source_question TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    assigned_to VARCHAR(255),
    admin_reply TEXT
);

CREATE TABLE IF NOT EXISTS feedback (
    id BIGSERIAL PRIMARY KEY,
    chat_history_id BIGINT,
    helpful BOOLEAN DEFAULT FALSE,
    comment TEXT,
    created_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_faqs_status ON faqs (status);
CREATE INDEX IF NOT EXISTS idx_faqs_category ON faqs (category);
CREATE INDEX IF NOT EXISTS idx_faqs_language ON faqs (language);
CREATE INDEX IF NOT EXISTS idx_chat_history_created_at ON chat_history (created_at DESC);
CREATE INDEX IF NOT EXISTS idx_unanswered_status ON unanswered_questions (status);
CREATE INDEX IF NOT EXISTS idx_support_tickets_status ON support_tickets (status);
