package com.example.e_barangay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_barangay.FirebaseHelper;
import com.example.e_barangay.Model.User;
import com.example.e_barangay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText mEmailEditTextRegister, mPasswordEditTextRegister;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_registration_page);

        mEmailEditTextRegister = findViewById(R.id.register_ediText_email);
        mPasswordEditTextRegister = findViewById(R.id.register_editText_password);
        mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = mEmailEditTextRegister.getText().toString();
                String userPassword = mPasswordEditTextRegister.getText().toString();

                if(userEmail.isEmpty() || userPassword.isEmpty())
                {
                    Toast.makeText(Registration.this, "Please input Email/Password Fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    User user = new User(userEmail, userPassword);
                    FirebaseHelper.registerUser(user, firebaseAuth, Registration.this);
                }
            }
        });
    }
}