package com.vit.flightticketsapp.data.local;

public class DBConstants {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact_database";

    public interface Contact {
        String TABLE_NAME = "contacts";
        String COLUMN_NAME = "name";
        String COLUMN_PROFILEIMAGE = "profileImage";
        String COLUMN_PHONE = "phone";
        String COLUMN_EMAIL = "email";
    }
}
