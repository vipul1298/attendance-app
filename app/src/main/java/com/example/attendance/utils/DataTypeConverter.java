package com.example.attendance.utils;

import androidx.room.TypeConverter;

import com.example.attendance.dataModel.Semester;
import com.example.attendance.dataModel.Subject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DataTypeConverter  {

   static Gson gson = new Gson();

    @TypeConverter
    public static List<Subject> stringToSubjects(String data){
        if(data == null){
            return Collections.emptyList();
        }

        Type  listType = new TypeToken<List<Subject>>(){}.getType();

        return gson.fromJson(data,listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Subject> subjects) {
        return gson.toJson(subjects);
    }

}
