package com.example.mydiabetesdiary;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

    @Dao
    public interface BasalDao {

        @Query("INSERT INTO basals (start_time,end_time,basal) VALUES ('Enter Time', 'Enter Time','Enter Basal') ")
        void createBasal();

        @Query("Select * FROM basals ORDER BY start_time")
        List<Basal> getAllBasal();

        @Query("UPDATE basals SET start_time = :start WHERE id = :id")
        void saveStartBasal(String start, int id);

        @Query("Update basals SET end_time =:end WHERE id = :id")
        void saveEndBasal(String end, int id);

        @Query("UPDATE basals SET basal = :basal WHERE id = :id")
        void saveBasal(String basal,int id);
    }
