package com.example.musicappdemo2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.musicappdemo2.fragment.HomePageFragment;
import com.example.musicappdemo2.fragment.LibraryFragment;
import com.example.musicappdemo2.fragment.LogInFragment;
import com.example.musicappdemo2.fragment.MyProfileFragment;
import com.example.musicappdemo2.fragment.SearchFragment;
import com.example.musicappdemo2.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 10;

    private Toolbar myToolbar;
    final private MyProfileFragment mMyProfileFragment = new MyProfileFragment();

    BottomNavigationView bottomNavigationView;
    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent == null) {
                    return;
                }
                Uri uri = intent.getData();
                mMyProfileFragment.setUri(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    mMyProfileFragment.setBitmapImageView(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomMenu();

        myToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(myToolbar);


        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.mnBottomHome) {

                    setFragment(new HomePageFragment());
                }

                if (item.getItemId() == R.id.mnBottomLibrary) {

                    setFragment(new LibraryFragment());
                }

                if (item.getItemId() == R.id.mnBottomSearch) {

                    setFragment(new SearchFragment());
                }

                if (item.getItemId() == R.id.mnBottomUser) {

                    setFragment(mMyProfileFragment);
                }


                return true;
            }
        });

    }
//    public void showUserInformation() {
//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        if(user==null) {
//            return;
//        }
//        String name=user.getDisplayName();
//        String email=user.getEmail();
//        Uri photoUrl=user.getPhotoUrl();
//        if(name==null) {
//            tvName.setVisibility(View.GONE);
//        } else {
//            tvName.setVisibility(View.VISIBLE);
//            tvName.setText(name);
//
//        }
//
//        tvEmail.setText(email);
//        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(imgAvatar);
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                // Quyền không được cấp, hiển thị thông báo hoặc thông báo lỗi cho người dùng
                Toast.makeText(this, "Permission denied. Cannot access gallery.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    public void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void showToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }


    void setFragment(Fragment fragment) {
        if (fragment == null) {
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
