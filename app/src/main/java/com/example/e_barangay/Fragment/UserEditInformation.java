package com.example.e_barangay.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_barangay.Activity.Login;
import com.example.e_barangay.Activity.UserHome;
import com.example.e_barangay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserEditInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEditInformation extends Fragment {

    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    private static final String FIREBASE_DATABASE_URL = "https://e-barangay-a3f2f-default-rtdb.asia-southeast1.firebasedatabase.app";

    private static final int REQUEST_SELECT_PHOTO = 1;
    private static final int REQUEST_CAPTURE_PHOTO = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    ImageView mUserPhoto;
    TextView mEmailAddress, mFirstName, mLastName, mAddress, mBirthdate, mPlaceOfBirth, mGender, mCivilStatus;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserEditInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserEditInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static UserEditInformation newInstance(String param1, String param2) {
        UserEditInformation fragment = new UserEditInformation();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button mUploadPhotoButton, mUpdateInformationButton, mLogoutButton;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_edit_information, container, false);

        mUploadPhotoButton = view.findViewById(R.id.upload_user_photo);
        mUpdateInformationButton = view.findViewById(R.id.update_user_information);
        mLogoutButton = view.findViewById(R.id.logout_button);

        mUserPhoto = view.findViewById(R.id.user_photo);
        mEmailAddress = view.findViewById(R.id.email_address);
        mFirstName = view.findViewById(R.id.first_name);
        mLastName = view.findViewById(R.id.last_name);
        mAddress = view.findViewById(R.id.address);
        mBirthdate = view.findViewById(R.id.birthdate);
        mPlaceOfBirth = view.findViewById(R.id.place_of_birth);
        mGender = view.findViewById(R.id.gender);
        mCivilStatus = view.findViewById(R.id.civil_status);

        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference postReference = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).getReference("users").child(userID);

        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String userImage = snapshot.child("0_userImage").getValue(String.class);
                    String emailAddress = snapshot.child("email").getValue(String.class);
                    String firstName = snapshot.child("1_firstname").getValue(String.class);
                    String lastName = snapshot.child("2_lastname").getValue(String.class);
                    String address = snapshot.child("3_address").getValue(String.class);
                    String birthdate = snapshot.child("4_birthdate").getValue(String.class);
                    String placeOfBirth = snapshot.child("5_placeOfBirth").getValue(String.class);
                    String gender = snapshot.child("6_gender").getValue(String.class);
                    String civilStatus = snapshot.child("7_civilStatus").getValue(String.class);

                    mEmailAddress.setText(emailAddress);
                    mFirstName.setText(firstName);
                    mLastName.setText(lastName);
                    mAddress.setText(address);
                    mBirthdate.setText(birthdate);
                    mPlaceOfBirth.setText(placeOfBirth);
                    mGender.setText(gender);
                    mCivilStatus.setText(civilStatus);

                    if(snapshot.child("0_userImage").exists())
                    {
                        String base64EncodedPhoto = userImage;
                        byte[] decodedBytes = Base64.decode(base64EncodedPhoto, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        mUserPhoto.setImageBitmap(bitmap);
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

        mUploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    showPhotoOptionsDialog();
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
        databaseReference = firebaseDatabase.getReference("users");
        mUpdateInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = builder.create().getLayoutInflater();
                View editInfoDialogView = inflater.inflate(R.layout.edit_information_dialog, null);

                EditText mFirstNameEditText, mLastNameEditText, mAddressEditText, mBirthdateEditText,
                        mPlaceOfBirth, mGender, mCivilStatus;

                mFirstNameEditText = editInfoDialogView.findViewById(R.id.editInfo_editText_firstName);
                mLastNameEditText = editInfoDialogView.findViewById(R.id.editInfo_editText_lastName);
                mAddressEditText = editInfoDialogView.findViewById(R.id.editInfo_editText_address);
                mBirthdateEditText = editInfoDialogView.findViewById(R.id.editInfo_editText_birthDate);
                mPlaceOfBirth = editInfoDialogView.findViewById(R.id.editInfo_editText_placeOfBirth);
                mGender = editInfoDialogView.findViewById(R.id.editInfo_editText_gender);
                mCivilStatus = editInfoDialogView.findViewById(R.id.editInfo_editText_civilStatus);

                DatabaseReference userIDDatabaseReference = databaseReference.child(userID);
                userIDDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists())
                       {
                           String[] childNodeKeys = {"1_firstname", "2_lastname", "3_address", "4_birthdate", "5_placeOfBirth", "6_gender", "7_civilStatus"};
                           for(String childNodeKey : childNodeKeys)
                           {
                               if(snapshot.hasChild(childNodeKey))
                               {
                                   String childNodeValue = snapshot.child(childNodeKey).getValue(String.class);
                                   switch (childNodeKey)
                                   {
                                       case "1_firstname" :  mFirstNameEditText.setText(childNodeValue);
                                           break;
                                       case "2_lastname" :  mLastNameEditText.setText(childNodeValue);
                                           break;
                                       case "3_address" :  mAddressEditText.setText(childNodeValue);
                                           break;
                                       case "4_birthdate" :  mBirthdateEditText.setText(childNodeValue);
                                           break;
                                       case "5_placeOfBirth" :  mPlaceOfBirth.setText(childNodeValue);
                                           break;
                                       case "6_gender" :  mGender.setText(childNodeValue);
                                           break;
                                       case "7_civilStatus" :  mCivilStatus.setText(childNodeValue);
                                           break;
                                   }
                               }
                           }
                       }
                       else
                       {
                           Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                            //no data found
                       }
                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
                builder.setCancelable(false);
                builder.setView(editInfoDialogView)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String firstName = mFirstNameEditText.getText().toString();
                                String lastName = mLastNameEditText.getText().toString();
                                String address = mAddressEditText.getText().toString();
                                String birthDate = mBirthdateEditText.getText().toString();
                                String placeOfBirth = mPlaceOfBirth.getText().toString();
                                String gender = mGender.getText().toString();
                                String civilStatus = mCivilStatus.getText().toString();

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userID = firebaseAuth.getCurrentUser().getUid();
                                        DatabaseReference postDatabaseReference = databaseReference.child(userID);

                                        Map<String, Object> updateData = new HashMap<>();
                                        updateData.put("1_firstname", firstName);
                                        updateData.put("2_lastname", lastName);
                                        updateData.put("3_address", address);
                                        updateData.put("4_birthdate", birthDate);
                                        updateData.put("5_placeOfBirth", placeOfBirth);
                                        updateData.put("6_gender", gender);
                                        updateData.put("7_civilStatus", civilStatus);

                                        postDatabaseReference.updateChildren(updateData);
                                        //postDatabaseReference.child(userID).updateChildren((Map<String, Object>) residentProfile);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Toast.makeText(getActivity(), firstName + lastName + address + birthDate + placeOfBirth + gender + civilStatus, Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();

                Intent intent = new Intent(getActivity(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showPhotoOptionsDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose an option")
                .setItems(new CharSequence[]{"Capture Photo", "Select Photo"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i)
                        {
                            case 0: capturePhoto();
                                    break;
                            case 1: selectPhoto();
                                    break;
                        }
                    }
                })
                .show();
    }

    private void capturePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAPTURE_PHOTO);
    }
    private void selectPhoto()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_CAPTURE_PHOTO && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                mUserPhoto.setImageBitmap(photo);

                ByteArrayOutputStream convertImageToArray = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, convertImageToArray);
                byte[] photoBytes = convertImageToArray.toByteArray();

                String base64EncodedPhoto = Base64.encodeToString(photoBytes, Base64.DEFAULT);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userID = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference postDatabaseReference = databaseReference.child(userID);

                        Map<String, Object> updateData = new HashMap<>();
                        updateData.put("0_userImage", base64EncodedPhoto);

                        postDatabaseReference.updateChildren(updateData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else if(requestCode == REQUEST_SELECT_PHOTO && data != null)
            {
                Uri selectedImageUri = data.getData();
                mUserPhoto.setImageURI(selectedImageUri);

                InputStream inputStream;

                try
                {
                    ContentResolver contentResolver = requireActivity().getContentResolver();
                    inputStream = contentResolver.openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int len;

                while (true) {
                    try {
                        if (!((len = inputStream.read(buffer)) != -1)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    byteBuffer.write(buffer, 0, len);
                }
                byte[] photoBytes = byteBuffer.toByteArray();

                String base64EncodedPhoto = Base64.encodeToString(photoBytes, Base64.DEFAULT);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userID = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference postDatabaseReference = databaseReference.child(userID);

                        Map<String, Object> updateData = new HashMap<>();
                        updateData.put("0_userImage", base64EncodedPhoto);

                        postDatabaseReference.updateChildren(updateData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }
}
