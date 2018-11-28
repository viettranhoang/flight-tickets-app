package com.vit.flightticketsapp.ui.ticket;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.vit.flightticketsapp.data.model.Price;
import com.vit.flightticketsapp.data.model.Ticket;
import com.vit.flightticketsapp.data.remote.ApiClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class TicketPresenter implements TicketContract.Presenter {

    private static final String TAG = TicketPresenter.class.getSimpleName();

    @NonNull
    private final TicketContract.View mTicketsView;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public TicketPresenter(@NonNull TicketContract.View ticketsView) {
        this.mTicketsView = ticketsView;

        mCompositeDisposable = new CompositeDisposable();
        mTicketsView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadTickets(String from, String to) {
        ConnectableObservable<List<Ticket>> ticketsObservable = getTickets(from, to).replay();

        /**
         * Fetching all tickets first
         * Observable emits List<Ticket> at once
         * All the items will be added to RecyclerView
         * */
        mCompositeDisposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Ticket>>() {

                            @Override
                            public void onNext(List<Ticket> tickets) {
                                mTicketsView.showTickets(tickets);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mTicketsView.showError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));

        /**
         * Fetching individual ticket price
         * First FlatMap converts single List<Ticket> to multiple emissions
         * Second FlatMap makes HTTP call on each Ticket emission
         * */
        mCompositeDisposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        /**
                         * Converting List<Ticket> emission to single Ticket emissions
                         * */
                        .flatMap(new Function<List<Ticket>, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(List<Ticket> tickets) throws Exception {
                                return Observable.fromIterable(tickets);
                            }
                        })
                        /**
                         * Fetching price on each Ticket emission
                         * */
                        .flatMap(new Function<Ticket, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(Ticket ticket) throws Exception {
                                return getPriceObservable(ticket);
                            }
                        })
                        .subscribeWith(new DisposableObserver<Ticket>() {
                            @Override
                            public void onNext(Ticket ticket) {
                                mTicketsView.showTicketPrice(ticket);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mTicketsView.showError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );

        // Calling connect to start emission
        ticketsObservable.connect();

    }

    @Override
    public void searchTickets(String keyword, EditText input) {

        mCompositeDisposable.add(RxTextView.textChangeEvents(input)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        mTicketsView.showTicketSearch(textViewTextChangeEvent.text().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTicketsView.showError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    /**
     * Making Retrofit call to fetch all tickets
     */
    private Observable<List<Ticket>> getTickets(String from, String to) {
        return ApiClient.getTicketService().getTickets(from, to)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Making Retrofit call to get single ticket price
     * get price HTTP call returns Price object, but
     * map() operator is used to change the return type to Ticket
     */
    private Observable<Ticket> getPriceObservable(final Ticket ticket) {
        return ApiClient.getTicketService()
                .getPrice(ticket.getFlightNumber(), ticket.getFrom(), ticket.getTo())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Price, Ticket>() {
                    @Override
                    public Ticket apply(Price price) throws Exception {
                        ticket.setPrice(price);
                        return ticket;
                    }
                });
    }

}
