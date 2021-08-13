package com.brandwatch.drquarantino.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import com.brandwatch.drquarantino.QuestionStorage;

@Slf4j
@RestController
public class QuestionController {

    @Autowired
    private QuestionStorage questionStorage;

    @GetMapping("/questions")
    public QuestionsResponse getQuestions() {
        return new QuestionsResponse(questionStorage.countQuestions());
    }

    @PostMapping("/question")
    public QuestionCreationResponse addQuestion(QuestionRequest request) {
        try {
            log.info("Adding question {}", request.getQuestion());
            questionStorage.insertQuestion(request.getQuestion());
            return new QuestionCreationResponse("OK! I've added that to my list");
        } catch (Exception e) {
            return new QuestionCreationResponse("Error! " + e.getMessage());
        }
    }

}