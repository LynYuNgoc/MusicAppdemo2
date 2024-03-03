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

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView back;
    private FrameLayout frameLayout;
    private Drawable errorIcon;
    private EditText email;
    private ProgressBar resetPasswordProgress;
    private TextView responseMessage;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        back=findViewById(R.id.back);
        //frameLayout=findViewById(R.id.register_frame_layout);
        //errorIcon=getResources().getDrawable(R.drawable.ic_error)

        email=findViewById(R.id.email);
        resetPasswordProgress=findViewById(R.id.resetPasswordProgressBar);
        responseMessage=findViewById(R.id.responseMessage);
        resetPasswordButton=findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goị hàm resetPassword
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(back);
            }

        });

    }
}