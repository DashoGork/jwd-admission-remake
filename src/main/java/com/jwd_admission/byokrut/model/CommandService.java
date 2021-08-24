package com.jwd_admission.byokrut.model;

import javax.servlet.http.HttpServletRequest;

/**
 *This class is responsible for executing commands
 */

public class CommandService {

    public CommandResponse executeCommand(String commandName, HttpServletRequest request) {
        Command command = Command.of(commandName);
        return command.execute(new CommandRequest(request));
    }
}
