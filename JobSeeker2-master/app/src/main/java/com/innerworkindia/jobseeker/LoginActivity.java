package com.innerworkindia.jobseeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.innerworkindia.jobseeker.API.LoginApi;
import com.innerworkindia.jobseeker.Model.BodyLoginUser;
import com.innerworkindia.jobseeker.Model.ResponseLoginUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {


    private static View view;
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private static ProgressBar progressBar;

    Retrofit retrofit;
    LoginApi loginApi;

    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    private static final String KEY_UID="userid";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        builder=new AlertDialog.Builder(this);
sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
editor=sharedPreferences.edit();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        loginApi = retrofit.create(LoginApi.class);

        emailid = (EditText) findViewById(R.id.login_emailid);
        password = (EditText)findViewById(R.id.login_password);
        loginButton = (Button)findViewById(R.id.loginBtn);
        forgotPassword = (TextView)findViewById(R.id.forgot_password);
        signUp = (TextView)findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout)findViewById(R.id.login_layout);
        shakeAnimation = AnimationUtils.loadAnimation(LoginActivity.this,
                R.anim.shake);
        signUp=(TextView)findViewById(R.id.createAccount);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
// Get email id and password
                String getEmailId = emailid.getText().toString();
                String getPassword = password.getText().toString();
                // Check patter for email id
                Pattern p = Pattern.compile(Utils.regEx);
                Matcher m = p.matcher(getEmailId);
                // Check for both field is empty or not
                if (getEmailId.equals("") || getEmailId.length() == 0
                        || getPassword.equals("") || getPassword.length() == 0) {
                    loginLayout.startAnimation(shakeAnimation);
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Empty Email and Password Fields").setTitle("Enter both credentials.");
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
                else if (!m.find()) {
                    progressBar.setVisibility(View.GONE);
                    builder.setCancelable(true);
                    builder.setMessage("Please Enter Valid Email-Id").setTitle("Your Email Id is Invalid.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{                // Else do login and do your stuff
                    Call<ResponseLoginUser> call = loginApi.logInUser(new BodyLoginUser(emailid.getText().toString(), password.getText().toString()));
                    call.enqueue(new Callback<ResponseLoginUser>() {
                        @Override
                        public void onResponse(Call<ResponseLoginUser> call, Response<ResponseLoginUser> response) {
                            if (!response.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                builder.setMessage("Cannot Login at the moment. Press OK to continue").setTitle("Login UnSuccessful");
                                builder.setCancelable(true);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                //Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ResponseLoginUser responseLoginUser = response.body();
                            if (responseLoginUser.getCode() == 200) {
                                progressBar.setVisibility(View.GONE);
                                editor.putString(KEY_EMAIL,emailid.getText().toString());
                                editor.putString(KEY_UID,responseLoginUser.getUserId());
                                editor.commit();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                //Toast.makeText(getContext(), responseLoginUser.getMessage(), Toast.LENGTH_SHORT).show();
                            }else{
                                progressBar.setVisibility(View.GONE);
                                builder.setMessage(responseLoginUser.getMessage()).setTitle("Incorrect Login Credentials");
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
                        public void onFailure(Call<ResponseLoginUser> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Log.i("LoginFragment", "onFailure: " + t.getLocalizedMessage());
                            builder.setMessage(t.getLocalizedMessage()).setTitle("Login Failed");
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
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Forgotpassword.class));
            }
        });
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {
                        if (isChecked) {
                            show_hide_password.setText(R.string.hide_pwd);// change
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                        } else {
                            show_hide_password.setText(R.string.show_pwd);
                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());
                        }
                    }
                });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }
}