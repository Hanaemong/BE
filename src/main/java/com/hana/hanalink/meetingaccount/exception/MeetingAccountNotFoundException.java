package com.hana.hanalink.meetingaccount.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class MeetingAccountNotFoundException extends EntityNotFoundException {
    public MeetingAccountNotFoundException() {
        super("Could not find meeting account");
    }

    public MeetingAccountNotFoundException(String message) {
        super(message);
    }

}
