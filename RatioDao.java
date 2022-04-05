package com.example.mydiabetesdiary;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RatioDao {

    @Query("INSERT INTO ratios (start_time,end_time,ratio) VALUES ('Enter Time', 'Enter Time','Enter Ratio') ")
    void createContents();

    @Query("Select * FROM ratios ORDER BY start_time")
    List<Ratio> getAll();

    @Query("UPDATE ratios SET start_time = :start WHERE id = :id")
    void saveStart(String start, int id);

    @Query("Update ratios SET end_time =:end WHERE id = :id")
    void saveEnd(String end, int id);

    @Query("UPDATE ratios SET ratio = :ratio WHERE id = :id")
    void saveRatio(String ratio,int id);

}
