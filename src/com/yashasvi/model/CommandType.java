package com.yashasvi.model;

import lombok.Getter;

@Getter
public enum CommandType {
    GET("get"), PUT("put"), DELETE("delete"), SEARCH("search"), KEYS("keys"), EXIT("exit");
    private final String type;

    CommandType(String type) {
        this.type = type;
    }
}
