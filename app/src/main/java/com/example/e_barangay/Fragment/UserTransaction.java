package com.example.e_barangay.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_barangay.Model.Transaction;
import com.example.e_barangay.Model.TransactionAdapter;
import com.example.e_barangay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTransaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTransaction extends Fragment {
    private static final String FIREBASE_DATABASE_URL = "https://e-barangay-a3f2f-default-rtdb.asia-southeast1.firebasedatabase.app";

    /*private RecyclerView transactionRecyclerView;
    TextView mBarangayClearance;*/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    TransactionAdapter transactionAdapter;
    Button mViewFile;
    public UserTransaction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserTransaction.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTransaction newInstance(String param1, String param2) {
        UserTransaction fragment = new UserTransaction();
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

        ArrayList<Transaction> transaction = new ArrayList<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        View view = inflater.inflate(R.layout.fragment_user_transaction, container, false);

        mViewFile = view.findViewById(R.id.viewFile);

        DatabaseReference postReference = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).getReference("Transaction").child(userID);

        recyclerView = view.findViewById(R.id.transactionRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        transactionAdapter = new TransactionAdapter(transaction);
        recyclerView.setAdapter(transactionAdapter);

        // TODO: if empty No Transaction will display
        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    transaction.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        ArrayList<String> data = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
                            data.add(childDataSnapshot.getValue().toString());
                        }

                        String key = dataSnapshot.getKey();
                        Transaction transactionID = new Transaction(data.get(0), data.get(1), data.get(2), data.get(3));
                        transactionID.setTransactionID(key);

                        transaction.add(transactionID);
                    }
                    transactionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}