package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.attendance.ViewModel.SemesterViewModel;
import com.example.attendance.dataModel.Subject;

public class UpdateSubjectActivity extends AppCompatActivity {

    public static final String EXTRA_ID=
            "com.example.attendance.EXTRA_ID";
    public static final String EXTRA_TITLE=
            "com.example.attendance.EXTRA_TITLE";
    public static final String EXTRA_PRES=
            "com.example.attendance.EXTRA_DESC";
    public static final String EXTRA_ABS=
            "com.example.attendance.EXTRA_PRIORITY";

    private EditText sName,sPres,sAbs;
    private Button save_btn;
    private SemesterViewModel semesterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subject);

        setTitle("Update");

        sName=findViewById(R.id.update_subject);
        sPres=findViewById(R.id.update_present);
        sAbs=findViewById(R.id.update_absent);
        save_btn=findViewById(R.id.update_btn);

        semesterViewModel = new ViewModelProvider(this).get(SemesterViewModel.class);

        Intent i = getIntent();

        if(i.hasExtra(EXTRA_ID)){
            sName.setText(i.getStringExtra(EXTRA_TITLE));
            sPres.setText(i.getIntExtra(EXTRA_PRES,0)+"");
            sAbs.setText(i.getIntExtra(EXTRA_ABS,0)+"");
        }


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pSub = sName.getText().toString();
                int pAbs =Integer.parseInt(sAbs.getText().toString());
                int pPres = Integer.parseInt(sPres.getText().toString());

                Intent data = new Intent();
                data.putExtra(EXTRA_TITLE,pSub);
                data.putExtra(EXTRA_ABS,pAbs);
                data.putExtra(EXTRA_PRES,pPres);

                int id = getIntent().getIntExtra(EXTRA_ID,-1);
                if(id!=-1){
                    data.putExtra(EXTRA_ID,id);
                }
                setResult(RESULT_OK,data);
                finish();

            }
        });
    }
}