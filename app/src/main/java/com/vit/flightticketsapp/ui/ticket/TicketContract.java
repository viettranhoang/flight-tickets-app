package com.vit.flightticketsapp.ui.ticket;

import android.widget.EditText;

import com.vit.flightticketsapp.data.model.Ticket;
import com.vit.flightticketsapp.ui.base.BasePresenter;
import com.vit.flightticketsapp.ui.base.BaseView;

import java.util.List;

public interface TicketContract {

    interface View extends BaseView<Presenter> {

        void showTickets(List<Ticket> tickets);

        void showTicketPrice(Ticket ticket);

        void showTicketSearch(String text);

        void showError(Throwable e);
    }

    interface Presenter extends BasePresenter {

        void loadTickets(String from, String to);

        void searchTickets(EditText input);
    }
}
