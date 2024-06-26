package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.ValueInvalidException;

public class PasswordIncorrectException extends ValueInvalidException {
    public PasswordIncorrectException() {
        super("Invalid password");
    }

    public PasswordIncorrectException(String message) {
        super(message);
    }

}
