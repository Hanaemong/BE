package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class PasswordNotFoundException extends EntityNotFoundException {
    public PasswordNotFoundException(){
        super("Could not find password");
    }

    public PasswordNotFoundException(String message){
        super(message);
    }
}
