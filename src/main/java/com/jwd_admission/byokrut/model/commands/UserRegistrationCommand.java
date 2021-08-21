package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.dao.services.InformationDaoService;
import com.jwd_admission.byokrut.dao.services.UserDaoService;
import com.jwd_admission.byokrut.entity.PersonalInformation;
import com.jwd_admission.byokrut.entity.Request;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.enums.SessionAttributes;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;

import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;
import static com.jwd_admission.byokrut.controller.ServiceDestination.REGISTRATION_PAGE;

public class UserRegistrationCommand implements Command {

    private UserDao userDao = new UserDao();
    private InformationDao informationDao = new InformationDao();
    private RequestDao requestDao = new RequestDao();

    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(MAIN_PAGE);

    @Override
    public CommandResponse execute(CommandRequest request) {
        String s= (String) request.getAttribute("errorMessege");
        if(s.equals("")){
            String login=request.getParameter("login");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String middleNme = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            int score1 = Integer.parseInt(request.getParameter("score_1"));
            int score2 = Integer.parseInt(request.getParameter("score_2"));
            int score3 = Integer.parseInt(request.getParameter("score_3"));
            int score4 = Integer.parseInt(request.getParameter("score_4"));
            int faculty = Integer.parseInt(request.getParameter("faculty"));
            String passportId = request.getParameter("passport_id");
            PersonalInformation personalInformation = new PersonalInformation(-1, name, middleNme, lastName, passportId);
            User user = new User(-1, login, password);
            user.setPersonalInformation(personalInformation);
            Request request1 = new Request(faculty, score1 + score2 + score3 + score4);

            InformationDaoService informationDaoService = new InformationDaoService();
            UserDaoService userDaoService = new UserDaoService();
            if (!informationDaoService.userInfExist(user) && !userDaoService.userExist(user)) {

                boolean informationCreated = informationDao.create(personalInformation);
                boolean userCreated = userDao.create(user);
                request1.setUserId(userDao.findUserId(user));
                boolean requestCreated = requestDao.create(request1);
                if (informationCreated && userCreated && requestCreated) {
                    final HttpSession session = request.createSession();
                    session.setAttribute(SessionAttributes.login.toString(), user.getLogin());
                    session.setAttribute(SessionAttributes.role.toString(), 2);
                } else {
                    requestDao.delete(request1.getId());
                    userDao.delete(user.getId());
                    informationDao.delete(user.getPersonalInformation().getId());
                }

            } else {
                request.setAttribute("info", "Such person already exists");
                return new CommandResponse(REGISTRATION_PAGE);
            }
            return COMMAND_RESPONSE;
        }
        return new CommandResponse(REGISTRATION_PAGE);
    }
}

