package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.entity.UserRole;
import com.jwd_admission.byokrut.enums.SessionAttributes;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;
import com.jwd_admission.byokrut.model.SessionAttributeService;

import javax.servlet.http.HttpSession;

import static com.jwd_admission.byokrut.controller.ServiceDestination.PERSONAL_ACCOUNT_PAGE;

/**
 * This command is used by admin for deleting all information about user
 */

public class UserDeleteCommand implements Command {

    private UserDao userDao = new UserDao();
    private InformationDao informationDao = new InformationDao();
    private RequestDao requestDao = new RequestDao();

    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(PERSONAL_ACCOUNT_PAGE);

    @Override
    public CommandResponse execute(CommandRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDao.findEntityById(id);
        requestDao.delete(id);
        userDao.delete(id);
        informationDao.delete(user.getPersonalInformation().getId());
        final HttpSession session = request.createSession();
        int role = Integer.parseInt(String.valueOf(session.getAttribute(SessionAttributes.role.name())));
        if (role == UserRole.USER.getRoleId()) {
            session.removeAttribute(SessionAttributes.login.name());
            session.removeAttribute(SessionAttributes.role.name());
        } else {
            SessionAttributeService.updateUserListRequestList(id, session);
        }
        return new CommandResponse(PERSONAL_ACCOUNT_PAGE);
    }
}
