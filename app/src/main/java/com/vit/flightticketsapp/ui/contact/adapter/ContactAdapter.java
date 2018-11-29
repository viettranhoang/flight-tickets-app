package com.vit.flightticketsapp.ui.contact.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vit.flightticketsapp.R;
import com.vit.flightticketsapp.data.model.Contact;
import com.vit.flightticketsapp.ui.base.BaseViewHolder;
import com.vit.flightticketsapp.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> mContacts = new ArrayList<>();

    public ContactAdapter(List<Contact> mContacts) {
        this.mContacts = mContacts;
    }

    public List<Contact> getList() {
        return mContacts;
    }

    public void setList(List<Contact> mContacts) {
        this.mContacts = mContacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
        contactViewHolder.bindData(mContacts.get(i));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    class ContactViewHolder extends BaseViewHolder<Contact> {

        @BindView(R.id.text_name)
        TextView mTextName;

        @BindView(R.id.text_phone)
        TextView mTextPhone;

        @BindView(R.id.image_thumbnail)
        ImageView mImageThumbnail;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Contact contact) {
            mTextName.setText(contact.getName());
            mTextPhone.setText(contact.getPhone());
            GlideApp.with(itemView.getContext())
                    .load(contact.getProfileImage())
                    .circleCrop()
                    .into(mImageThumbnail);
        }
    }
}
