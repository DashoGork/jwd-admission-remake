package com.jwd_admission.byokrut.model.commands;

import com.jwd_admission.byokrut.entity.Language;
import com.jwd_admission.byokrut.model.Command;
import com.jwd_admission.byokrut.model.CommandRequest;
import com.jwd_admission.byokrut.model.CommandResponse;

import static com.jwd_admission.byokrut.controller.ServiceDestination.MAIN_PAGE;

public class LanguageChangeCommand implements Command {

    private static final String sessionPath = "javax.servlet.jsp.jstl.fmt.locale.session";

    public static final CommandResponse COMMAND_RESPONSE = new CommandResponse(MAIN_PAGE);

    @Override
    public CommandResponse execute(CommandRequest request) {
        String langParameter = request.getParameter("id");
        String language = Language.getLanguage(langParameter);
        request.createSession().setAttribute(sessionPath, language);
        return COMMAND_RESPONSE;
    }
}
