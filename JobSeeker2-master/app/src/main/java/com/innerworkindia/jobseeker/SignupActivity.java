package com.innerworkindia.jobseeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.innerworkindia.jobseeker.API.LoginApi;
import com.innerworkindia.jobseeker.Model.BodySignUpUser;
import com.innerworkindia.jobseeker.Model.ResponseSignUpUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private static EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions, isIntern, isJob;
    private static ProgressBar progressBar;

    Retrofit retrofit;
    LoginApi loginApi;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        builder=new AlertDialog.Builder(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginApi = retrofit.create(LoginApi.class);

        fullName = (EditText)findViewById(R.id.fullName);
        emailId = (EditText) findViewById(R.id.userEmailId);
        mobileNumber = (EditText)findViewById(R.id.mobileNumber);
        location = (EditText)findViewById(R.id.location);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        signUpButton = (Button)findViewById(R.id.signUpBtn);
        login = (TextView)findViewById(R.id.already_user);
        terms_conditions = (CheckBox)findViewById(R.id.terms_conditions);
        isJob = (CheckBox)findViewById(R.id.job);
        isIntern = (CheckBox)findViewById(R.id.intern);
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }
    private void checkValidation() {
        progressBar.setVisibility(View.VISIBLE);
        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0){
            progressBar.setVisibility(View.GONE);
            builder.setMessage("Either some or all fields are empty. Please fill all fields before proceeding");
            builder.setTitle("All fields are required");
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
            // Check if email id valid or not
        else if (!m.find()){
            progressBar.setVisibility(View.GONE);
            builder.setMessage("Please Enter Valid Email-Id");
            builder.setTitle("Invalid Email-Id");
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
            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)){
            progressBar.setVisibility(View.GONE);
            builder.setMessage("Both Password doesn't match. Please enter same password in both fields");
            builder.setTitle("Password Mismatch");
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
            //Check if neither of 'Intern' and 'Job' are ticked.
        else if((!isIntern.isChecked()) && (!isJob.isChecked()))
        {
            progressBar.setVisibility(View.GONE);
            builder.setMessage("Both options are empty. Please select either Job or Intern before proceeding");
            builder.setTitle("Select at least one from Job or Intern");
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
            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked()){
            progressBar.setVisibility(View.GONE);
            builder.setMessage("Please accept Terms and Conditions to proceed further.");
            builder.setTitle("Please accept Terms and Conditions");
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
        // Else do signup or do your stuff
		/*else
			Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT)
					.show();*/		//TODO: Implement LoginApi.
        else {
            BodySignUpUser bodySignUpUser = new BodySignUpUser(fullName.getText().toString(),
                    emailId.getText().toString(),
                    mobileNumber.getText().toString(),
                    password.getText().toString(),
                    (isIntern.isChecked()?1:0),
                    (isJob.isChecked()?1:0),
                    location.getText().toString());
            Call<ResponseSignUpUser> call = loginApi.signUpUser(bodySignUpUser);
            call.enqueue(new Callback<ResponseSignUpUser>() {
                @Override
                public void onResponse(Call<ResponseSignUpUser> call, Response<ResponseSignUpUser> response) {
                    if(!response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        builder.setMessage(response.message());
                        builder.setTitle("Registration Inprogress");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert=builder.create();
                        alert.show();
                        return;
                    }
                    progressBar.setVisibility(View.GONE);
                    ResponseSignUpUser responseSignUpUser = response.body();
                    builder.setMessage(responseSignUpUser.getMessage()+"Registration Successful.\nPlease Login again to ensure security");
                    builder.setTitle("Registration Successful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();

                }

                @Override
                public void onFailure(Call<ResponseSignUpUser> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.i("Failure", "onFailure: "+t.getMessage());
                    builder.setMessage(t.getLocalizedMessage());
                    builder.setTitle("Registration Failed");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                    return;
                }
            });
        }
    }
}