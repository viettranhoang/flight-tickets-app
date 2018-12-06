package com.vit.flightticketsapp.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.vit.flightticketsapp.data.local.DBConstants;
import com.vit.flightticketsapp.data.model.Contact;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class ContactDao extends BaseDao<Contact> {

    @Query("SELECT * FROM " + DBConstants.Contact.TABLE_NAME)
    public abstract Flowable<List<Contact>> getContacts();
}
