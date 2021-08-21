package com.jwd_admission.byokrut.entity;

import java.io.Serializable;
import java.util.Objects;

public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String login;
    private String password;
    private int roleId;
    private PersonalInformation personalInformation;

    public User() {
        super(-1);
    }

    public User(int id, PersonalInformation personalInformation) {
        super(id);
        this.personalInformation = personalInformation;
    }


    public User(int id, String login, String password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, PersonalInformation personalInformation) {
        super(id);
        this.login = login;
        this.password = password;
        this.personalInformation = personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    public int getRoleId() {
        return roleId;
    }


    @Override
    public void setId(int id) {
        super.setId(id);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return roleId == user.roleId && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(personalInformation, user.personalInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, roleId, personalInformation);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", personalInformation=" + personalInformation +
                '}';
    }
}

