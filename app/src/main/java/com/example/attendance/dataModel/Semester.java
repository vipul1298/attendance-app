package com.example.attendance.dataModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "attendance")
public class Semester  {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "name")
    String name;

    @Ignore
    List<Subject> subjects=null;

    public Semester() {
    }

    @Ignore
    public Semester(String name, List<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public Semester(SemesterDetail semesterDetail){
        this.id = semesterDetail.getSemester().getId();
        this.name = semesterDetail.getSemester().getName();
        this.subjects = semesterDetail.getSubjects();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }


    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
