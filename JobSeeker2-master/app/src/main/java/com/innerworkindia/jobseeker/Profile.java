package com.innerworkindia.jobseeker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.innerworkindia.jobseeker.API.LoginApi;
import com.innerworkindia.jobseeker.Model.BodyGetUserProfile;
import com.innerworkindia.jobseeker.Model.ResponseGetUserProfile;
import com.innerworkindia.jobseeker.Model.ResponseUpdateUserProfile;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    public static final int GET_FIRST_FILE = 1, GET_SECOND_FILE = 2;
    public static final String BASE_URL = "https://www.innerworkindia.com/";
    public static final String INTERN_DOCS = "InternshipApplicants/";
    public static final String PHOTO_LOCATION = "AppUsers/photos/";
    private static final String TAG = "Profile";
    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    private static final String KEY_UID="userid";

    String userIdString;
    String userName, userEmail, userPhone, userLocation;
    int userIsIntern, userIsJob;

    boolean filePhoto, fileResume, profileFetchSuccess = false;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    EditText editTextName, editTextEmail, editTextMobile, editTextLocation;
    CheckBox checkBoxIntern, checkBoxJob;
    TextView textViewResume;
    Button buttonSelect, buttonUpload;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ProgressBar progressBar;
    Retrofit retrofit;
    LoginApi loginApi;

    AlertDialog.Builder builder;
    Uri uri1, uri2;
    Gson gson;

    ResponseGetUserProfile responseGetUserProfile;
    String originalResumeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        originalResumeName = "Upload Resume file here";

        if(ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Profile.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
        if(ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Profile.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
        
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.innerworkindia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginApi = retrofit.create(LoginApi.class);
        builder=new AlertDialog.Builder(this);
        gson = new Gson();

        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        editTextName =findViewById(R.id.fullName1);
        editTextEmail =findViewById(R.id.userEmailId1);
        editTextMobile =findViewById(R.id.mobileNumber1);
        checkBoxIntern =findViewById(R.id.intern1);
        checkBoxJob =findViewById(R.id.job1);
        editTextLocation =findViewById(R.id.location1);
        textViewResume =findViewById(R.id.textViewResume);
        imageView=findViewById(R.id.imageView2);
        buttonSelect = findViewById(R.id.resumeUpload);
        buttonUpload = findViewById(R.id.UpdateBtn);
        checkBoxIntern.setEnabled(false);
        checkBoxJob.setEnabled(false);

        progressBar=findViewById(R.id.progressBar7);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        toolbar.setTitle("Profile");
        navigationView=findViewById(R.id.navigation_view1);
        drawerLayout=findViewById(R.id.drawer1);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        fetchProfile();

        setPageView();

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fileResume) {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_SECOND_FILE);
                }
                else{
                    textViewResume.setText(originalResumeName);
                    uri2 = null;
                    fileResume = false;
                    buttonSelect.setText("Upload Resume");
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!filePhoto) {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_FIRST_FILE);
                }
                else{
                    uri1 = null;
                    filePhoto = false;
                    Toast.makeText(Profile.this, "Profile picture un-selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileFetchSuccess)
                    upload();
                else
                    Toast.makeText(Profile.this, "Please try later.", Toast.LENGTH_SHORT).show();
            }
        });

        textViewResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileFetchSuccess && responseGetUserProfile.resume_uploaded && (!fileResume))
                    downloadFile();
            }
        });
    }

    private void setPageView() {
        View head=navigationView.getHeaderView(0);
        TextView n1=head.findViewById(R.id.header_name);
        TextView e1=head.findViewById(R.id.header_email);
        ImageView i1=head.findViewById(R.id.header_logo);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Profile.class));
            }
        });
        String a=sharedPreferences.getString(KEY_EMAIL,"abc@example.com");
        String b=sharedPreferences.getString(KEY_UID,"Hello World");
        userIdString = b;
        n1.setText(b);
        e1.setText(a);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.internships:
                        startActivity(new Intent(Profile.this,HomeActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contactus:
                        startActivity(new Intent(Profile.this,ContactUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.applied:
                        startActivity(new Intent(Profile.this, Applied.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutus:
                        startActivity(new Intent(Profile.this,AboutUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        editor.clear();
                        startActivity(new Intent(Profile.this,LoginActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void fetchProfile() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseGetUserProfile> call1 = loginApi.getUserProfile(new BodyGetUserProfile(Integer.valueOf(sharedPreferences.getString(KEY_UID,null))));
        call1.enqueue(new Callback<ResponseGetUserProfile>() {
            @Override
            public void onResponse(Call<ResponseGetUserProfile> call1, Response<ResponseGetUserProfile> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    profileFetchSuccess = false;
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
                responseGetUserProfile = response.body();
                if (responseGetUserProfile.getName() == null) {
                    profileFetchSuccess = false;
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
                    profileFetchSuccess = true;
                    setProfileViews();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserProfile> call1, Throwable t) {
                progressBar.setVisibility(View.GONE);
                profileFetchSuccess = false;
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
            }
        });
    }

    private void setProfileViews() {
        editTextName.setText(responseGetUserProfile.name);
        editTextEmail.setText(responseGetUserProfile.email);
        editTextMobile.setText(responseGetUserProfile.phone);
        if(responseGetUserProfile.isIntern){
            checkBoxIntern.setChecked(true);
        }
        if(responseGetUserProfile.isJob){
            checkBoxJob.setChecked(true);
        }
        if(responseGetUserProfile.photo_uploaded){
            String url = BASE_URL + PHOTO_LOCATION + encodeValue(responseGetUserProfile.photo);
            Picasso.get().load(url).into(imageView);
        }
        if(responseGetUserProfile.resume_uploaded){
            originalResumeName = responseGetUserProfile.resume;
            textViewResume.setText(originalResumeName);
        }
        editTextLocation.setText(responseGetUserProfile.location);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FIRST_FILE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            uri1 = data.getData();
            filePhoto = true;
            Toast.makeText(this, "Profile picture selected.", Toast.LENGTH_SHORT).show();
            /*File originalFile = FileUtils.getFile(this, uri1);
            textViewResume.setText(originalFile.getName());
            buttonSelect.setText("Remove");*/
            //Set new photo
        }
        else if(requestCode == GET_SECOND_FILE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            uri2 = data.getData();
            File originalFile = FileUtils.getFile(this, uri2);
            textViewResume.setText(originalFile.getName());
            fileResume = true;
            buttonSelect.setText("Remove");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                else{
                }
                return;
            }
            case 101:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                else{
                }
                return;
            }
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),responseGetUserProfile.resume);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("Divvi", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                Toast.makeText(this, "File saved to Downloads directory.", Toast.LENGTH_SHORT).show();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    private void upload() {

        Map<String, Object> mp = new HashMap<>();
        int userID = Integer.parseInt(userIdString);

        getUserValues();
        mp.put("userId", userID);
        mp.put("name", userName);
        mp.put("email", userEmail);
        mp.put("phone", userPhone);
        mp.put("isIntern", userIsIntern);
        mp.put("isJob", userIsJob);
        mp.put("location", userLocation);
        mp.put("fileStatusPhoto", filePhoto);
        mp.put("fileStatusResume", fileResume);

        String fStatus = gson.toJson(mp);
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, fStatus);

        MultipartBody.Part f1 = null, f2 = null, f3 = null;
        File originalFile;
        RequestBody filePart;

        //filePhoto
        if(filePhoto){
            originalFile = FileUtils.getFile(this, uri1);
            filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(uri1)), originalFile);
            f1 = MultipartBody.Part.createFormData("filePhoto", originalFile.getName(), filePart);
        }

        //fileResume
        if(fileResume){
            originalFile = FileUtils.getFile(this, uri2);
            filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(uri2)), originalFile);
            f2 = MultipartBody.Part.createFormData("fileResume", originalFile.getName(), filePart);
        }

        Call<ResponseUpdateUserProfile> call = loginApi.updateUserProfile(descriptionPart, f1, f2);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseUpdateUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUpdateUserProfile> call, Response<ResponseUpdateUserProfile> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Profile.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.d("HI", response.raw().toString());
                }
                else{
                    Toast.makeText(Profile.this, "NO!"+response.message(), Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseUpdateUserProfile> call, Throwable t) {
                Toast.makeText(Profile.this, "No!!"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getUserValues() {
        userName = editTextName.getText().toString();
        userEmail = editTextEmail.getText().toString();
        userPhone = editTextMobile.getText().toString();
        userIsIntern = (responseGetUserProfile.isIntern?1:0);
        userIsJob = (responseGetUserProfile.isJob?1:0);
        userLocation = editTextLocation.getText().toString();
    }

    private void downloadFile() {
        String fileUrl = BASE_URL + INTERN_DOCS + encodeValue(responseGetUserProfile.resume);

        Call<ResponseBody> call = loginApi.downloadFileWithDynamicUrlSync(fileUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }
}