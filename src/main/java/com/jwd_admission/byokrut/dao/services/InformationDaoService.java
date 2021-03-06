package com.jwd_admission.byokrut.dao.services;

import com.jwd_admission.byokrut.dao.InformationDao;
import com.jwd_admission.byokrut.entity.User;

/**
 * This class some methods from {@link InformationDao}
 */


public class InformationDaoService {
    private static InformationDao informationDao;
    public InformationDaoService() {
        informationDao=new InformationDao();
    }

    public static boolean userInfExist(User user) {
        return (informationDao.findIdByPassportId(user.getPersonalInformation().getPassportId()) != -1);
    }
}
