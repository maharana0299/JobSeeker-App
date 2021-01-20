package com.innerworkindia.jobseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Animation top,bot;
    ImageView iv;
    TextView i1;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="jobseeker";
    private static final String KEY_EMAIL="email";
    String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        top= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bot=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        i1=findViewById(R.id.tex1);
        iv=findViewById(R.id.im1);
        i1.setAnimation(bot);
        iv.setAnimation(top);

        Handler h=new Handler();
        test=sharedPreferences.getString(KEY_EMAIL,null);
        if(test!=null){
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.v("tag","first");
                    Intent i=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            },4100);
        }else{
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.v("tag","first");
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            },4100);
        }

    }
}