package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import static com.jwd_admission.byokrut.controller.ServiceDestination.LOGIN_PAGE;

/**
 * This command shows page for log in
 */

public class ShowLoginPageCommand implements Command {

    @Override
    public CommandResponse execute(CommandRequest request) {
        return new CommandResponse(LOGIN_PAGE);
    }
}
