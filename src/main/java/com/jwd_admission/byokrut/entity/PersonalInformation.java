package com.jwd_admission.byokrut.entity;

import java.io.Serializable;
import java.util.Objects;

public class PersonalInformation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String middleName;
    private String lastName;
    private String passportId;

    public PersonalInformation(int id) {
        super(id);
    }

    public PersonalInformation(int id, String firstName, String middleName, String lastName, String passportId) {
        super(id);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.passportId = passportId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInformation that = (PersonalInformation) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(middleName, that.middleName) && Objects.equals(lastName, that.lastName) && Objects.equals(passportId, that.passportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, passportId);
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportId='" + passportId + '\'' +
                '}';
    }
}
