package com.example.mydiabetesdiary;

import androidx.room.Dao;
import androidx.room.Query;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Dao
public interface NoteDao {

    @Query("INSERT INTO notes (blood_glucose,carbs,bolus,contents,title,date,time) VALUES ('','','','',:title,:date,:time) ")
    void createContents(String title, String date,String time);

    @Query("SELECT * FROM notes ORDER BY date DESC,time DESC")
    List<Note> getAll();

    @Query("UPDATE notes Set contents = :contents WHERE id = :id")
    void saveContent(String contents, int id);

    @Query("UPDATE notes Set blood_glucose = :bg WHERE id = :id ")
    void saveBG(String bg, int id);

    @Query("UPDATE notes Set title = :title WHERE id = :id ")
    void saveTitle(String title, int id);

    @Query("UPDATE notes Set carbs = :carbs WHERE id = :id")
    void saveCarbs(String carbs, int id);

    @Query("UPDATE notes Set bolus = :bolus WHERE id = :id")
    void saveBolus(String bolus, int id);

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(int id);

    @Query("SELECT ROUND(AVG(blood_glucose),1) FROM notes WHERE NOT '' ")
    String bgAvg();

    @Query("SELECT ROUND(AVG(carbs),0)FROM notes WHERE NOT ''")
    String carbsAvg();

    @Query("SELECT ROUND(AVG(bolus),2) FROM notes WHERE NOT ''")
    String bolusAvg();

    @Query("SELECT id, time, blood_glucose FROM notes ORDER BY time ASC LIMIT 30")
    List<Note> graphBG();

    @Query("SELECT id, blood_glucose FROM notes WHERE NOT ''")
    List<Note> normalBgCount();

}