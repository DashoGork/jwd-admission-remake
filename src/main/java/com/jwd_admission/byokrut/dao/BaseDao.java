package com.jwd_admission.byokrut.dao;

import com.jwd_admission.byokrut.entity.BaseEntity;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDao<K, T extends BaseEntity> {

    public abstract T findEntityById(K id);

    public abstract List<T> findAll();

    public abstract boolean delete(K id);

    public abstract boolean create(T t);

    public abstract T update(T t);

}
