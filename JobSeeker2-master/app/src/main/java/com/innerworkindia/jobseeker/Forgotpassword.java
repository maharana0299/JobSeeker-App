package com.innerworkindia.jobseeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.innerworkindia.jobseeker.API.LoginApi;
import com.innerworkindia.jobseeker.Model.BodySendPassword;
import com.innerworkindia.jobseeker.Model.ResponseSendPassword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Forgotpassword extends AppCompatActivity {

    private static View view;
    private static EditText emailId;
    private static TextView submit, back;
    private static ProgressBar progressBarForgotPassword;


    AlertDialog.Builder builder;
    Retrofit retrofit;
    LoginApi loginApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        builder=new AlertDialog.Builder(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginApi = retrofit.create(LoginApi.class);

        emailId = (EditText)findViewById(R.id.registered_emailid);
        submit = (TextView)findViewById(R.id.forgot_button);
        back = (TextView)findViewById(R.id.backToLoginBtn);
        progressBarForgotPassword=(ProgressBar)findViewById(R.id.progressBar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarForgotPassword.setVisibility(View.VISIBLE);
                String getEmailId = emailId.getText().toString();

                // Pattern for email id validation
                Pattern p = Pattern.compile(Utils.regEx);

                // Match the pattern
                Matcher m = p.matcher(getEmailId);

                // First check if email id is not null else show error toast
                if (getEmailId.equals("") || getEmailId.length() == 0){
                    progressBarForgotPassword.setVisibility(View.GONE);
                    builder.setMessage("Please enter your Email Id.").setTitle("Empty Email Field");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();

                }
                // Check if email id is valid or not
                else if (!m.find()){
                    progressBarForgotPassword.setVisibility(View.GONE);
                    builder.setMessage("Please enter valid Email Id.").setTitle("Invalid Email-Id");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();

                }// Else submit email id and fetch passwod or do your stuff
                else{
                    Call<ResponseSendPassword> call = loginApi.sendPassword(new BodySendPassword(getEmailId));
                    call.enqueue(new Callback<ResponseSendPassword>() {
                        @Override
                        public void onResponse(Call<ResponseSendPassword> call, Response<ResponseSendPassword> response) {
                            if(!response.isSuccessful()){
                                progressBarForgotPassword.setVisibility(View.GONE);
                                builder.setMessage(response.message()).setTitle("Recovery Email Sent");
                                builder.setCancelable(true);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(Forgotpassword.this,LoginActivity.class));
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alert=builder.create();
                                alert.show();
                                //Toast.makeText(getContext(), "Failed to send password to mail ID. "+response.message(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            progressBarForgotPassword.setVisibility(View.GONE);
                            ResponseSendPassword responseSendPassword = response.body();
                            builder.setMessage(responseSendPassword.getMessage()).setTitle("Recovery Email Sent Response");
                            builder.setCancelable(true);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert=builder.create();
                            alert.show();
                        }

                        @Override
                        public void onFailure(Call<ResponseSendPassword> call, Throwable t) {
                            progressBarForgotPassword.setVisibility(View.GONE);
                            builder.setMessage("Failed to request server. Please Try Later").setTitle(t.getLocalizedMessage());
                            builder.setCancelable(true);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert=builder.create();
                            alert.show();
                            //Toast.makeText(getContext(), "Failed to request server. Try again later. ", Toast.LENGTH_SHORT).show();
                            Log.i("ForgetPassword_Fragment", "onFailure: " + t.getLocalizedMessage());
                        }
                    });
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forgotpassword.this,LoginActivity.class));
            }
        });
    }
}