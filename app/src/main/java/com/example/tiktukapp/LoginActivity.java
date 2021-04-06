package com.example.tiktukapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tiktukapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "ActivityLogin";
    private EditText edtEmail , edtPassword;
    private ProgressBar progressBar;
    private FirebaseFirestore dbStore;
    private FirebaseAuth dbAuth;
    private Context context;

    @Override
    protected void onStart() {
        super.onStart();
        if (dbAuth.getCurrentUser() != null) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        load();
    }

    private void load() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        progressBar = findViewById(R.id.progressBarLogin);
        dbStore = FirebaseFirestore.getInstance();
        dbAuth = FirebaseAuth.getInstance();
        context = getApplicationContext();
    }

    public void noAccount(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void loginClick(View view) {
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        if (!Utils.Validation.isEmailValid(email) ||
                !Utils.Validation.isPasswordValid(password)) {
            if (!Utils.Validation.isEmailValid(email)) {
                edtEmail.setError(getString(R.string.input_email_not_valid));
            }
            if (!Utils.Validation.isPasswordValid(password)) {
                edtPassword.setError(getString(R.string.input_password_not_valid));
            }
        } else {
            progressBar.setVisibility(View.VISIBLE);

            dbAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(context, R.string.login_email_or_password_not_valid, Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

        }

    }
}