package com.hana.hanalink.member.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class SiGunGuIdNotFoundException extends EntityNotFoundException {
    public SiGunGuIdNotFoundException(){
        super("Could not find SiGunGuId");
    }

    public SiGunGuIdNotFoundException(String message){
        super(message);
    }
}
