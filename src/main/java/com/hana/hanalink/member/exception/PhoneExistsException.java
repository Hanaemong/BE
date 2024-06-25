package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.ValueInvalidException;

public class PhoneExistsException extends ValueInvalidException {
    public PhoneExistsException(){
        super("Phone already exists");
    }

    public PhoneExistsException(String message){
        super(message);
    }
}
