package com.example.mydiabetesdiary;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "basals" )
public class Basal {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "start_time")
    public String start_time;

    @ColumnInfo (name = "end_time")
    public String end_time;

    @ColumnInfo (name = "basal")
    public String basal;

}
