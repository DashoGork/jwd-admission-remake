package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.FacultyDao;
import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.*;
import com.jwd_admission.byokrut.enums.SessionAttributes;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.jwd_admission.byokrut.controller.ServiceDestination.PERSONAL_ACCOUNT_PAGE;

/**
 * This command shows personal page for admin and user
 */

public class ShowPersonalAccountCommand implements Command {
    private  UserDao userDao = new UserDao();
    private  InformationDao informationDao = new InformationDao();
    private  RequestDao requestDao = new RequestDao();
    private  FacultyDao facultyDao = new FacultyDao();


    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(PERSONAL_ACCOUNT_PAGE);


    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.createSession();
        int role = (int) session.getAttribute(SessionAttributes.role.name());
        if (role == UserRole.ADMIN.getRoleId()) {
            List<PersonalInformation> personalInformationList = informationDao.findAll();
            List<User> userList = new ArrayList<>();
            for (PersonalInformation information : personalInformationList) {
                User user = userDao.findUserByInfId(information.getId());
                user.setPersonalInformation(information);
                if(requestDao.findRequestByUser(user.getId()).getApproved()==0)
                userList.add(user);
            }
            List<Request> userReq = requestDao.findAll();
            session.setAttribute(SessionAttributes.users.name(), userList);
            session.setAttribute(SessionAttributes.req.name(), userReq);
        } else {
            User user = new User();
            user.setRoleId(Integer.parseInt(String.valueOf(session.getAttribute(SessionAttributes.role.toString()))));
            user.setLogin(session.getAttribute(SessionAttributes.login.toString()).toString());
            user.setId(userDao.findUserId(user));
            user = userDao.findEntityById(user.getId());
            user.setPersonalInformation(informationDao.findEntityById(user.getPersonalInformation().getId()));
            Request userRequest = requestDao.findRequestByUser(user.getId());
            session.setAttribute(SessionAttributes.user.name(), user);
            session.setAttribute(SessionAttributes.req.name(), userRequest);

            session.setAttribute(SessionAttributes.faculty.name(), facultyDao.findEntityById(userRequest.getFacultyId()).getName());

        }
        return COMMAND_RESPONSE;
    }
}
