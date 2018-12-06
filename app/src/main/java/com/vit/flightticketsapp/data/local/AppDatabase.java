package com.vit.flightticketsapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.vit.flightticketsapp.data.local.dao.ContactDao;
import com.vit.flightticketsapp.data.model.Contact;

@Database(entities = Contact.class, version = DBConstants.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract ContactDao contactDao();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DBConstants.DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }
}
