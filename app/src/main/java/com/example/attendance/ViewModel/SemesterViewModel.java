package com.example.attendance.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.attendance.SemesterRepository;
import com.example.attendance.dataModel.Semester;
import com.example.attendance.dataModel.SemesterDetail;
import com.example.attendance.dataModel.Subject;

import java.util.List;

public class SemesterViewModel extends AndroidViewModel {

    private SemesterRepository semesterRepository;
    private LiveData<List<SemesterDetail>> getAllSemesters;

    public SemesterViewModel(@NonNull Application application) {
        super(application);
        semesterRepository=new SemesterRepository(application);
        getAllSemesters=semesterRepository.getAllSemesters();
    }

    public void insertSem(Semester semester){
        semesterRepository.insertSem(semester);
    }

    public void insertSub(Subject subject){
        semesterRepository.insertSub(subject);
    }

    public void deleteSem(Semester semester){
        semesterRepository.deleteSem(semester);
    }

    public void deleteSub(Subject subject){
        semesterRepository.deleteSub(subject);
    }

    public void updateSub(Subject subject){
        semesterRepository.updateSub(subject);
    }

    public void deleteAllSemester(){
        semesterRepository.deleteAllSemesters();
    }

    public LiveData<List<SemesterDetail>> getAllSemesters(){
        return getAllSemesters;
    }


    public LiveData<SemesterDetail> getSemester(int semesterId){
       return semesterRepository.getSemester(semesterId);
    }


}
