package com.example.musicappdemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView back;
    private FrameLayout frameLayout;
    private Drawable errorIcon;
    private EditText email;
    private ProgressBar resetPasswordProgress;
    private TextView responseMessage;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        back = findViewById(R.id.back);
        //frameLayout=findViewById(R.id.register_frame_layout);
        //errorIcon=getResources().getDrawable(R.drawable.ic_error)

        email = findViewById(R.id.email);
        resetPasswordProgress = findViewById(R.id.resetPasswordProgressBar);
        responseMessage = findViewById(R.id.responseMessage);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(back);
            }

        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void resetPassword() {
        if (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            resetPasswordProgress.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        responseMessage.setText("Check your Email.");
                        responseMessage.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        responseMessage.setText("There is an issue sending Email.");
                        responseMessage.setTextColor(getResources().getColor(R.color.red));
                    }
                    resetPasswordProgress.setVisibility(View.INVISIBLE);
                    responseMessage.setVisibility(View.VISIBLE);
                }
            });
        } else {
            email.setError("Invalid Email Pattern!", errorIcon);
            resetPasswordButton.setEnabled(true);
            resetPasswordButton.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void checkInputs() {
        if (!email.getText().toString().isEmpty()) {
            resetPasswordButton.setEnabled(true);
            resetPasswordButton.setTextColor(getResources().getColor(R.color.white));

        } else {
            resetPasswordButton.setEnabled(false);
            resetPasswordButton.setTextColor(getResources().getColor(R.color.transWhite));
        }
    }

}