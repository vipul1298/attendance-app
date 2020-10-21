package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.attendance.ViewModel.SemesterViewModel;
import com.example.attendance.adapters.SemesterAdapter;
import com.example.attendance.dataModel.Semester;
import com.example.attendance.dataModel.SemesterDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private SemesterViewModel semesterViewModel;
    private RecyclerView recyclerView;
    List<SemesterDetail> semestersList =new ArrayList<>();
    SemesterAdapter semesterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Semester");

        recyclerView=findViewById(R.id.recycler_semester);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        FloatingActionButton floatingActionButton = findViewById(R.id.add_semester);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSemester();
            }
        });



        semesterViewModel = new ViewModelProvider(this).get(SemesterViewModel.class);

        semesterViewModel.getAllSemesters().observe(this, new Observer<List<SemesterDetail>>() {
            @Override
            public void onChanged(List<SemesterDetail> semesters) {
                if(semesters.size()==0)
                    Toasty.info(getApplicationContext(),"Start adding semester here!").show();
                semestersList=semesters;
                semesterAdapter = new SemesterAdapter(semestersList,getApplicationContext());
                recyclerView.setAdapter(semesterAdapter);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                semesterViewModel.deleteSem(semesterAdapter.getSemesterAt(viewHolder.getAdapterPosition()));
                int pos =viewHolder.getAdapterPosition()+1;
                Toasty.success(MainActivity.this, "Semester " + pos + " deleted!", Toast.LENGTH_SHORT,true).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void addSemester() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add Semester");
        dialog.setIcon(R.drawable.ic_baseline_school_24);
        final EditText editText = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        editText.setHint("ex. Semester 1");
        dialog.setView(editText);
        dialog.setCancelable(true);
        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sem_name = editText.getText().toString();
                Semester semester = new Semester(sem_name,null);
                semesterViewModel.insertSem(semester);
                Toasty.success(getApplicationContext(),"Semester Added!",Toasty.LENGTH_SHORT,true).show();
            }
        });


        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_semester:
                semesterViewModel.deleteAllSemester();
                Toasty.normal(this, "All semester deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}