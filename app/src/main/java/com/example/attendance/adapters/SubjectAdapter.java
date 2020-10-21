package com.example.attendance.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.R;
import com.example.attendance.SubjectActivity;
import com.example.attendance.UpdateSubjectActivity;
import com.example.attendance.dataModel.Subject;

import java.util.List;

import static com.example.attendance.UpdateSubjectActivity.EXTRA_ABS;
import static com.example.attendance.UpdateSubjectActivity.EXTRA_ID;
import static com.example.attendance.UpdateSubjectActivity.EXTRA_PRES;
import static com.example.attendance.UpdateSubjectActivity.EXTRA_TITLE;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    List<Subject> subjectList;
    Context context;
    OnItemClickListener listener;

    public SubjectAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    public SubjectAdapter() {
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item,parent,false);
        final SubjectAdapter.ViewHolder sViewHolder = new SubjectAdapter.ViewHolder(view);
        return sViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {
        Subject subject =subjectList.get(position);
        holder.title.setText(subject.getSubName());
        holder.pres.setText("Present: "+subject.getPresent());
        holder.abs.setText("Absent: "+subject.getAbsent());

        if(subject.getPresent()==0 && subject.getAbsent()==0){
            holder.pAttendance.setTextColor(Color.RED);
            holder.pAttendance.setText(0+"%");
        }else {
            double p = (double) subject.getPresent();
            double a = (double) subject.getAbsent();
            double per_att = p/(p+a)*100;
            if(per_att>=75){
                holder.pAttendance.setTextColor(Color.rgb(0,100,0));
            }else {
                holder.pAttendance.setTextColor(Color.RED);
            }
            holder.pAttendance.setText((int)per_att + "%");
        }
    }

    @Override
    public int getItemCount() {
        return subjectList==null ? 0 : subjectList.size();
    }

    public void setSubjects(List<Subject> subjects){
        this.subjectList=subjects;
        notifyDataSetChanged();
    }

    public Subject getSubjectAt(int position){return subjectList.get(position);}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,pres,abs,pAttendance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.subject_title);
            pres=itemView.findViewById(R.id.present);
            abs=itemView.findViewById(R.id.absent);
            pAttendance=itemView.findViewById(R.id.attendance_percent);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnitemClick(subjectList.get(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void OnitemClick(Subject subject);
    }

    public void setOnitemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
