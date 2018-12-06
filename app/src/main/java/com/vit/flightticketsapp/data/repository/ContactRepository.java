package com.vit.flightticketsapp.data.repository;

import android.app.Application;

import com.vit.flightticketsapp.data.local.AppDatabase;
import com.vit.flightticketsapp.data.local.dao.ContactDao;
import com.vit.flightticketsapp.data.model.Contact;
import com.vit.flightticketsapp.data.remote.ApiClient;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ContactRepository implements DataSource {

    private ContactDao mContactDao;

    public ContactRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.mContactDao = database.contactDao();
    }

    @Override
    public Flowable<List<Contact>> getContacts() {
        return Flowable.mergeDelayError(
                ApiClient.getTicketService().getContacts(null, null)
                        .toFlowable()
                        .doOnNext(contacts -> mContactDao.insert(contacts))
                        .subscribeOn(Schedulers.io()),
                mContactDao.getContacts().subscribeOn(Schedulers.io()));
    }

    @Override
    public Observable<List<Contact>> searchContacts(String keyword) {
        return Observable.mergeDelayError(
                ApiClient.getTicketService().getContacts(null, keyword)
                        .toObservable()
                        .doOnNext(contacts -> mContactDao.insert(contacts))
                        .subscribeOn(Schedulers.io()),

                mContactDao.getContacts()
                        .map(contacts -> Observable.fromIterable(contacts)
                                .filter(contact -> contact.getName().toLowerCase().contains(keyword.toLowerCase()))
                                .toList()
                                .blockingGet())
                        .toObservable()
                        .subscribeOn(Schedulers.io())
        );
    }
}
