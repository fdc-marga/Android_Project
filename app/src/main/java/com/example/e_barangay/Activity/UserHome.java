package com.example.e_barangay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.e_barangay.Fragment.UserBCApplication;
import com.example.e_barangay.Fragment.UserEditInformation;
import com.example.e_barangay.Fragment.UserHomeFragment;
import com.example.e_barangay.Fragment.UserTransaction;
import com.example.e_barangay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private UserHomeFragment userHomeFragment;
    private UserBCApplication userBCApplication;
    private UserEditInformation userEditInformation;
    private UserTransaction userTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_user_home);

        userHomeFragment = new UserHomeFragment();
        userBCApplication = new UserBCApplication();
        userEditInformation = new UserEditInformation();
        userTransaction = new UserTransaction();

        bottomNavigationView = findViewById(R.id.userHome_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //to display HomeFragment after login
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        switch(item.getItemId())
        {
            case R.id.userHome:
                selectedFragment = userHomeFragment;
                break;
            case R.id.userHome_bcApplication:
                selectedFragment = userBCApplication;
                break;
            case R.id.userHome_userTransaction:
                selectedFragment = userTransaction;
                break;
            case R.id.userHome_userInformation:
                selectedFragment = userEditInformation;
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.userFrame_container, selectedFragment)
                .commit();

        return true;
    }

    //TODO: Current screen(Navigation Bottom) click back button last activity or fragment
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(UserHome.this, UserHome.class);
        startActivity(intent);
    }
}