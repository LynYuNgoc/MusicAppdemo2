package com.example.musicappdemo2.fragment;



import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicappdemo2.HomeActivity;
import com.example.musicappdemo2.LoginActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment {

    Toolbar toolbarLogout;
    BottomNavigationView bottomNavigationView;
    private HomeActivity homeActivity;

    private Button btnAlbumMng, btnEditProfile;
    private ImageView imgAvatar;
    private TextView tvName, tvEmail;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivity = (HomeActivity)getActivity();
        homeActivity.hideToolbar(); // Ẩn thanh toolbar khi Fragment được tạo

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        imgAvatar=view.findViewById(R.id.imageView2);
        tvName=view.findViewById(R.id.tv_name);
        tvEmail=view.findViewById(R.id.tv_email);

        showUserInformation();



        bottomNavigationView = view.findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logOut) {
//                    FirebaseAuth.getInstance().signOut();
//                    Intent intent=new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();

                    showConfirmLogOut();
                }
                if(item.getItemId()==R.id.edt_profile) {
                    setFragment(new MyProfileFragment());
                }
                return false;
            }
        });


        return view;
    }
    void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void showUserInformation() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null) {
            return;
        }
        String name=user.getDisplayName();
        String email=user.getEmail();
        Uri photoUrl=user.getPhotoUrl();
        if(name==null) {
            tvName.setVisibility(View.GONE);
        } else {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);

        }

        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(imgAvatar);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==MY_REQUEST_CODE) {
//            if(grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            }
//
//        }
//    }
//    public void  openGallery() {
//        Intent intent=new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeActivity.showToolbar(); // Hiển thị lại thanh toolbar khi Fragment bị hủy
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        toolbarLogout = view.findViewById(R.id.toolbarLogout);
//        toolbarLogout.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showConfirmLogOut();
//            }
//        });
        //showConfirmLogOut();

    }

    void showConfirmLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn chắc chắn thoát?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Thoát",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

        builder.setNegativeButton(
                "Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        //2. now setup to change color of the button
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_main_bg, null));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.gray_light, null));
            }
        });
        alert.show();
    }

}