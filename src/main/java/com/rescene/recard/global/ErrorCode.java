package com.rescene.recard.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Photocard not found"),
    NOT_OWNER(HttpStatus.FORBIDDEN, "User does not own the offered photocard");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
