package com.example.attendance;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.attendance.dataModel.Semester;
import com.example.attendance.dataModel.SemesterDao;
import com.example.attendance.dataModel.SemesterDatabase;
import com.example.attendance.dataModel.SemesterDetail;
import com.example.attendance.dataModel.Subject;

import java.util.List;


public class SemesterRepository {

    private SemesterDao semesterDao;
    private LiveData<List<SemesterDetail>> allSem;



    public SemesterRepository(Application application){
        SemesterDatabase semesterDatabase =SemesterDatabase.getInstance(application);
        semesterDao=semesterDatabase.semesterDao();
        allSem=semesterDao.getSemesters();
    }

    public void insertSem(Semester semester){new InsertSemAsyncTask(semesterDao).execute(semester);}


    public void deleteSem(Semester semester){new DeleteSemAsyncTask(semesterDao).execute(semester);}



    public void updateSem(Semester semester){new UpdateSemAsyncTask(semesterDao).execute(semester);}

    public void insertSub(Subject subject){new InsertSubAsyncTask(semesterDao).execute(subject);}

    public void deleteSub(Subject subject){new DeleteSubAsyncTask(semesterDao).execute(subject);}

    public void updateSub(Subject subject){new UpdateSubAsyncTask(semesterDao).execute(subject);}


    public LiveData<SemesterDetail> getSemester(int semesterId){
        return semesterDao.getSemester(semesterId);
    }

    public LiveData<List<SemesterDetail>> getSubjectsBySemesterId(int semId){
        return semesterDao.getSubjectsBySemesterId(semId);
    }

    public LiveData<List<SemesterDetail>> getAllSemesters(){
        return allSem;
    }

    public void deleteAllSemesters(){new DeleteAllSemesterAsyncTask(semesterDao).execute();}


    private static class InsertSemAsyncTask extends AsyncTask<Semester,Void,Void>{

        private SemesterDao semesterDao;

        private InsertSemAsyncTask(SemesterDao semesterDao){this.semesterDao=semesterDao;}

        @Override
        protected Void doInBackground(Semester... semesters) {
            semesterDao.insertSemester(semesters[0]);
            return null;
        }
    }

//

    private static class DeleteSemAsyncTask extends AsyncTask<Semester,Void,Void>{

        private SemesterDao semesterDao;

        private DeleteSemAsyncTask(SemesterDao semesterDao){this.semesterDao=semesterDao;}

        @Override
        protected Void doInBackground(Semester... semesters) {
            semesterDao.deleteSemester(semesters[0]);
            return null;
        }
    }



    private static class UpdateSemAsyncTask extends AsyncTask<Semester,Void,Void>{

        private SemesterDao semesterDao;

        private UpdateSemAsyncTask(SemesterDao semesterDao){this.semesterDao=semesterDao;}

        @Override
        protected Void doInBackground(Semester... semesters) {
            semesterDao.updateSemester(semesters[0]);
            return null;
        }
    }


    private static class InsertSubAsyncTask extends AsyncTask<Subject,Void,Void>{

        private SemesterDao subjectDao;

        private InsertSubAsyncTask(SemesterDao subjectDao){this.subjectDao=subjectDao;}

        @Override
        protected Void doInBackground(Subject... subjects) {
            subjectDao.insertSubject(subjects[0]);
            return null;
        }
    }

    private static class DeleteSubAsyncTask extends AsyncTask<Subject,Void,Void>{

        private SemesterDao subjectDao;

        private DeleteSubAsyncTask(SemesterDao subjectDao){this.subjectDao=subjectDao;}

        @Override
        protected Void doInBackground(Subject... subjects) {
            subjectDao.deleteSubject(subjects[0]);
            return null;
        }
    }

    private static class UpdateSubAsyncTask extends AsyncTask<Subject,Void,Void>{

        private SemesterDao subjectDao;

        private UpdateSubAsyncTask(SemesterDao subjectDao){this.subjectDao=subjectDao;}

        @Override
        protected Void doInBackground(Subject... subjects) {
            subjectDao.updateSubject(subjects[0]);
            return null;
        }
    }
//

    private static class DeleteAllSemesterAsyncTask extends AsyncTask<Void,Void,Void>{

        private SemesterDao semesterDao;

        private DeleteAllSemesterAsyncTask(SemesterDao semesterDao){this.semesterDao=semesterDao;}

        @Override
        protected Void doInBackground(Void... voids) {
            semesterDao.deleteAll();
            return null;
        }
    }

}
