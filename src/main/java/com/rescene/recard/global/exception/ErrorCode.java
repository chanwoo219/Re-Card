package com.rescene.recard.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "포토카드를 찾을 수 없습니다."),
    NOT_OWNER(HttpStatus.FORBIDDEN, "본인의 리소스만 처리할 수 있습니다."),
    TRADE_NOT_FOUND(HttpStatus.NOT_FOUND, "교환 요청을 찾을 수 없습니다."),
    TRADE_ALREADY_DONE(HttpStatus.CONFLICT, "이미 처리된 교환 요청입니다.");

    private final HttpStatus status;
    private final String message;
}
