package com.example.mydiabetesdiary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Basal.class}, version = 1)
public abstract class BasalDatabase extends RoomDatabase {
    public abstract BasalDao basalDao();
}

