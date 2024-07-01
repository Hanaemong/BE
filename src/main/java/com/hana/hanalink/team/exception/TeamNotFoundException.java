package com.hana.hanalink.team.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class TeamNotFoundException extends EntityNotFoundException {
    public TeamNotFoundException() {
        super("Could not find team");
    }

    public TeamNotFoundException(String message) {
        super(message);
    }

}
