package com.example.mydiabetesdiary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 3)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
