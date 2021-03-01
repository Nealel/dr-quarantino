package com.brandwatch.drquarantino;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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

    public Scheduler(QuestionStorage questionStorage, SlackSender slackSender) {
        this.questionStorage = questionStorage;
        this.slackSender = slackSender;
        calculateCurrentSlot();
    }

    private void calculateCurrentSlot() {
        OffsetDateTime now = Instant.now().atOffset(ZoneOffset.UTC);
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        slot = dayOfWeek.getValue() * 2; // 2 slots a day
        if (now.getHour() > 10 ) { // increase slot by 1 for the afternoon
            slot++;
        }
        log.info("Starting question from slot {}", slot);
    }

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
        slot = 1;
    }

}
