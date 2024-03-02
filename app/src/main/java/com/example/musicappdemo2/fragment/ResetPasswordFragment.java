package com.example.musicappdemo2.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.musicappdemo2.R;


public class ResetPasswordFragment extends Fragment {
    private TextView back;
    private FrameLayout frameLayout;
    private Drawable errorIcon;
    private EditText email;
    private ProgressBar resetPasswordProgress;
    private TextView responseMessage;
    private Button resetPasswordButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        back=view.findViewById(R.id.back);
        frameLayout=getActivity().findViewById(R.id.register_frame_layout);


        email=view.findViewById(R.id.email);
        resetPasswordProgress=view.findViewById(R.id.resetPasswordProgressBar);
        responseMessage=view.findViewById(R.id.responseMessage);
        resetPasswordButton=view.findViewById(R.id.resetPasswordButton);

        return view;

    }
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_left,R.anim.out_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}