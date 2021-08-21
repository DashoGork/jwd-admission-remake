package com.jwd_admission.byokrut.entity;

/**
 * This enum contains all possible roles
 */

public enum UserRole {
    ADMIN(1),
    USER(2);


    private final int id;
    UserRole(int id) {
        this.id = id;
    }

    public int getRoleId(){
        return this.id;
    }
}
