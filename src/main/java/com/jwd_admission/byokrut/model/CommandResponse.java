package com.jwd_admission.byokrut.model;

import com.jwd_admission.byokrut.controller.Destination;

/**
 *This class is result of command {@link Command} execution
 */

public class CommandResponse {
    private boolean isRedirect = false;
    private Destination destination;
    public CommandResponse(Destination destination) {
        this.destination = destination;
    }
    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
    public boolean isRedirect() {
        return isRedirect;
    }
    public Destination getDestination() {
        return destination;
    }
}
