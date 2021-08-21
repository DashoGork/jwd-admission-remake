package com.jwd_admission.byokrut.dao.services;


import com.jwd_admission.byokrut.dao.UserDao;
import com.jwd_admission.byokrut.entity.User;

import java.sql.Connection;

public class UserDaoService {
    private static UserDao userDao;

    public UserDaoService() {
        userDao=new UserDao();
    }

    public static boolean userExist(User user) {
        return (userDao.findUserId(user) != -1);
    }
}
