package com.hana.hanalink.meetingacount.exception;

import com.hana.hanalink.common.exception.AccessDeniedException;

public class NotChangeChairException extends AccessDeniedException {
    public NotChangeChairException() {
        super();
    }

    public NotChangeChairException(String message) {
        super(message);
    }

}
