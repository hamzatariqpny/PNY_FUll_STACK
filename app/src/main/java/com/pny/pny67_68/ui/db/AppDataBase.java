package com.pny.pny67_68.ui.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    // Dao reference
    public abstract ContactDao contactDao();

    public static AppDataBase appDataBase = null;

    // returns app database reference
    public static synchronized AppDataBase getAppDataBase(Context context) {

        if (appDataBase == null) {

            appDataBase = Room.databaseBuilder(context,
                    AppDataBase.class, "database-pny")
                    .allowMainThreadQueries()
                    .build();

        }

        return appDataBase;

    }
}
