CREATE TABLE discord_chatbot_questions (
    question text NOT NULL,
    user_submitted boolean NOT NULL DEFAULT FALSE,
    last_asked timestamp,
    recurring_slot int DEFAULT NULL,
    UNIQUE (question)
);