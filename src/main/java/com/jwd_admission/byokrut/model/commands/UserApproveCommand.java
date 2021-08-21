package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.entity.Request;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;
import com.jwd_admission.byokrut.model.SessionAttributeService;


import javax.servlet.http.HttpSession;
import java.util.List;

import static com.jwd_admission.byokrut.controller.ServiceDestination.PERSONAL_ACCOUNT_PAGE;

public class UserApproveCommand implements Command {
    private RequestDao requestDao = new RequestDao();

    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(PERSONAL_ACCOUNT_PAGE);

    @Override
    public CommandResponse execute(CommandRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        requestDao.approve(id);
        final HttpSession session = request.createSession();
        SessionAttributeService.updateUserListRequestList(id,session);
        return COMMAND_RESPONSE;
    }
}
