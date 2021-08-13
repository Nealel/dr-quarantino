package com.brandwatch.drquarantino.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionsResponse {
    @JsonProperty
    private int count;
}
