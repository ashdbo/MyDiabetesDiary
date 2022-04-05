package com.example.mydiabetesdiary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Ratio.class}, version = 3)
public abstract class RatioDatabase extends RoomDatabase {
    public abstract RatioDao ratioDao();
}

