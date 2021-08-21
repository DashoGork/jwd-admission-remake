package com.jwd_admission.byokrut.controller;

import com.jwd_admission.byokrut.dao.FacultyDao;
import com.jwd_admission.byokrut.entity.Faculty;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.enums.ContextAttributes;
import com.jwd_admission.byokrut.enums.PathToFiles;
import com.jwd_admission.byokrut.model.CommandResponse;
import com.jwd_admission.byokrut.model.CommandService;
import com.jwd_admission.byokrut.util.InputDeserializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "home", value = "/home")
public class MainServlet extends HttpServlet {
    static final String COMMAND = "command";
    final private CommandService commandService = new CommandService();
    private FacultyDao facultyDao = new FacultyDao();

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        if (InputDeserializer.deserialize(PathToFiles.MMFPassed.getPath()) != null) {
            List<User> userListFromMmf = (List<User>) InputDeserializer.deserialize(PathToFiles.MMFPassed.getPath());
            List<User> userListFromRfikt = (List<User>) InputDeserializer.deserialize(PathToFiles.RFIKTPassed.getPath());
            List<User> userListFromFmo = (List<User>) InputDeserializer.deserialize(PathToFiles.FMOPassed.getPath());
            List<User> userListFromBio = (List<User>) InputDeserializer.deserialize(PathToFiles.FMOPassed.getPath());

            servletContext.setAttribute(ContextAttributes.listOfPassedFromMMf.toString(), userListFromMmf);
            servletContext.setAttribute(ContextAttributes.listOfPassedFromFmo.toString(), userListFromFmo);
            servletContext.setAttribute(ContextAttributes.listOfPassedFromRfikt.toString(), userListFromRfikt);
            servletContext.setAttribute(ContextAttributes.listOfPassedFromBio.toString(), userListFromBio);
            servletContext.setAttribute(ContextAttributes.calculated.toString(), true);
        } else {
            servletContext.setAttribute(ContextAttributes.calculated.toString(), false);
        }
        List<Faculty> faculties = facultyDao.findAll();
        servletContext.setAttribute(ContextAttributes.allFaculties.toString(), faculties);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandResponse commandResponse = commandService.executeCommand(request.getParameter(COMMAND), request);
        String destination = commandResponse.getDestination().getPath();
        if (commandResponse.isRedirect()) {
            response.sendRedirect(destination);
        } else {
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }
}
