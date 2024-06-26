package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class PhoneNotFoundException extends EntityNotFoundException {

    public PhoneNotFoundException() {
        super("Could not find phone");
    }

    public PhoneNotFoundException(String message) {
        super(message);
    }

}
