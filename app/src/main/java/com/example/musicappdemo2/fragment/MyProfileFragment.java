package com.example.musicappdemo2.fragment;

import static com.example.musicappdemo2.HomeActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.musicappdemo2.HomeActivity;
import com.example.musicappdemo2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class MyProfileFragment extends Fragment {



   private ImageView imgAvatar;
   private EditText edtFullName, edtEmail;
   private Button btnUpdate;
   private Uri mUri;
   private HomeActivity mHomeActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_profile, container, false);

        imgAvatar=view.findViewById(R.id.img_avatar);
        edtFullName=view.findViewById(R.id.edt_full_name);
        edtEmail=view.findViewById(R.id.edt_email);
        btnUpdate=view.findViewById(R.id.btn_update);

        setUserInformation();
        initListener();
        return view;
    }
    private void setUserInformation() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null) {
            return;
        }

        edtFullName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatar_default).into(imgAvatar);
    }
    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCliclRequestPermission();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });
    }
    private void  onCliclRequestPermission() {

        if(mHomeActivity==null)
        {
            return;
        }

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) {
            mHomeActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            mHomeActivity.openGallery();
        } else {
            String [] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions,MY_REQUEST_CODE);
        }
    }
    public void setBitmapImageView(Bitmap bitmapImageView) {
        imgAvatar.setImageBitmap(bitmapImageView);

    }
    public void setUri(Uri mUri) {
        this.mUri=mUri;
    }
    private void  onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null) {
            return;
        }
        String strFullName=edtFullName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Update profile success", Toast.LENGTH_SHORT).show();
                            //mHomeActivity.showUserInformation();
                        }
                    }
                });
    }
}