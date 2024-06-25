package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.ValueInvalidException;

public class PasswordExistsException extends ValueInvalidException {
    public PasswordExistsException(){
        super("Password already exists");
    }

    public PasswordExistsException(String message){
        super(message);
    }
}
