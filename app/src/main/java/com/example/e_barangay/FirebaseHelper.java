package com.example.e_barangay;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_barangay.Activity.UserHome;
import com.example.e_barangay.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;

    private static final String FIREBASE_DATABASE_URL = "https://e-barangay-a3f2f-default-rtdb.asia-southeast1.firebasedatabase.app";

    public static void loginUser(String email, String password, final Context context)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(context, "Successfully Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, UserHome.class);
                        context.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    //TODO: Refactor FirebaseAuth
    public static void registerUser(User user, FirebaseAuth firebaseAuth, Context context)
    {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
                            databaseReference = firebaseDatabase.getReference("users");

                            String userID = firebaseAuth.getCurrentUser().getUid();

                            databaseReference.child(userID).setValue(user);

                            Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, UserHome.class);
                            context.startActivity(intent);
                        }
                        else
                        {
                            String registrationErrorMessage = task.getException().getMessage();
                            Toast.makeText(context, registrationErrorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
