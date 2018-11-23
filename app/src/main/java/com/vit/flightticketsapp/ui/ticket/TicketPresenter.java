package com.vit.flightticketsapp.ui.ticket;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

public class TicketPresenter implements TicketContract.Presenter {

    @NonNull
    private final TicketContract.View mTicketsView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public TicketPresenter(@NonNull TicketContract.View ticketsView) {
        this.mTicketsView = checkNotNull(ticketsView, "tasksView cannot be null!");

        mCompositeDisposable = new CompositeDisposable();
        mTicketsView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
