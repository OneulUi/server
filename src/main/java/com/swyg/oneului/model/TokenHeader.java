package com.swyg.oneului.model;

import lombok.Getter;

@Getter
public enum TokenHeader {
    AUTHORIZATION("Authorization"),
    TOKEN_PREFIX("Bearer ");

    private final String value;

    TokenHeader(String value) {
        this.value = value;
    }
}