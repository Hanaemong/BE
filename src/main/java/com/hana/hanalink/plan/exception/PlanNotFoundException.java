package com.hana.hanalink.plan.exception;

import com.hana.hanalink.common.exception.EntityNotFoundException;

public class PlanNotFoundException extends EntityNotFoundException {
    public PlanNotFoundException() {
        super("Could not find plan");
    }

    public PlanNotFoundException(String message) {
        super(message);
    }

}
