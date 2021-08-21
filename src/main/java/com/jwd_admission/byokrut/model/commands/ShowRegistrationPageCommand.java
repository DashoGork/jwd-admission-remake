package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import static com.jwd_admission.byokrut.controller.ServiceDestination.REGISTRATION_PAGE;

public class ShowRegistrationPageCommand implements Command {
    @Override
    public CommandResponse execute(CommandRequest request) {
        return new CommandResponse(REGISTRATION_PAGE);
    }
}
