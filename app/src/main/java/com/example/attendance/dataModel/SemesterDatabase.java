package com.example.attendance.dataModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.ArrayList;

@Database(entities = {Semester.class,Subject.class} ,version = 4)
public abstract class SemesterDatabase extends RoomDatabase {

    public abstract SemesterDao semesterDao();

    public static SemesterDatabase instance;

    public static synchronized SemesterDatabase getInstance(Context context){

        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    SemesterDatabase.class,"semester_database"
            ).fallbackToDestructiveMigration()
                    .addCallback(semCallback)
                    .build();
        }
        return instance;
    }

    private static SemesterDatabase.Callback semCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private SemesterDao semesterDao;


        private PopulateDbAsyncTask(SemesterDatabase semesterDatabase) {semesterDao=semesterDatabase.semesterDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            semesterDao.insertSemester(new Semester("Semester 1",new ArrayList<Subject>(){{
                add(new Subject("Operating System",0,0));
            }}));
            return null;
        }
    }
}
