package com.example.musicappdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.example.musicappdemo2.widget.MainActivity;
import com.example.musicappdemo2.widget.MainActivity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

/** @noinspection ALL*/
public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if(currentUser != null){
                    String str1 = currentUser.getEmail();
                    String str2 = currentUser.getDisplayName();
                    Uri str3 = currentUser.getPhotoUrl();
                    gotoHomeScreen();
                    //gotoMainScreen();
                }else {
                    gotoLoginScreen();
                }
                finish();

            }
        }, 3000);


//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                if(currentUser != null){
//                    String str1 = currentUser.getEmail();
//                    String str2 = currentUser.getDisplayName();
//                    Uri str3 = currentUser.getPhotoUrl();
//                    gotoHomeScreen();
//                }else {
//                    gotoLoginScreen();
//                }
//                finish();
//            }
//        }, 1000);


    }

    void gotoLoginScreen() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
    }

    void gotoHomeScreen() {
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);
    }

    void gotoMainScreen() {
        Intent i = new Intent(SplashActivity.this, MainActivity2.class);
        startActivity(i);
    }
}