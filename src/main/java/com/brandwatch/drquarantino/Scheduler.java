package com.brandwatch.drquarantino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {

    @Autowired
    private QuestionStorage questionStorage;
    @Autowired
    private SlackSender slackSender;

    @Scheduled(cron = "${app.schedule}")
    public void run() {
        Question question = questionStorage.getQuestion();
        log.info("Sending question: {}", question);
        slackSender.ask(question);
        questionStorage.markAsAsked(question);
    }

}
