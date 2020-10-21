package com.example.attendance.dataModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SemesterDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSemester(Semester semester);


    @Transaction
    @Delete
    void deleteSemester(Semester semester);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSemester(Semester semester);

    @Transaction
    @Query("SELECT * FROM attendance where id= :semesterId")
    LiveData<SemesterDetail> getSemester(int semesterId);


    @Transaction
    @Query("SELECT * FROM attendance")
    LiveData<List<SemesterDetail>> getSemesters();

    @Transaction
    @Query("DELETE FROM attendance")
    void deleteAll();

    // Subject

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubject(Subject subject);

    @Transaction
    @Delete
    void deleteSubject(Subject subject);

    @Transaction
    @Update
    void updateSubject(Subject subject);


    @Transaction
    @Query("SELECT * FROM attendance where id LIKE :semId")
    LiveData<List<SemesterDetail>> getSubjectsBySemesterId(int semId);

}
