package com.vit.flightticketsapp.data.repository;

import com.vit.flightticketsapp.data.model.Contact;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface DataSource {

    Flowable<List<Contact>> getContacts();

    Observable<List<Contact>> searchContacts(String keyword);
}
