package com.example.musicappdemo2.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class LogInFragment extends Fragment {

    private TextView resetPassword;
    private FrameLayout frameLayout;
    private Drawable errorIcon;
    private EditText email;
    private EditText password;
    private Button signInButton;
    private ProgressBar signInProgess;

    private Button singUpButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        resetPassword = view.findViewById(R.id.reset_password);
        frameLayout=getActivity().findViewById(R.id.register_frame_layout);


        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        signInButton = view.findViewById(R.id.signInButton);
        signInProgess = view.findViewById(R.id.signInProgress);

        //singUpButton = view.findViewById(R.id.signUpButton);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ResetPasswordFragment());

            }
        });

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_right,R.anim.out_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}





