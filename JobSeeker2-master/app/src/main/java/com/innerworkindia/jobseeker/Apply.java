package com.innerworkindia.jobseeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.innerworkindia.jobseeker.API.LoginApi;
import com.innerworkindia.jobseeker.Model.BodyApplyIntern;
import com.innerworkindia.jobseeker.Model.BodyApplyJob;
import com.innerworkindia.jobseeker.Model.BodyGetJobs;
import com.innerworkindia.jobseeker.Model.BodyGetUserProfile;
import com.innerworkindia.jobseeker.Model.ResponseApplyIntern;
import com.innerworkindia.jobseeker.Model.ResponseApplyJob;
import com.innerworkindia.jobseeker.Model.ResponseGetJobs;
import com.innerworkindia.jobseeker.Model.ResponseGetUserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apply extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LoginApi loginApi;
    Spinner spinner,y7;
    EditText t1,t2,t4,t5,t6,t8,t9;
    RadioGroup t3;
    Button b1;
    String jobid,userid,exp,gender;
    AlertDialog.Builder builder;
    ArrayAdapter<CharSequence> adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Retrofit retrofit;

    ProgressBar progressBar;
    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    private static final String KEY_UID="userid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        spinner = (Spinner) findViewById(R.id.spinner_experience);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.experience_spinnerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        progressBar=findViewById(R.id.progressBar6);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginApi = retrofit.create(LoginApi.class);

        builder=new AlertDialog.Builder(this);
        userid=getIntent().getStringExtra("User Id");
        jobid=getIntent().getStringExtra("Job Id");
        b1=findViewById(R.id.button_upload);
        t1=findViewById(R.id.edittext_candidateName);
        t2=findViewById(R.id.edittext_candidateEmail);
        t4=findViewById(R.id.edittext_candidatePhone);
        t5=findViewById(R.id.edittext_candidateQualification);
        t6=findViewById(R.id.edittext_candidateCity);
        t8=findViewById(R.id.edittext_skills);
        t9=findViewById(R.id.edittext_interests);
        t3=findViewById(R.id.radiogroup_gender);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Call<ResponseGetUserProfile> call1 = loginApi.getUserProfile(new BodyGetUserProfile(Integer.valueOf(sharedPreferences.getString(KEY_UID,null))));
                call1.enqueue(new Callback<ResponseGetUserProfile>() {
                    @Override
                    public void onResponse(Call<ResponseGetUserProfile> call1, Response<ResponseGetUserProfile> response) {
                        if (!response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Continue").setTitle("Fetch UnSuccessful");
                            builder.setCancelable(true);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        ResponseGetUserProfile responseLoginUser = response.body();
                        if (responseLoginUser.getName() == null) {

                            progressBar.setVisibility(View.GONE);
                            builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Continue").setTitle("Fetch Unsuccessful. Please try again later.");
                            builder.setCancelable(true);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            if(responseLoginUser.isResume_uploaded()  && responseLoginUser.isPhoto_uploaded()){
                                int a=t3.getCheckedRadioButtonId();
                                if(a==R.id.male){
                                    gender="Male";
                                }else if(a==R.id.female){
                                    gender="Female";
                                }else if(a==R.id.others){
                                    gender="Others";
                                }else{
                                    gender="Others";
                                }
                                if(getIntent().hasExtra("Type")){
                                    String t=getIntent().getStringExtra("Type");
                                    if(t.equals("Internship")){
                                        String s1= String.valueOf(Long.valueOf(t4.getText().toString()));
                                        Log.i("Phone no",s1);

                                        String s2= String.valueOf(Long.valueOf(userid));
                                        Log.i("User Id",s2);

                                        String s3= String.valueOf(Long.valueOf(jobid));
                                        Log.i("Job Id",s3);
                                        Intern();
                                    }else if(t.equals("Job")){
                                        Job();
                                    }
                                }
                            }else{
                                builder.setMessage("Cannot Apply unless both resume and profile picture are updated in profile page. Please Try Again after updating. Press OK to Continue").setTitle("Missing Profile Picture and Resume");
                                builder.setCancelable(true);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        }
                        return;//Toast.makeText(getContext(), "Received token: " + responseLoginUser.getToken(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseGetUserProfile> call1, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Continue.").setTitle("Fetch UnSuccessful");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        //Toast.makeText(getContext(), "Login Failed. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

    }

    public void Intern(){
        Call<ResponseApplyIntern> call = loginApi.applyIntern(new BodyApplyIntern(t1.getText().toString(),gender,t6.getText().toString(),t5.getText().toString(),t2.getText().toString(),Long.valueOf(t4.getText().toString()),t8.getText().toString(),t9.getText().toString(),exp,Long.valueOf(jobid),Long.valueOf(userid)));
        call.enqueue(new Callback<ResponseApplyIntern>() {
            @Override
            public void onResponse(Call<ResponseApplyIntern> call, Response<ResponseApplyIntern> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to apply at the moment. Please try again later").setTitle("Apply Unsuccessful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                ResponseApplyIntern responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Press Ok to go back to home screen.").setTitle("Applied");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Apply.this,HomeActivity.class));
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to apply at the moment. Please try again later").setTitle("Apply Unsuccessful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return;//Toast.makeText(getContext(), "Received token: " + responseLoginUser.getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseApplyIntern> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                builder.setMessage("Unable to apply at the moment. Please try again later").setTitle("Apply UnSuccessful");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                //Toast.makeText(getContext(), "Login Failed. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    public void Job(){
        Call<ResponseApplyJob> call = loginApi.applyJob(new BodyApplyJob(t1.getText().toString(),gender,t6.getText().toString(),t5.getText().toString(),t2.getText().toString(),Long.valueOf(t4.getText().toString()),t8.getText().toString(),t9.getText().toString(),exp,Long.valueOf(jobid),Long.valueOf(userid)));
        call.enqueue(new Callback<ResponseApplyJob>() {
            @Override
            public void onResponse(Call<ResponseApplyJob> call, Response<ResponseApplyJob> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to apply at the moment. Please try again later").setTitle("Apply UnSuccessful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                ResponseApplyJob responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Press Ok to go back to home screen.").setTitle("Applied");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Apply.this,HomeActivity.class));
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to apply at the moment. Please try again later").setTitle("Apply Unsuccessful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return;//Toast.makeText(getContext(), "Received token: " + responseLoginUser.getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseApplyJob> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                builder.setMessage("Unable to apply at the moment. Please try again later").setTitle("Apply UnSuccessful");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                //Toast.makeText(getContext(), "Login Failed. " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        exp=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}