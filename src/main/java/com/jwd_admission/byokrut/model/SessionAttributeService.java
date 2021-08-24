package com.jwd_admission.byokrut.model;

import com.jwd_admission.byokrut.entity.Request;
import com.jwd_admission.byokrut.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This class handle CRUD operations for sessions attributes
 */
public class SessionAttributeService {
    /**
     * Method deletes user and request of this user from session attributes "users"
     * and "req"
     *
     * @param id      - id of user
     * @param session
     */
    public static void updateUserListRequestList(int id, HttpSession session) {
        List<User> users = (List<User>) session.getAttribute("users");
        List<Request> requestList = (List<Request>) session.getAttribute("req");
        User userToDelete = users.stream().filter(user -> user.getId() == id).findFirst().get();
        Request requestToDelete = requestList.stream().filter(request -> request.getUserId() == id).findFirst().get();
        users.remove(userToDelete);
        requestList.remove(requestToDelete);
        session.setAttribute("users", users);
        session.setAttribute("req", requestList);
    }
}
