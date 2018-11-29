package com.vit.flightticketsapp.ui.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vit.flightticketsapp.R;
import com.vit.flightticketsapp.data.model.Contact;
import com.vit.flightticketsapp.ui.base.BaseActivity;
import com.vit.flightticketsapp.ui.contact.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class ContactActivity extends BaseActivity implements ContactContract.View{

    protected static final String TAG = ContactActivity.class.getSimpleName();

    public static void start(Context context) {
        Intent starter = new Intent(context, ContactActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.list_contact)
    RecyclerView mRcvContact;

    @BindView(R.id.layout_root)
    LinearLayout mLayoutRoot;

    @BindView(R.id.input_search)
    EditText mInputSearch;

    private ContactAdapter mAdapter;

    private ContactContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.contact_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initRcv();

        new ContactPresenter(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        mAdapter.setList(contacts);
    }

    @Override
    public void showError(Throwable e) {
        Log.e(TAG, "showError: " + e.getMessage());

        showSnackbar(mLayoutRoot, e.getMessage());
    }

    @Override
    public void setPresenter(ContactContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnTextChanged(R.id.input_search)
    void onClickSearch() {
        mPresenter.searchContacts(mInputSearch.getText().toString());
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact");
    }

    private void initRcv() {
        mAdapter = new ContactAdapter(new ArrayList<>(0));
        mRcvContact.setLayoutManager(new LinearLayoutManager(this));
        mRcvContact.setHasFixedSize(true);
        mRcvContact.setItemAnimator(new DefaultItemAnimator());
        mRcvContact.setAdapter(mAdapter);
    }
}
