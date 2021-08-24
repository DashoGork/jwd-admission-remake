package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.PersonalInformation;
import com.jwd_admission.byokrut.entity.Request;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import static com.jwd_admission.byokrut.controller.ServiceDestination.PERSONAL_ACCOUNT_PAGE;

/**
 * This command edits information about user in db
 */

public class UserEditCommand implements Command {

    private  UserDao userDao = new UserDao();
    private  InformationDao informationDao = new InformationDao();
    private  RequestDao requestDao = new RequestDao();

    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(PERSONAL_ACCOUNT_PAGE);

    @Override
    public CommandResponse execute(CommandRequest request) {

        String password = (String) request.getRequestParameter("password");
        int id = Integer.parseInt((String) request.getRequestParameter("id"));
        String name = request.getParameter("name");
        String middleNme = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        int score1 = Integer.parseInt(request.getParameter("score_1"));
        int score2 = Integer.parseInt(request.getParameter("score_2"));
        int score3 = Integer.parseInt(request.getParameter("score_3"));
        int score4 = Integer.parseInt(request.getParameter("score_4"));
        int faculty = Integer.parseInt(request.getParameter("faculty"));
        String passportId = request.getParameter("passport_id");
        User user = userDao.findEntityById(id);
        PersonalInformation personalInformation = new PersonalInformation(user.getPersonalInformation().getId(),name,middleNme,lastName,passportId);
        user.setPassword(password);
        user.setPersonalInformation(personalInformation);
        Request request1 = new Request(faculty, id, (score1+score2+score3+score4));
        informationDao.update(personalInformation);
        userDao.updateUser(user);
        requestDao.update(request1);

        return COMMAND_RESPONSE;
    }
}
