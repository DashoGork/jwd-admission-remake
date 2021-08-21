package com.jwd_admission.byokrut.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Faculty extends BaseEntity {
    private int numberOfStudents;
    private String name;
    private ArrayList<Subject> subjects;

    public Faculty(int id) {
        super(id);
    }

    public Faculty(String name) {
        super(-1);
        this.name = name;
    }

    public Faculty(int id, int numberOfStudents, String name) {
        super(id);
        this.numberOfStudents = numberOfStudents;
        this.name = name;
    }

    public void setId(int id) {
        super.setId(id);
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public Faculty(int id, ArrayList<Subject> subjects) {
        super(id);
        this.subjects = subjects;
    }

    public int getId() {
        return super.getId();
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "numberOfStudents=" + numberOfStudents +
                ", name='" + name + '\'' +
                ", subjects=" + subjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return numberOfStudents == faculty.numberOfStudents && Objects.equals(name, faculty.name) && Objects.equals(subjects, faculty.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfStudents, name, subjects);
    }
}
