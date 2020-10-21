package com.example.attendance.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.SubjectActivity;
import com.example.attendance.dataModel.Semester;
import com.example.attendance.dataModel.SemesterDetail;
import com.example.attendance.dataModel.Subject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.ViewHolder> {

    List<SemesterDetail> semesterList;
    Context context;

    public SemesterAdapter(List<SemesterDetail> semesterList, Context context) {
        this.semesterList = semesterList;
        this.context = context;
    }

    @NonNull
    @Override
    public SemesterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_item,parent,false);
        return new SemesterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterAdapter.ViewHolder holder, int position) {
      SemesterDetail semesterDetail = semesterList.get(position);
      Semester semester = new Semester(semesterDetail);
      holder.semester_text.setText(semester.getName());


    }

    @Override
    public int getItemCount() {
        return semesterList==null ? 0 :semesterList.size();
    }

    public Semester getSemesterAt(int position) {return semesterList.get(position).getSemester();}

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView semester_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semester_text=itemView.findViewById(R.id.sem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SemesterDetail semesterDetail = semesterList.get(getAdapterPosition());
                    Intent intent = new Intent(context,SubjectActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",semesterDetail.getSemester().getId());
                    context.startActivity(intent);

                }
            });

        }


    }


}
