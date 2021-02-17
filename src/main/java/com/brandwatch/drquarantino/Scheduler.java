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

    private volatile int slot;

    @Scheduled(cron = "${app.schedule}")
    public void run() {
        Question question = questionStorage.getScheduledQuestion(slot)
                .orElseGet(questionStorage::getRandomQuestion);
        log.info("Sending question: {}", question);
        slackSender.ask(question);
        questionStorage.markAsAsked(question);
        slot++;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void resetWeek() {
        slot = 0;
    }

}
