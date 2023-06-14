package com.example.e_barangay.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_barangay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarangayClearanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarangayClearanceFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView mFullNameBC, mAddressBC, mDateOfBirthBC, mPlaceOfBirthBC, mGenderBC, mCivilStatusBC, mPurposeBC;
    ImageView mUserImageBC;

    private static final String FIREBASE_DATABASE_URL = "https://e-barangay-a3f2f-default-rtdb.asia-southeast1.firebasedatabase.app";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BarangayClearanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarangayClearanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarangayClearanceFragment newInstance(String param1, String param2) {
        BarangayClearanceFragment fragment = new BarangayClearanceFragment();
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
        View view = inflater.inflate(R.layout.fragment_barangay_clearance_file, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
        databaseReference = firebaseDatabase.getReference("users").child(userID);

        mUserImageBC = view.findViewById(R.id.bc_userImage);
        mFullNameBC = view.findViewById(R.id.bc_fullName);
        mAddressBC = view.findViewById(R.id.bc_address);
        mDateOfBirthBC = view.findViewById(R.id.bc_dateOfBirth);
        mPlaceOfBirthBC = view.findViewById(R.id.bc_placeOfBirth);
        mGenderBC = view.findViewById(R.id.bc_gender);
        mCivilStatusBC = view.findViewById(R.id.bc_civilStatus);
        mPurposeBC = view.findViewById(R.id.bc_userPurpose);

        Bundle args = getArguments();
        if (args != null) {
            String value = args.getString("getUserPurpose");
            mPurposeBC.setText(value);
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userImage = snapshot.child("0_userImage").getValue(String.class);
                    String firstName = snapshot.child("1_firstname").getValue(String.class);
                    String lastName = snapshot.child("2_lastname").getValue(String.class);
                    String address = snapshot.child("3_address").getValue(String.class);
                    String birthdate = snapshot.child("4_birthdate").getValue(String.class);
                    String placeOfBirth = snapshot.child("5_placeOfBirth").getValue(String.class);
                    String gender = snapshot.child("6_gender").getValue(String.class);
                    String civilStatus = snapshot.child("7_civilStatus").getValue(String.class);

                    String userFullName = firstName + " " + lastName;

                    mFullNameBC.setText(userFullName);
                    mAddressBC.setText(address);
                    mDateOfBirthBC.setText(birthdate);
                    mPlaceOfBirthBC.setText(placeOfBirth);
                    mGenderBC.setText(gender);
                    mCivilStatusBC.setText(civilStatus);

                    if(snapshot.child("0_userImage").exists())
                    {
                        String base64EncodedPhoto = userImage;
                        byte[] decodedBytes = Base64.decode(base64EncodedPhoto, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        mUserImageBC.setImageBitmap(bitmap);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please upload photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}