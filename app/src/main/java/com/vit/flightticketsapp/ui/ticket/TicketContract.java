package com.vit.flightticketsapp.ui.ticket;

import com.vit.flightticketsapp.ui.base.BasePresenter;
import com.vit.flightticketsapp.ui.base.BaseView;

public interface TicketContract {

    interface View extends BaseView<Presenter> {
        void showError(Throwable e);
    }

    interface Presenter extends BasePresenter {

    }
}
