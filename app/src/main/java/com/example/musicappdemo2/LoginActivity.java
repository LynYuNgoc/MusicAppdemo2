package com.example.musicappdemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicappdemo2.classes.ProgressHelper;
import com.example.musicappdemo2.fragment.ResetPasswordFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView dontHaveAnAccount;
    private TextView resetPassword;


    private EditText email;
    private EditText password;
    private Button signInButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        resetPassword = findViewById(R.id.reset_password);
        dontHaveAnAccount=findViewById(R.id.dont_have_an_account);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);

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

                if (checkInput() == true) {
                    ProgressHelper.showDialog(LoginActivity.this, "Loading...");

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("FirebaseAuth", "signInWithEmail:success");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        //updateUI(user);
                                        gotoHomeActivity();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("FirebaseAuth", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Login failed.",
                                                Toast.LENGTH_SHORT).show();
                                        gotoLoginActivity();

                                    }
                                }
                            });
                }
            }
        });

    }

    void gotoHomeActivity(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    void gotoLoginActivity(){
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    boolean checkInput(){
        if (email.getText().toString().length() < 6) {
            email.setError("Vui lòng nhập email...");
            return false;
        }

        if (password.getText().toString().length() < 3) {
            password.setError("Vui lòng nhập password...");
            return false;
        }

        return true;
    }



}