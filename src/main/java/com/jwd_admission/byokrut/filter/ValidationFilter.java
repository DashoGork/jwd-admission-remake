package com.jwd_admission.byokrut.filter;

import com.jwd_admission.byokrut.dao.schemas.InformationSchema;
import com.jwd_admission.byokrut.dao.schemas.UserSchema;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter checks the correctness of the entered data
 */

@WebFilter(urlPatterns = "/home/*")
public class ValidationFilter implements Filter {

    private final static Pattern LOGIN = Pattern.compile("[A-Za-z0-9А-Яа-я]{5,15}");
    private final static Pattern PASSWORD = Pattern.compile("[A-Za-z0-9А-Яа-я]{5,20}");
    private final static Pattern NAME = Pattern.compile("[A-Za-zА-Яа-я]{3,10}");
    private final static Pattern PASSPORT_ID = Pattern.compile("^[A-ZА-Я]{2}[0-9]{4}");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if(httpRequest.getMethod().equalsIgnoreCase("POST")){
            String login = servletRequest.getParameter(UserSchema.login.name());
            String password = servletRequest.getParameter(UserSchema.password.name());
            String name = servletRequest.getParameter(InformationSchema.name.name());
            String middleName = servletRequest.getParameter(InformationSchema.middlename.name());
            String lastName = servletRequest.getParameter(InformationSchema.lastname.name());
            String passportId = servletRequest.getParameter(InformationSchema.passport_id.name());

            StringBuilder errorMessege = new StringBuilder();
            errorMessege.append(isValid(LOGIN,login ));
            errorMessege.append(isValid(PASSWORD,password));
            errorMessege.append(isValid(NAME,name));
            errorMessege.append(isValid(NAME,middleName));
            errorMessege.append(isValid(NAME,lastName));
            errorMessege.append(isValid(PASSPORT_ID,passportId));
            servletRequest.setAttribute("errorMessege",errorMessege.toString());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String isValid(Pattern pattern, String string) {
        if(string!=null){
            if (!pattern.matcher(string).matches()) {
                return "error in " + string;
            }
        }
        return "";
    }
}
