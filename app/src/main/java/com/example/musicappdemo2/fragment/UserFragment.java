package com.example.musicappdemo2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.musicappdemo2.HomeActivity;
import com.example.musicappdemo2.R;
import com.google.android.material.navigation.NavigationView;


public class UserFragment extends Fragment {
    Toolbar myToolbar;
    DrawerLayout myDrawer;
    NavigationView myNavigation;

    View headerNavigation;

    ImageButton btAvatar, btSetting;
    private HomeActivity homeActivity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivity = (HomeActivity) getActivity();
        homeActivity.hideToolbar(); // Ẩn thanh toolbar khi Fragment được tạo
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeActivity.showToolbar(); // Hiển thị lại thanh toolbar khi Fragment bị hủy
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}