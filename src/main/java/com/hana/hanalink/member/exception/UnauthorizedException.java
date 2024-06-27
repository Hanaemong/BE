package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.AccessDeniedException;

public class UnauthorizedException extends AccessDeniedException {
    public UnauthorizedException() {
        super("Access Denied : token이 일치하지 않음");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
