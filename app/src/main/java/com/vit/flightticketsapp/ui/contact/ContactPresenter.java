package com.vit.flightticketsapp.ui.contact;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.vit.flightticketsapp.data.model.Contact;
import com.vit.flightticketsapp.data.remote.ApiClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ContactPresenter implements ContactContract.Presenter {

    private static final String TAG = ContactPresenter.class.getSimpleName();

    @NonNull
    private final ContactContract.View mContactsView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private PublishSubject<String> mPublishSubject;

    public ContactPresenter(@NonNull ContactContract.View ticketsView) {
        this.mContactsView = ticketsView;

        mCompositeDisposable = new CompositeDisposable();
        mContactsView.setPresenter(this);
    }


    @Override
    public void subscribe() {
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
                    .switchMapSingle(text ->
                            ApiClient.getTicketService().getContacts(null, text)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<Contact>>() {
                        @Override
                        public void onNext(List<Contact> contacts) {
                            mContactsView.showContacts(contacts);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mContactsView.showError(e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        }

        mPublishSubject.onNext(keyword);
    }
}
