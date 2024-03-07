package com.example.musicappdemo2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.musicappdemo2.HomeActivity;
import com.example.musicappdemo2.LoginActivity;
import com.example.musicappdemo2.PlayMusicActivity;
import com.example.musicappdemo2.R;


public class LibraryFragment extends Fragment {
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
        View view=inflater.inflate(R.layout.fragment_library, container, false);

        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        homeActivity.showToolbar(); // Hiển thị lại thanh toolbar khi Fragment bị hủy
    }
}