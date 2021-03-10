package com.brandwatch.drquarantino;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QuestionStorage {

    public static final String SELECT_RANDOM_QUESTION =
            "SELECT question FROM discord_chatbot_questions " +
            "WHERE recurring_slot IS NULL " +
            "ORDER BY last_asked NULLS FIRST, user_submitted DESC, random() " +
            "LIMIT 1";


    public static final String SELECT_RECURRING_QUESTION =
            "SELECT question FROM discord_chatbot_questions " +
                    "WHERE recurring_slot = :slot " +
                    "LIMIT 1";

    public static final String MARK_AS_ASKED = "UPDATE discord_chatbot_questions SET last_asked = now() WHERE question = :question";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Question getRandomQuestion() {
        return new Question(jdbcTemplate.queryForObject(SELECT_RANDOM_QUESTION, new HashMap<>(), String.class));
    }

    public Question getQuestion(int slot) {
        List<Question> scheduledQuestions = getScheduledQuestion(slot);
        if (scheduledQuestions.isEmpty()) {
            return getRandomQuestion();
        }
        return scheduledQuestions.get(0);
    }

    private List<Question> getScheduledQuestion(int slot) {
        log.info("Searching for questions with slot {}", slot);
        List<Question> questions = jdbcTemplate.queryForList(SELECT_RECURRING_QUESTION, ImmutableMap.of("slot", slot), Question.class);
        log.info("Found scheduled questions: {}", questions);
        return questions;
    }

    public void markAsAsked(Question question) {
        jdbcTemplate.update(MARK_AS_ASKED, ImmutableMap.of("question", question.getText()));
    }
}
