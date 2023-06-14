package com.example.e_barangay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_barangay.FirebaseHelper;
import com.example.e_barangay.Model.User;
import com.example.e_barangay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText mEmailEditTextLogin, mPasswordEditTextLogin;
    private Button mLoginButton;
    private TextView mRegisterButtonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_login_page);

        mEmailEditTextLogin = findViewById(R.id.login_ediText_email);
        mPasswordEditTextLogin = findViewById(R.id.login_editText_password);
        mLoginButton = findViewById(R.id.login_button);
        mRegisterButtonEditText = findViewById(R.id.login_button_register);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmailLogin = mEmailEditTextLogin.getText().toString();
                String userPasswordLogin = mPasswordEditTextLogin.getText().toString();

                if(userEmailLogin.isEmpty() || userPasswordLogin.isEmpty())
                {
                    Toast.makeText(Login.this, "Please input Email/Password Field!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseHelper.loginUser(userEmailLogin, userPasswordLogin, Login.this);
                }
            }
        });

        mRegisterButtonEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}