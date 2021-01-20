package com.innerworkindia.jobseeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.innerworkindia.jobseeker.Model.BodyLoginUser;
import com.innerworkindia.jobseeker.Model.ResponseGetJobs;
import com.innerworkindia.jobseeker.Model.ResponseGetUserApplications;
import com.innerworkindia.jobseeker.Model.ResponseLoginUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Applied extends AppCompatActivity {

    Retrofit retrofit;
    LoginApi loginApi;
    ProgressBar progressBar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    private static final String KEY_UID="userid";

    RadioButton r1,r2;
    RecyclerView recyclerView;
    String uid;
    int u;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied);

        r1=findViewById(R.id.radioButton5);
        r2=findViewById(R.id.radioButton6);
        progressBar=findViewById(R.id.progressBar4);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Opportunities");
        navigationView=findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawer);
        builder=new AlertDialog.Builder(this);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginApi = retrofit.create(LoginApi.class);

        recyclerView=findViewById(R.id.recycle1);
        uid=sharedPreferences.getString(KEY_UID,null);
        u=Integer.valueOf(uid);

        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseGetUserApplications> call = loginApi.getUserApplications(new BodyGetUserApplications(u,"Internship"));
        call.enqueue(new Callback<ResponseGetUserApplications>() {
            @Override
            public void onResponse(Call<ResponseGetUserApplications> call, Response<ResponseGetUserApplications> response) {
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
                ResponseGetUserApplications responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {

                    progressBar.setVisibility(View.GONE);
                    if(responseLoginUser.jobposts.size()==0){

                        builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch Unsuccessful");
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

                    else{
                        AppliedAdapter appliedAdapter=new AppliedAdapter(Applied.this,responseLoginUser.jobposts);
                        recyclerView.setAdapter(appliedAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Applied.this));
                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("").setTitle("Fetch Unsuccessful");
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
            public void onFailure(Call<ResponseGetUserApplications> call, Throwable t) {
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
        View head=navigationView.getHeaderView(0);
        TextView n1=head.findViewById(R.id.header_name);
        TextView e1=head.findViewById(R.id.header_email);
        ImageView i1=head.findViewById(R.id.header_logo);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Applied.this,Profile.class));
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
                        startActivity(new Intent(Applied.this,HomeActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contactus:
                        startActivity(new Intent(Applied.this,ContactUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.applied:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(Applied.this, Profile.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutus:
                        startActivity(new Intent(Applied.this,AboutUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        editor.clear();
                        startActivity(new Intent(Applied.this,LoginActivity.class));
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
            case R.id.radioButton5:
                if (checked)
                    Intern();
                    break;
            case R.id.radioButton6:
                if (checked)
                    Job();
                    break;
        }
    }
    public void Intern(){
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseGetUserApplications> call = loginApi.getUserApplications(new BodyGetUserApplications(u,"Internship"));
        call.enqueue(new Callback<ResponseGetUserApplications>() {
            @Override
            public void onResponse(Call<ResponseGetUserApplications> call, Response<ResponseGetUserApplications> response) {
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
                ResponseGetUserApplications responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {

                    progressBar.setVisibility(View.GONE);
                    if(responseLoginUser.jobposts.size()==0){

                        builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch Unsuccessful");
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

                    else{
                        AppliedAdapter appliedAdapter=new AppliedAdapter(Applied.this,responseLoginUser.jobposts);
                        recyclerView.setAdapter(appliedAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Applied.this));
                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("").setTitle("Fetch Unsuccessful");
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
            public void onFailure(Call<ResponseGetUserApplications> call, Throwable t) {
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
        Call<ResponseGetUserApplications> call1 = loginApi.getUserApplications(new BodyGetUserApplications(u,"Job"));
        call1.enqueue(new Callback<ResponseGetUserApplications>() {
            @Override
            public void onResponse(Call<ResponseGetUserApplications> call1, Response<ResponseGetUserApplications> response) {
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
                ResponseGetUserApplications responseLoginUser = response.body();
                if (responseLoginUser.getCode() == 200) {

                    progressBar.setVisibility(View.GONE);
                    if(responseLoginUser.jobposts.size()==0){
                        builder.setMessage("Unable to fetch. Please Try Again Later. Press OK to Try Again. Press Cancel to Continue").setTitle("Fetch Unsuccessful");
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

                    }else{

                        AppliedAdapter appliedAdapter=new AppliedAdapter(Applied.this,responseLoginUser.jobposts);
                        recyclerView.setAdapter(appliedAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Applied.this));

                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                    builder.setMessage("You have not applied for any Job yet.Press OK to Try to fetch again. Press Cancel to continue").setTitle("Not Applied");
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
            public void onFailure(Call<ResponseGetUserApplications> call1, Throwable t) {
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