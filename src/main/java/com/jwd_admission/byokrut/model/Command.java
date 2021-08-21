package com.jwd_admission.byokrut.model;

public interface Command {
    CommandResponse execute(CommandRequest request);
    static Command of(String commandName) {
        return CommandInstance.commandOf(commandName);
    }
}
