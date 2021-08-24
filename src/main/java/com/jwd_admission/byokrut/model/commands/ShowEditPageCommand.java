package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.Request;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import static com.jwd_admission.byokrut.controller.ServiceDestination.EDIT_PAGE;

/**
 * This command shows page for editing user info
 */

public class ShowEditPageCommand implements Command {
    private  UserDao userDao = new UserDao();
    private  InformationDao informationDao = new InformationDao();
    private  RequestDao requestDao = new RequestDao();

    @Override
    public CommandResponse execute(CommandRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDao.findEntityById(id);
        user.setPersonalInformation(informationDao.findEntityById(user.getPersonalInformation().getId()));
        Request userRequest = requestDao.findRequestByUser(user.getId());
        request.setAttribute("req", userRequest);
        request.setAttribute("user", user);
        return new CommandResponse(EDIT_PAGE);
    }
}
