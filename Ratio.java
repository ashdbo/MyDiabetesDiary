package com.example.mydiabetesdiary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ratios" )
public class Ratio {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "start_time")
    public String start_time;

    @ColumnInfo (name = "end_time")
    public String end_time;

    @ColumnInfo (name = "ratio")
    public String ratio;

}
