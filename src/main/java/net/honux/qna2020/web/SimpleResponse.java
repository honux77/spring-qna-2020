package net.honux.qna2020.web;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SimpleResponse {

    @JsonProperty
    private int status;
    @JsonProperty
    private String message;

    public SimpleResponse(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
