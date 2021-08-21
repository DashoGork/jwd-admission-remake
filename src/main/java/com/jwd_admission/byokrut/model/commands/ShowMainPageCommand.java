package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.dao.FacultyDao;
import com.jwd_admission.byokrut.entity.Faculty;
import com.jwd_admission.byokrut.entity.User;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;
import com.jwd_admission.byokrut.util.InputDeserializer;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;

public class ShowMainPageCommand implements Command {


    @Override
    public CommandResponse execute(CommandRequest request) {
        return new CommandResponse(MAIN_PAGE);
    }


}
