package com.jwd_admission.byokrut.model;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class CommandRequest {
    private final HttpServletRequest request;

    public CommandRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Object getRequestParameter(String name) {
        return request.getParameter(name);
    }

    public HttpSession createSession() {
        return request.getSession();
    }

    public void setAttribute(String name, Object object) {
        request.setAttribute(name, object);
    }

    public String getParameter(String command) {
        return request.getParameter(command);
    }

    public Object getAttribute(String attribute){
        return request.getAttribute(attribute);
    }

    public StringBuffer getRequestUrl(){
        return request.getRequestURL();
    }

    public String getServletPath(){
        return request.getServletPath();
    }

    public HttpSession getSession() {
        return  request.getSession();
    }
}
