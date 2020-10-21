package com.example.attendance.dataModel;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SemesterDetail {

    public SemesterDetail() {
    }

    @Embedded
    private Semester semester;

    @Relation(parentColumn = "id",entityColumn = "semesterId",entity = Subject.class)
    private List<Subject> subjects;


    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
