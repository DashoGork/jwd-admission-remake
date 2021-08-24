package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;

/**
 * This command shows main page
 */

public class ShowMainPageCommand implements Command {
    @Override
    public CommandResponse execute(CommandRequest request) {
        return new CommandResponse(MAIN_PAGE);
    }
}
