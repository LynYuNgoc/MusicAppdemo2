package com.example.musicappdemo2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicappdemo2.classes.ProgressHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private TextView alreadyHaveAnAccount;
    private FrameLayout frameLayout;
    private Drawable errorIcon;

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        alreadyHaveAnAccount = findViewById(R.id.already_have_account);
//        frameLayout=getActivity().findViewById(R.id.register_frame_layout);
//        errorIcon=getResources().getDrawable(R.drawable.ic_error);

        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpButton);




        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gọi hàm signUpWithFireBase
                if (checkInput() == true) {
                    ProgressHelper.showDialog(SignUpActivity.this, "Loading...");
                    //firebaseRegisterNewUser();

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), confirmPassword.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("FirebaseAuth", "createUserWithEmail:success");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        //updateUI(user);
                                        gotoHomeActivity();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "SignUp failed.",
                                                Toast.LENGTH_SHORT).show();
                                        gotoLoginActivity();

                                    }
                                }
                            });
                }
            }
        });

    }

    void gotoHomeActivity() {
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    void gotoLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //    void firebaseRegisterNewUser(){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("userName", username.getText().toString().trim());
//        user.put("email", email.getText().toString().trim());
//        user.put("password", password.getText().toString());
//        user.put("avatar", "");
//
//
//// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        ProgressHelper.dismissDialog();
//                        Log.d("SignUpActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        ProgressHelper.dismissDialog();
//                        Log.w("SignUpActivity", "Error adding document", e);
//                    }
//                });
//    }



    boolean checkInput() {
        if (email.getText().toString().length() < 6) {
            email.setError("Invalid mobile number / email.");
            return false;
        }

        if (username.getText().toString().length() < 3) {
            username.setError("Invalid full name.");
            return false;
        }

        if (password.getText().toString().length() < 3) {
            password.setError("Invalid password.");
            return false;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Password not match.");
            return false;
        }
        return true;
    }
}