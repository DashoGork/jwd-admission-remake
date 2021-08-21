package com.jwd_admission.byokrut.filter;

import com.jwd_admission.byokrut.enums.SessionAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
 * Filter which limit access
 */

@WebFilter(urlPatterns = "/home/*")
public class AuthorizationFilter implements Filter {

    private static final String[] whiteList = {"show_main", "show_login", "show_registration", "login", "show_registration", "registration", "language"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpSession session = request.getSession();

        final Object role = session.getAttribute(SessionAttributes.role.name());
        final String command = request.getParameter("command");
        if (!Arrays.stream(whiteList).anyMatch(s -> s.equals(command))) {
            if (role == null) {
                ((HttpServletResponse) servletResponse).sendRedirect("home?command=show_login");
            } else filterChain.doFilter(servletRequest, servletResponse);
        } else filterChain.doFilter(servletRequest, servletResponse);
    }
}
