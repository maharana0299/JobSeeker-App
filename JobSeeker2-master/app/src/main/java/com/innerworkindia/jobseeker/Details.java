package com.innerworkindia.jobseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.innerworkindia.jobseeker.Model.JobPost;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    private static final String KEY_UID="userid";
    ArrayList<JobPost> c;
    Button apply;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18;

    String tt;
    String jobid,userid;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

         sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
         userid=sharedPreferences.getString(KEY_UID,null);

        t1=findViewById(R.id.textview_innerwork);
        if(getIntent().hasExtra("Company")){
            t1.setText(getIntent().getStringExtra("Company"));
        }
        t2=findViewById(R.id.textview_jobdetails);
         if(getIntent().hasExtra("Type")){
             String s=getIntent().getStringExtra("Type")+" Details";
             t2.setText(s);
         }
         t3=findViewById(R.id.textview_jobHeading);
         if(getIntent().hasExtra("Job Title")){
             t3.setText(getIntent().getStringExtra("Job Title"));
         }
         t4=findViewById(R.id.textview_companyname);
         if(getIntent().hasExtra("Company")){
             t4.setText(getIntent().getStringExtra("Company"));
         }
         t5=findViewById(R.id.textview_jobtype);
         if(getIntent().hasExtra("Type")){
             tt=getIntent().getStringExtra("Type");
             t5.setText(getIntent().getStringExtra("Type"));
         }
         t6=findViewById(R.id.textview_stipendType);
         if(getIntent().hasExtra("Max Salary")){
             String s=getIntent().getStringExtra("Max Salary");
             if(s.equals("Unpaid")){
                 t6.setText(s);
             }else{
                 String t="Paid";
                 t6.setText(t);
             }
         }
         t7=findViewById(R.id.textview_candidateType);
         if(getIntent().hasExtra("Experience")){
             t7.setText(getIntent().getStringExtra("Experience"));
         }
         t8=findViewById(R.id.textview_datePosted);
         if(getIntent().hasExtra("Date Time")){
             String s="Posted on "+getIntent().getStringExtra("Date Time");
             t8.setText(s);
         }
         t9=findViewById(R.id.textview_aboutCompanyName);
         if(getIntent().hasExtra("About Company")){
             t9.setText(getIntent().getStringExtra("About Company"));
         }
         t10=findViewById(R.id.textview_contactName);
         if(getIntent().hasExtra("Contact Person Name")){
             t10.setText(getIntent().getStringExtra("Contact Person Name"));
         }
         t11=findViewById(R.id.textview_contactPhone);
         if(getIntent().hasExtra("Contact Person Number")){
             t11.setText(getIntent().getStringExtra("Contact Person Number"));
         }
         t12=findViewById(R.id.textview_companyEmail);
         if(getIntent().hasExtra("Email")){
             t12.setText(getIntent().getStringExtra("Email"));
         }
         t13=findViewById(R.id.textview_jobDesc);
         if(getIntent().hasExtra("Type")){
             String s=getIntent().getStringExtra("Type");
             s=s+" Details";
             t13.setText(s);
         }
         t14=findViewById(R.id.textview_jobDescription);
         if(getIntent().hasExtra("Job Desc") && getIntent().hasExtra("Job Type")){
             String s="For "+getIntent().getStringExtra("Job Type")+". "+getIntent().getStringExtra("Job Desc");
             t14.setText(s);
         }
         t15=findViewById(R.id.textview_eduRequirementDescription);
         if(getIntent().hasExtra("Education")){
             t15.setText(getIntent().getStringExtra("Education"));
         }
         t16=findViewById(R.id.textview_skillReqDescription);
         if(getIntent().hasExtra("Skill")){
             t16.setText(getIntent().getStringExtra("Skill"));
         }
         t17=findViewById(R.id.textview_stipendamt);
         if(getIntent().hasExtra("Max Salary") && getIntent().hasExtra("Min Salary")){
             String s=getIntent().getStringExtra("Max Salary")+" - "+getIntent().getStringExtra("Min Salary");
             t17.setText(s);
         }
         t18=findViewById(R.id.textview_stipend);
         if(getIntent().hasExtra("Type")){
             String s=getIntent().getStringExtra("Type");
             if(s.equals("Job")){
                 String t="Salary";
                 t18.setText(t);
             }else{
                 String t="Stipend";
                 t18.setText(t);
             }
         }
        if(getIntent().hasExtra("Id")){
            jobid=getIntent().getStringExtra("Id");
        }
         apply=(Button)findViewById(R.id.button_applyonline);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Details.this,Apply.class);
                intent.putExtra("Job Id",jobid);
                intent.putExtra("User Id",userid);
                intent.putExtra("Type",tt);
                startActivity(intent);
            }
        });
    }
}