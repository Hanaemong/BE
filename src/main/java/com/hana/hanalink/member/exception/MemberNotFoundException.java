package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException() {
        super("Could not find member");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

}
