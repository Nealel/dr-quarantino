CREATE TABLE discord_chatbot_questions (
    question text NOT NULL,
    user_submitted boolean NOT NULL DEFAULT FALSE,
    last_asked timestamp,
    recurring_slot int DEFAULT NULL,
    UNIQUE (question)
);

INSERT INTO discord_chatbot_questions (question, recurring_slot) VALUES
('How was your weekend?', 1),
('What tv show/book/music/etc have you been enjoying recent?', 6),
('Show me your best gif!',9);