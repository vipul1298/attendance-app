package com.example.attendance.dataModel;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "subjects")
public class Subject{

    @PrimaryKey(autoGenerate = true)
    int id;

    String subName;
    int present;
    int absent;

    public Subject() {
    }

    @Ignore
    public Subject(String subName, int present, int absent) {
        this.subName = subName;
        this.present = present;
        this.absent = absent;
    }



    @ForeignKey(entity = Semester.class,
          parentColumns = "id",
            childColumns = "semesterId",
            onDelete = CASCADE
    )

    private long semesterId;

    public long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(long semesterId) {
        this.semesterId = semesterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }
}
