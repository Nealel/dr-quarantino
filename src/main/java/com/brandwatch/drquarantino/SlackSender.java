package com.brandwatch.drquarantino;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SlackSender {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    public void ask(Question question) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(webhookUrl);

            StringEntity entity = new StringEntity(getQuestionJson(question));

            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            client.execute(httpPost);
        } catch (IOException e) {
            log.error("Problem calling slack", e);
        }
    }

    public String getQuestionJson(Question question) throws JsonProcessingException {
        if (question.getText().matches("^[Ww]ould you rather .* or .*")) {
            String[] options = question.getText().substring("Would you rather".length()).split(" or ");

            return MAPPER.writeValueAsString(new Question("Would you rather: "
                    + "\n:one: " + options[0]
                    + "\n:two: " + options[1].replace("?", "")));
        } else {
            return MAPPER.writeValueAsString(question);
        }
    }

    public void poll(String question) {

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(webhookUrl);

            String json = MAPPER.writeValueAsString(question);



            StringEntity entity = new StringEntity(json);

            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            client.execute(httpPost);
        } catch (IOException e) {
            log.error("Problem calling slack", e);
        }
    }
}
