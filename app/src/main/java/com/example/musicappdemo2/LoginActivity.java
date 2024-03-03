package com.example.musicappdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicappdemo2.fragment.ResetPasswordFragment;

public class LoginActivity extends AppCompatActivity {

    private TextView dontHaveAnAccount;
    private TextView resetPassword;
    private FrameLayout frameLayout;
    private Drawable errorIcon;
    private EditText email;
    private EditText password;
    private Button signInButton;
    private ProgressBar signInProgess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        btLogin = findViewById(R.id.buttonLogin);
//        btLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                i.putExtra("","");
//                startActivity(i);
//            }
//        });
        resetPassword = findViewById(R.id.reset_password);
        //frameLayout=findViewById(R.id.register_frame_layout);
        dontHaveAnAccount=findViewById(R.id.dont_have_an_account);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        signInProgess = findViewById(R.id.signInProgress);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rspass= new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(rspass);
            }
        });
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dha=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(dha);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(email.getText().toString().trim().isEmpty()|| password.getText().toString().trim().isEmpty()) {
                   Toast.makeText(LoginActivity.this,"Please input your email and password",Toast.LENGTH_LONG).show();
                   return;
               }
               Intent i=new Intent(LoginActivity.this, HomeActivity.class);
               i.putExtra("email", email.getText());
               i.putExtra("password", password.getText());
               startActivity(i);
            }
        });



    }



}