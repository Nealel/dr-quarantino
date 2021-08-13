package com.brandwatch.drquarantino;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

class SlackSenderTest {

    SlackSender slackSender = new SlackSender();

    @Test
    public void wouldYouRatherQuestion() throws JsonProcessingException {
        String inputText = "Would you rather have a bath in baked beans or a shower of mayonaise?";
        String actualResult = slackSender.getQuestionJson(new Question(inputText));
        String expectedResult = "{\"text\":\"Would you rather: \\n:one:  have a bath in baked beans\\n:two: a shower of mayonaise\"}";
        assertThat(actualResult, equalTo(expectedResult));
    }

    @Test
    public void normalQuestion() throws JsonProcessingException {
        String inputText = "What's your favourite pizza topping?";
        String actualResult = slackSender.getQuestionJson(new Question(inputText));
        String expectedResult = "{\"text\":\"What's your favourite pizza topping?\"}";
        assertThat(actualResult, equalTo(expectedResult));
    }

}