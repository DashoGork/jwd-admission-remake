package com.jwd_admission.byokrut.model;

import com.jwd_admission.byokrut.model.commands.*;

public enum CommandInstance {
    SHOW_MAIN(new ShowMainPageCommand()),
    SHOW_REGISTRATION(new ShowRegistrationPageCommand()),
    REGISTRATION(new UserRegistrationCommand()),
    SHOW_LOGIN(new ShowLoginPageCommand()),
    LOGIN(new UserLoginCommand()),
    SHOW_PERSONAL_ACCOUNT(new ShowPersonalAccountCommand()),
    SHOW_EDIT(new ShowEditPageCommand()),
    EDIT(new UserEditCommand()),
    APPROVE(new UserApproveCommand()),
    DELETE(new UserDeleteCommand()),
    CALCULATE(new AdminCalculateCommand()),
    LANGUAGE(new LanguageChangeCommand()),
    LOG_OUT(new UserLogOutCommand());

    private final Command command;

    CommandInstance(Command command) {
        this.command = command;
    }

    static Command commandOf(String commandName) {
        for (CommandInstance value : values()) {
            if (value.name().equalsIgnoreCase(commandName)) {
                return value.command;
            }
        }
        return SHOW_MAIN.command;
    }
}
