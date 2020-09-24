package com.brandwatch.drquarantino;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
