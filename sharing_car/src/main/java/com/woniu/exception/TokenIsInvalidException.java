package com.woniu.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenIsInvalidException extends AuthenticationException {

    public TokenIsInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenIsInvalidException(String msg) {
        super(msg);
    }
}
