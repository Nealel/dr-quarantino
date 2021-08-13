package com.brandwatch.drquarantino.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionCreationResponse {
    @JsonProperty
    private String message;
}
