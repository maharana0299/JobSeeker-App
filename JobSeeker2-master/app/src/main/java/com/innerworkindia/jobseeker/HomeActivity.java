package com.innerworkindia.jobseeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.innerworkindia.jobseeker.API.LoginApi;
import com.innerworkindia.jobseeker.Model.BodyGetJobs;
import com.innerworkindia.jobseeker.Model.BodyGetUserApplications;
import com.innerworkindia.jobseeker.Model.BodyGetUserProfile;
import com.innerworkindia.jobseeker.Model.ResponseGetJobs;
import com.innerworkindia.jobseeker.Model.ResponseGetUserApplications;
import com.innerworkindia.jobseeker.Model.ResponseGetUserProfile;
import com.innerworkindia.jobseeker.Model.ResponseLoginUser;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    Retrofit retrofit;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    LoginApi loginApi;
    int u;
    AlertDialog.Builder builder;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    private static final String KEY_UID="userid";
    RadioButton r1,r2;
    RecyclerView recyclerView;
    String uid;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        loginApi = retrofit.create(LoginApi.class);
        builder=new AlertDialog.Builder(this);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        progressBar=findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView=findViewById(R.id.recycle);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Opportunities");
        navigationView=findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawer);

        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton3);


        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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

                    if(responseLoginUser.isJob && !responseLoginUser.isIntern){
                        r2.setVisibility(View.GONE);
                        r1.setChecked(true);
                        Call<ResponseGetJobs> call = loginApi.getJobs(new BodyGetJobs("Job"));
                        call.enqueue(new Callback<ResponseGetJobs>() {
                            @Override
                            public void onResponse(Call<ResponseGetJobs> call, Response<ResponseGetJobs> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to continue").setTitle("Fetch UnSuccessful");
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
                                ResponseGetJobs responseLoginUser = response.body();
                                if (responseLoginUser.getCode() == 200) {

                                    progressBar.setVisibility(View.GONE);
                                    InternshipAdapter appliedAdapter=new InternshipAdapter(HomeActivity.this,responseLoginUser.code,responseLoginUser.message,responseLoginUser.jobposts);
                                    recyclerView.setAdapter(appliedAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    builder.setMessage("Press Ok to close dialog box").setTitle("Fetch Unsuccessful. Please try again later.");
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
                            public void onFailure(Call<ResponseGetJobs> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to continue").setTitle("Fetch UnSuccessful");
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
                    else
                    if(responseLoginUser.isIntern && !responseLoginUser.isJob){
                        r1.setVisibility(View.GONE);
                        r2.setChecked(true);
                        Call<ResponseGetJobs> call = loginApi.getJobs(new BodyGetJobs("Internship"));
                        call.enqueue(new Callback<ResponseGetJobs>() {
                            @Override
                            public void onResponse(Call<ResponseGetJobs> call, Response<ResponseGetJobs> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to continue").setTitle("Fetch UnSuccessful");
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
                                ResponseGetJobs responseLoginUser = response.body();
                                if (responseLoginUser.getCode() == 200) {

                                    progressBar.setVisibility(View.GONE);
                                    InternshipAdapter appliedAdapter=new InternshipAdapter(HomeActivity.this,responseLoginUser.code,responseLoginUser.message,responseLoginUser.jobposts);
                                    recyclerView.setAdapter(appliedAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    builder.setMessage("Press Ok to close dialog box").setTitle("Fetch Unsuccessful. Please try again later.");
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
                            public void onFailure(Call<ResponseGetJobs> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to continue").setTitle("Fetch UnSuccessful");
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
                    else
                    if(responseLoginUser.isJob && responseLoginUser.isIntern){
                        r2.setChecked(true);
                        Call<ResponseGetJobs> call = loginApi.getJobs(new BodyGetJobs("Internship"));
                        call.enqueue(new Callback<ResponseGetJobs>() {
                            @Override
                            public void onResponse(Call<ResponseGetJobs> call, Response<ResponseGetJobs> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to continue").setTitle("Fetch UnSuccessful");
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
                                ResponseGetJobs responseLoginUser = response.body();
                                if (responseLoginUser.getCode() == 200) {

                                    progressBar.setVisibility(View.GONE);
                                    InternshipAdapter appliedAdapter=new InternshipAdapter(HomeActivity.this,responseLoginUser.code,responseLoginUser.message,responseLoginUser.jobposts);
                                    recyclerView.setAdapter(appliedAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    builder.setMessage("Press Ok to close dialog box").setTitle("Fetch Unsuccessful. Please try again later.");
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
                            public void onFailure(Call<ResponseGetJobs> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to continue").setTitle("Fetch UnSuccessful");
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
                    progressBar.setVisibility(View.GONE);
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

        View head=navigationView.getHeaderView(0);
        TextView n1=head.findViewById(R.id.header_name);
        TextView e1=head.findViewById(R.id.header_email);
        ImageView i1=head.findViewById(R.id.header_logo);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Profile.class));
            }
        });

        String a=sharedPreferences.getString(KEY_EMAIL,"abc@example.com");
        String b=sharedPreferences.getString(KEY_UID,"Hello World");
        n1.setText(b);
        e1.setText(a);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.internships:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contactus:
                        startActivity(new Intent(HomeActivity.this,ContactUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.applied:
                        startActivity(new Intent(HomeActivity.this, Applied.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(HomeActivity.this, Profile.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutus:
                        startActivity(new Intent(HomeActivity.this,AboutUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        editor.clear();
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioButton3:
                if (checked)
                    Intern();
                break;
            case R.id.radioButton:
                if (checked)
                    Job();
                break;
        }
    }
    public void Intern(){
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseGetJobs> call = loginApi.getJobs(new BodyGetJobs("Internship"));
        call.enqueue(new Callback<ResponseGetJobs>() {
            @Override
            public void onResponse(Call<ResponseGetJobs> call, Response<ResponseGetJobs> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch UnSuccessful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intern();
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();;
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                ResponseGetJobs responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {

                    progressBar.setVisibility(View.GONE);
                    InternshipAdapter appliedAdapter=new InternshipAdapter(HomeActivity.this,responseLoginUser.code,responseLoginUser.message,responseLoginUser.jobposts);
                    recyclerView.setAdapter(appliedAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch Unsuccessful. Please try again later.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intern();
                            dialog.cancel();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();;
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return;//Toast.makeText(getContext(), "Received token: " + responseLoginUser.getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseGetJobs> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch UnSuccessful");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intern();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();;
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
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseGetJobs> call1 = loginApi.getJobs(new BodyGetJobs("Job"));
        call1.enqueue(new Callback<ResponseGetJobs>() {
            @Override
            public void onResponse(Call<ResponseGetJobs> call1, Response<ResponseGetJobs> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch UnSuccessful");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Job();
                            dialog.cancel();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();;
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                ResponseGetJobs responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {

                    progressBar.setVisibility(View.GONE);
                    InternshipAdapter appliedAdapter=new InternshipAdapter(HomeActivity.this,responseLoginUser.code,responseLoginUser.message,responseLoginUser.jobposts);
                    recyclerView.setAdapter(appliedAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch Unsuccessful. Please try again later.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Job();
                            dialog.cancel();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();;
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return;//Toast.makeText(getContext(), "Received token: " + responseLoginUser.getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseGetJobs> call1, Throwable t) {
                progressBar.setVisibility(View.GONE);
                builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch UnSuccessful");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Job();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();;
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