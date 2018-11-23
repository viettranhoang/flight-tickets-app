package com.vit.flightticketsapp.ui.ticket;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.vit.flightticketsapp.R;
import com.vit.flightticketsapp.data.model.Ticket;
import com.vit.flightticketsapp.ui.base.BaseActivity;
import com.vit.flightticketsapp.ui.ticket.adapter.TicketAdapter;
import com.vit.flightticketsapp.ui.ticket.listener.OnClickTicketItemListener;

import butterknife.BindView;

public class TicketActivity extends BaseActivity implements TicketContract.View, OnClickTicketItemListener {

    private static final String from = "DEL";
    private static final String to = "HYD";

    @BindView(R.id.list_ticket)
    RecyclerView mRcvTicket;

    @BindView(R.id.layout_root)
    LinearLayout mLayoutRoot;

    private TicketAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ticket_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
    }

    @Override
    public void setPresenter(TicketContract.Presenter presenter) {

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(from + " > " + to);
    }

    private void initRcv() {
        mRcvTicket.setLayoutManager(new LinearLayoutManager(this));
        mRcvTicket.setHasFixedSize(true);
        mRcvTicket.setItemAnimator(new DefaultItemAnimator());
        mRcvTicket.setAdapter(mAdapter);
    }

    @Override
    public void showError(Throwable e) {
        Log.e(TAG, "showError: " + e.getMessage());

        showSnackbar(mLayoutRoot, e.getMessage());
    }

    @Override
    public void onClickTicketItem(Ticket ticket) {
        showToast(ticket.getAirline().getName());
    }
}
