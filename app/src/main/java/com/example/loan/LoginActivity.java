package com.example.loan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dev.sagar.progress_button.ProgressButton;

public class LoginActivity extends AppCompatActivity {
    EditText et1, et2;
    ProgressButton progress_btn;
    TextView tv1, tv2;
    CheckBox checkBox;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1 = findViewById(R.id.username);
        et2 = findViewById(R.id.password);
        tv1 = findViewById(R.id.signup_tv);
        tv2 = findViewById(R.id.terms);
        checkBox = findViewById(R.id.checkbox);
        progress_btn = findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iv = new Intent(LoginActivity.this, TermsActivity.class);
                startActivity(iv);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iv = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(iv);
            }
        });

        progress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et1.getText().toString().trim();
                String passwrod = et2.getText().toString().trim();
                if (username.isEmpty()) {
                    et1.setError("Required");
                    return;
                }

                if (passwrod.isEmpty()) {
                    et2.setError("Required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    et1.setError("Invalid Email Format");
                    return;
                }
                if (!checkBox.isChecked()) {
                    tv2.setError("Accept");
                    return;
                }
                progress_btn.loading();
                mAuth.signInWithEmailAndPassword(username, passwrod)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progress_btn.finished();
                                    Intent iv = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(iv);
                                    finish();
                                } else {
                                    progress_btn.enable();
                                    Toast.makeText(LoginActivity.this,
                                            "Email or Password incorrect.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}