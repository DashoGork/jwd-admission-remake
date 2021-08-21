package com.jwd_admission.byokrut.model.commands;


import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.enums.SessionAttributes;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import javax.servlet.http.HttpSession;


import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;

public class UserLoginCommand implements Command {

    private UserDao userDao = new UserDao();


    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(MAIN_PAGE);

    @Override
    public CommandResponse execute(CommandRequest request) {
        String login = (String) request.getRequestParameter("login");
        String password = (String) request.getRequestParameter("password");
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        if (userDao.findUserByLoginAndPassword(user) != -1) {
            user.setRoleId(userDao.findUserRoleId(user));
            final HttpSession session = request.createSession();
            session.setAttribute(SessionAttributes.login.toString(), user.getLogin());
            session.setAttribute(SessionAttributes.role.toString(), user.getRoleId());
        }
        return COMMAND_RESPONSE;
    }
}
