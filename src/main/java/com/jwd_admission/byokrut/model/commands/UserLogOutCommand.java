package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.enums.SessionAttributes;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import javax.servlet.http.HttpSession;

import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;

/**
 * This command log out user
 */

public class UserLogOutCommand implements Command {
    @Override
    public CommandResponse execute(CommandRequest request) {
        final HttpSession session = request.createSession();
        session.removeAttribute(SessionAttributes.login.toString());
        session.removeAttribute(SessionAttributes.user.toString());
        session.removeAttribute(SessionAttributes.users.toString());
        session.removeAttribute(SessionAttributes.req.toString());
        session.removeAttribute(SessionAttributes.faculty.toString());
        session.removeAttribute(SessionAttributes.role.toString());
        return new CommandResponse(MAIN_PAGE);
    }
}
