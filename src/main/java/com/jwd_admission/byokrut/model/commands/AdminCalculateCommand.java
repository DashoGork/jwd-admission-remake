package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.dao.RequestDao;
import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.FacultyName;
import com.jwd_admission.byokrut.entity.PersonalInformation;
import com.jwd_admission.byokrut.entity.Request;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.enums.ContextAttributes;
import com.jwd_admission.byokrut.enums.PathToFiles;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;
import com.jwd_admission.byokrut.util.OutputSerializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;

/**
 * This command calculate and write ti file all users that passed
 */

public class AdminCalculateCommand implements Command {
    private UserDao userDao = new UserDao();
    private InformationDao informationDao = new InformationDao();
    private RequestDao requestDao = new RequestDao();


    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(MAIN_PAGE);

    /**
     * Creates list of users which corresponds list of requests
     *
     * @return list of users those request are in entering requestList
     */
    private List<User> createUserListFromRequestList(List<Request> requestList) {
        List<User> userList = new ArrayList<>();
        for (Request userRequest : requestList) {
            User user = userDao.findEntityById(userRequest.getUserId());
            PersonalInformation personalInformation = informationDao.findEntityById(user.getPersonalInformation().getId());
            user.setPersonalInformation(personalInformation);
            userList.add(user);
        }
        return userList;
    }


    @Override
    public CommandResponse execute(CommandRequest request) {

        List<User> userListFromMmf = createUserListFromRequestList(requestDao.findAllPassed(FacultyName.MMF));
        List<User> userListFromRfikt = createUserListFromRequestList(requestDao.findAllPassed(FacultyName.RFIKT));
        List<User> userListFromFmo = createUserListFromRequestList(requestDao.findAllPassed(FacultyName.FMO));
        List<User> userListFromBio = createUserListFromRequestList(requestDao.findAllPassed(FacultyName.BIO));

        OutputSerializer.serialize(userListFromMmf, PathToFiles.MMFPassed.getPath());
        OutputSerializer.serialize(userListFromFmo, PathToFiles.FMOPassed.getPath());
        OutputSerializer.serialize(userListFromRfikt, PathToFiles.RFIKTPassed.getPath());
        OutputSerializer.serialize(userListFromBio, PathToFiles.BIOPassed.getPath());

        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute(ContextAttributes.listOfPassedFromMMf.toString(), userListFromMmf);
        servletContext.setAttribute(ContextAttributes.listOfPassedFromFmo.toString(), userListFromFmo);
        servletContext.setAttribute(ContextAttributes.listOfPassedFromRfikt.toString(), userListFromRfikt);
        servletContext.setAttribute(ContextAttributes.listOfPassedFromBio.toString(), userListFromBio);
        servletContext.setAttribute(ContextAttributes.calculated.toString(), true);

        return COMMAND_RESPONSE;
    }
}
