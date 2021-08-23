package com.jwd_admission.byokrut.util;

import com.jwd_admission.byokrut.entity.BaseEntity;
import com.jwd_admission.byokrut.entity.PersonalInformation;
import com.jwd_admission.byokrut.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InputDeserializerTest {

    @Test
    void deserialize() {
        String pathnameFile = "passedTest.ser";
        List<BaseEntity> expectedTestList=new ArrayList<>();
        User firstTestUser= new User(1,"login","password");
        User secondTestUser= new User(3,new PersonalInformation(1,"Name","Surname","Lastname","PI"));
        List<User> actualTestList= (List<User>) InputDeserializer.deserialize(pathnameFile);
        assertEquals(firstTestUser.getLogin().equals(actualTestList.get(0).getLogin()),true);
    }

}