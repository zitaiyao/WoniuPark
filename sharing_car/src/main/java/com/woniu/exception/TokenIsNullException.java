package com.woniu.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenIsNullException extends AuthenticationException {
    public TokenIsNullException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenIsNullException(String msg) {
        super(msg);
    }
}
