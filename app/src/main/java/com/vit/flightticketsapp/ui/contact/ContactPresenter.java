package com.vit.flightticketsapp.ui.contact;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.vit.flightticketsapp.data.repository.ContactRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ContactPresenter implements ContactContract.Presenter {

    private static final String TAG = ContactPresenter.class.getSimpleName();

    @NonNull
    private final ContactContract.View mContactsView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private PublishSubject<String> mPublishSubject;

    private ContactRepository mContactRepository;

    public ContactPresenter(@NonNull ContactContract.View ticketsView, Application application) {
        this.mContactsView = ticketsView;

        mCompositeDisposable = new CompositeDisposable();
        mContactsView.setPresenter(this);
        mContactRepository = new ContactRepository(application);
    }


    @Override
    public void subscribe() {
        loadContacts();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


    @Override
    public void searchContacts(String keyword) {
        if (mPublishSubject == null) {
            mPublishSubject = PublishSubject.create();
            mCompositeDisposable.add(mPublishSubject
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .map(text -> text.trim())
                    .distinctUntilChanged()
                    .filter(text -> !TextUtils.isEmpty(text))
                    .switchMap(text ->
                            mContactRepository.searchContacts(text)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(contacts -> mContactsView.showContacts(contacts),
                            throwable -> mContactsView.showError(throwable))
            );
        }

        mPublishSubject.onNext(keyword);
    }

    @Override
    public void loadContacts() {
        mCompositeDisposable.add(mContactRepository.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> mContactsView.showContacts(contacts),
                        throwable -> mContactsView.showError(throwable))
        );
    }
}
