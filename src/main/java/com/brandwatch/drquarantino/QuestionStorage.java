package com.brandwatch.drquarantino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuestionStorage {

    public static final String SELECT_QUESTION = "SELECT question FROM discord_chatbot_questions ORDER BY last_asked NULLS FIRST, user_submitted DESC, random() LIMIT 1";
    public static final String MARK_AS_ASKED = "UPDATE discord_chatbot_questions SET last_asked = now() WHERE question = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Question getQuestion() {
        return new Question(jdbcTemplate.queryForObject(SELECT_QUESTION, String.class));
    }

    public void markAsAsked(Question question) {
        jdbcTemplate.update(MARK_AS_ASKED, question.getText());
    }
}
