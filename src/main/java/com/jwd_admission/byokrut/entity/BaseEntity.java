package com.jwd_admission.byokrut.entity;

import java.io.Serializable;

/**
 * This class describes base entity
 */

public abstract class BaseEntity implements Serializable, Cloneable {

    private int id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



}
