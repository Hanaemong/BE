package com.hana.hanalink.teammember.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class TeamMemberNotFoundException extends EntityNotFoundException {
    public TeamMemberNotFoundException() {
        super("Could not find TeamMember");
    }

    public TeamMemberNotFoundException(String message) {
        super(message);
    }

}
