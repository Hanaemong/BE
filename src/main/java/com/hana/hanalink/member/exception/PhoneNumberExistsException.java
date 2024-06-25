package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.ValueInvalidException;

public class PhoneNumberExistsException extends ValueInvalidException {
    public PhoneNumberExistsException(){
        super("Phone number already exists");
    }

    public PhoneNumberExistsException(String message){
        super(message);
    }
}
