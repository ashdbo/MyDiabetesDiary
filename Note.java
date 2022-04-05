package com.example.mydiabetesdiary;

import androidx.annotation.InspectableProperty;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "notes")
public class Note {

    @PrimaryKey
    public int id;

    @ColumnInfo (name = "title")
    public String title;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "blood_glucose")
    public String BG;

    @ColumnInfo(name = "carbs")
    public String carbs;

    @ColumnInfo(name = "bolus")
    public String bolus;

    @ColumnInfo(name = "contents")
    public String contents;

    public int getId(){
        return id;
    }
}

