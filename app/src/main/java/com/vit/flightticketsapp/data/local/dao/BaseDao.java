package com.vit.flightticketsapp.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(T... t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<T> t);

    @Update
    public abstract void update(T t);

    @Delete
    public abstract void delete(T t);
}