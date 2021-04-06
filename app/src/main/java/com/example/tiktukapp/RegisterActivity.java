package com.example.tiktukapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tiktukapp.data.model.User;
import com.example.tiktukapp.utils.FieldUtils;
import com.example.tiktukapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.validation.Validator;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = "ActivityRegister";
    private EditText edtUsername, edtEmail , edtPassword;
    private ProgressBar progressBar;
    private FirebaseFirestore dbStore;
    private FirebaseAuth dbAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        load();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (Utils.isNetworkAvailable(context)) {
//            Toast.makeText(context,"Network connected",Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context,"Network is not connected",Toast.LENGTH_LONG).show();
//        }
    }

    private void load() {
        edtUsername = findViewById(R.id.edtRegisterUsername);
        edtEmail = findViewById(R.id.edtRegisterEmail);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        progressBar = findViewById(R.id.progressBarRegister);
        dbStore = FirebaseFirestore.getInstance();
        dbAuth = FirebaseAuth.getInstance();
        context = getApplicationContext();
    }

    public void registerClick(View view) {
        final String username = edtUsername.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        if (!Utils.Validation.isUsernameValid(username) ||
                !Utils.Validation.isEmailValid(email) ||
                !Utils.Validation.isPasswordValid(password)) {
            if (!Utils.Validation.isUsernameValid(username)) {
                edtUsername.setError(getString(R.string.input_username_not_valid));
            }
            if (!Utils.Validation.isEmailValid(email)) {
                edtEmail.setError(getString(R.string.input_email_not_valid));
            }
            if (!Utils.Validation.isPasswordValid(password)) {
                edtPassword.setError(getString(R.string.input_password_not_valid));
            }
        } else {
            progressBar.setVisibility(View.VISIBLE);
            dbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser= dbAuth.getCurrentUser();
                        assert currentUser != null;
                        String userId = currentUser.getUid();
                        Map<String,Object> map = new HashMap<>();
                        User user = new User();
                        user.setId(userId);
                        user.setFullname(null);
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setFollow(null);
                        user.setFollower(null);
                        user.setVideos(null);
                        dbStore.collection("user")
                                .document(userId)
                                .set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(context,MainActivity.class);
                                        startActivity(intent);
                                        onDestroy();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //dbStore.
                    } else {
                        Toast.makeText(context, R.string.register_error_email_exit, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}