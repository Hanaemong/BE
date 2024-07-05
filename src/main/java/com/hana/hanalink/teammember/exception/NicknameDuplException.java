package com.hana.hanalink.teammember.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class NicknameDuplException extends EntityNotFoundException {
    public NicknameDuplException() {
        super("NicknameAlreadyExists");
    }

    public NicknameDuplException(String message) {
        super(message);
    }

}
