package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class SiGunIdNotFoundException extends EntityNotFoundException {
    public SiGunIdNotFoundException(){
        super("Could not find SiGunId");
    }

    public SiGunIdNotFoundException(String message){
        super(message);
    }
}
