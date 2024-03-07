package com.example.musicappdemo2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicappdemo2.fragment.HomePageFragment;
import com.example.musicappdemo2.fragment.LibraryFragment;
import com.example.musicappdemo2.fragment.LogInFragment;
import com.example.musicappdemo2.fragment.SearchFragment;
import com.example.musicappdemo2.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomMenu();



        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.mnBottomHome) {

                    setFragment(new HomePageFragment());
                }

                if(item.getItemId() == R.id.mnBottomLibrary) {

                    setFragment(new LibraryFragment());
                }

                if(item.getItemId() == R.id.mnBottomSearch) {

                    setFragment(new SearchFragment());
                }

                if(item.getItemId() == R.id.mnBottomUser) {

                    setFragment(new UserFragment());
                }



                return true;
            }
        });

    }



    void setFragment(Fragment fragment){
        if(fragment==null){
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void setupBottomMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener());
        setFragment(new HomePageFragment());
    }

    private NavigationBarView.OnItemSelectedListener onItemSelectedListener() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        };
    }

}
