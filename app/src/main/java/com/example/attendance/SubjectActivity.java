package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.attendance.ViewModel.SemesterViewModel;
import com.example.attendance.adapters.SubjectAdapter;
import com.example.attendance.dataModel.Semester;
import com.example.attendance.dataModel.SemesterDetail;
import com.example.attendance.dataModel.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.attendance.UpdateSubjectActivity.EXTRA_ABS;
import static com.example.attendance.UpdateSubjectActivity.EXTRA_ID;
import static com.example.attendance.UpdateSubjectActivity.EXTRA_PRES;
import static com.example.attendance.UpdateSubjectActivity.EXTRA_TITLE;


public class SubjectActivity extends AppCompatActivity {

   public static final int EDIT_REQUEST_SUBJECT =1;

    private SemesterViewModel semesterViewModel;
    RecyclerView recyclerView;
    SubjectAdapter subjectAdapter;
    List<Subject> subjectList = new ArrayList<>();
    Semester mSemester;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        setTitle("Semester Subjects");

        recyclerView=findViewById(R.id.recycler_subject);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSemester = new Semester();

        semesterViewModel = new ViewModelProvider(this).get(SemesterViewModel.class);

        Intent i = getIntent();
        id = i.getIntExtra("id",-1);

        subjectAdapter = new SubjectAdapter();

        semesterViewModel.getSemester(id).observe(this, new Observer<SemesterDetail>() {
            @Override
            public void onChanged(SemesterDetail semesterDetail) {
                if(semesterDetail.getSubjects().size()==0)
                    Toasty.info(getApplicationContext(),"Start adding subjects here!",Toasty.LENGTH_LONG).show();
                subjectList=semesterDetail.getSubjects();
                subjectAdapter.setSubjects(subjectList);
                Log.d("MainActivity", "onCreate: "+subjectAdapter.getItemCount());

                recyclerView.setAdapter(subjectAdapter);
            }
        });


        FloatingActionButton floatingActionButton =findViewById(R.id.add_subject);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubject();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               semesterViewModel.deleteSub(subjectAdapter.getSubjectAt(viewHolder.getAdapterPosition()));
                Toasty.success(SubjectActivity.this, "Subject Deleted", Toast.LENGTH_SHORT,true).show();
            }
        }).attachToRecyclerView(recyclerView);

      subjectAdapter.setOnitemClickListener(new SubjectAdapter.OnItemClickListener() {
          @Override
          public void OnitemClick(Subject subject) {
              Intent intent = new Intent(SubjectActivity.this,UpdateSubjectActivity.class);
              intent.putExtra(EXTRA_ID,subject.getId());
              intent.putExtra(EXTRA_ABS,subject.getAbsent());
              intent.putExtra(EXTRA_TITLE,subject.getSubName());
              intent.putExtra(EXTRA_PRES,subject.getPresent());
              startActivityForResult(intent,EDIT_REQUEST_SUBJECT);
          }
      });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_REQUEST_SUBJECT && resultCode==RESULT_OK){
            int subId = data.getIntExtra(EXTRA_ID,-1);

            if(subId==-1){
                Toasty.info(this, "Subject can't be updated!", Toast.LENGTH_SHORT,true).show();
                return;
            }

             String subName = data.getStringExtra(EXTRA_TITLE);
            int abs =data.getIntExtra(EXTRA_ABS,0);
            int pres =data.getIntExtra(EXTRA_PRES,0);

            Subject subject = new Subject(subName,pres,abs);
            subject.setId(subId);
            subject.setSemesterId(id);

            semesterViewModel.updateSub(subject);
            Toasty.success(this, "Subject Updated!", Toast.LENGTH_SHORT,true).show();

        }else{
            Toasty.error(this, "Updation failed!", Toast.LENGTH_SHORT,true).show();
        }
    }



    private void addSubject() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add Subject");
        dialog.setIcon(R.drawable.ic_baseline_subject_24);
        final EditText editText = new EditText(this);
        editText.setHint("ex. Data Structures");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        dialog.setView(editText);
        dialog.setCancelable(true);
        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sub_name = editText.getText().toString();
                Subject subject = new Subject(sub_name,0,0);
                List<Subject> subjectList = new ArrayList<>();
                subjectList.add(subject);
                insertSubjectsforSemester(subjectList);
                Toasty.success(getApplicationContext(),"Subject Added!",Toasty.LENGTH_SHORT,true).show();
            }
        });


        dialog.show();
    }

    private void insertSubjectsforSemester(List<Subject> subjects){
        for(Subject subject: subjects){
            subject.setSemesterId(id);
            semesterViewModel.insertSub(subject);
        }
    }



}