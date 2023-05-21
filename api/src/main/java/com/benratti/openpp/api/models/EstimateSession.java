package com.benratti.openpp.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record EstimateSession(
        @JsonInclude(NON_NULL)
        String uid,
        String name,
        int capacity
) {
}
