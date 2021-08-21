package com.jwd_admission.byokrut.entity;

public class Subject extends BaseEntity {
    private String name;

    public Subject(int id) {
        super(id);
    }

    public Subject(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
