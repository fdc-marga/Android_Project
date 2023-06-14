package com.example.e_barangay.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_barangay.Activity.UserHome;
import com.example.e_barangay.Model.User;
import com.example.e_barangay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserBCApplication#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserBCApplication extends Fragment {

    private static final String FIREBASE_DATABASE_URL = "https://e-barangay-a3f2f-default-rtdb.asia-southeast1.firebasedatabase.app";

    UserTransaction fragment = new UserTransaction();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserBCApplication() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserBCApplication.
     */
    // TODO: Rename and change types and number of parameters
    public static UserBCApplication newInstance(String param1, String param2) {
        UserBCApplication fragment = new UserBCApplication();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barangay_clearance_application_form, container, false);

        EditText userPurpose;
        Button submitButton;

        userPurpose = view.findViewById(R.id.bcApplication_editText_userPurpose);
        submitButton = view.findViewById(R.id.bcApplication_button_submit);

        userPurpose.setText("");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty((userPurpose.getText().toString())))
                {
                    Toast.makeText(getActivity(), "Please input reason!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Transaction").child(userID).push();

                    String getUniqueKey = databaseReference.getKey();
                    DatabaseReference newReference = databaseReference.child(getUniqueKey);

                    newReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String userReason = userPurpose.getText().toString();

                            String application_status = "APPROVED";
                            String admin_reason = "-";

                            Map<String, Object> updateData = new HashMap<>();
                            //updateData.put("1_userID", userID);
                            updateData.put("2_applicationStatus", application_status);
                            updateData.put("3_userPurpose", userReason);
                            updateData.put("4_adminReason", admin_reason);
                            updateData.put("5_transactionCreated", getCurrentDateAndTime());

                            databaseReference.updateChildren(updateData);

                            Toast.makeText(getActivity(), "Successfully Applied!", Toast.LENGTH_SHORT).show();

                            FragmentManager fragmentManager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.userFrame_container, fragment);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        return view;
    }

    public String getCurrentDateAndTime()
    {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        String formattedDate = dateFormat.format(currentDate);
        String formattedTime = timeFormat.format(currentDate);

        String currentDateTime = formattedDate + " " + formattedTime;

        return  currentDateTime;
    }
}