package com.vit.flightticketsapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.vit.flightticketsapp.data.local.DBConstants;

@Entity(tableName = DBConstants.Contact.TABLE_NAME)
public class Contact {

    @ColumnInfo(name = DBConstants.Contact.COLUMN_NAME)
    private String name;

    @SerializedName("image")
    @ColumnInfo(name = DBConstants.Contact.COLUMN_PROFILEIMAGE)
    private String profileImage;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DBConstants.Contact.COLUMN_PHONE)
    private String phone;

    @ColumnInfo(name = DBConstants.Contact.COLUMN_EMAIL)
    private String email;

    public Contact(String name, String profileImage, String phone, String email) {
        this.name = name;
        this.profileImage = profileImage;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Checking contact equality against email
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof Contact)) {
            return ((Contact) obj).getEmail().equalsIgnoreCase(email);
        }
        return false;
    }
}
