package com.example.musicappdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private TextView alreadyHaveAnAccount;
    private FrameLayout frameLayout;
    private Drawable errorIcon;

    private EditText username;
    private  EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        alreadyHaveAnAccount=findViewById(R.id.already_have_account);
//        frameLayout=getActivity().findViewById(R.id.register_frame_layout);
//        errorIcon=getResources().getDrawable(R.drawable.ic_error);

        username= findViewById(R.id.userName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpButton);

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gọi hàm signUpWithFireBase
            }
        });


    }
}