package com.example.e_barangay.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_barangay.Fragment.BarangayClearanceFragment;
import com.example.e_barangay.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>
{
    ArrayList<Transaction> transactionArrayList;

    private static final String FIREBASE_DATABASE_URL = "https://e-barangay-a3f2f-default-rtdb.asia-southeast1.firebasedatabase.app";


    public TransactionAdapter(ArrayList<Transaction> transactionArrayList)
    {
        this.transactionArrayList = transactionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_transaction_single_row_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.mUserIDTextView.setText(transactionArrayList.get(position).getUserID());
        BarangayClearanceFragment fragment = new BarangayClearanceFragment();
        Transaction transactionGetID = transactionArrayList.get(position);

        holder.mTransactionDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String userID = firebaseAuth.getCurrentUser().getUid();

                String getID = transactionGetID.getTransactionID();

                DatabaseReference postReference = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).getReference("Transaction").child(userID).child(getID);
                postReference.removeValue();

            }
        });

        holder.viewFile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.userFrame_container, fragment);
                fragmentTransaction.commit();

                BottomNavigationView bottomNavigationView = ((AppCompatActivity) view.getContext()).findViewById(R.id.userHome_navigation);
                bottomNavigationView.setVisibility(View.GONE);

                String userPurpose = transactionArrayList.get(position).getUserPurpose();
                Bundle args = new Bundle();
                args.putString("getUserPurpose", userPurpose); // Replace "key" with your desired key and "value" with the actual data
                fragment.setArguments(args);
            }
        });
        holder.mApplicationStatusTextView.setText(transactionArrayList.get(position).getApplicationStatus());
        holder.mUserPurposeTextView.setText(transactionArrayList.get(position).getUserPurpose());
        holder.mAdminReasonTextView.setText(transactionArrayList.get(position).getAdminReason());
        holder.mTransactionCreated.setText(transactionArrayList.get(position).getCreatedTransaction());
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUserIDTextView, mApplicationStatusTextView, mUserPurposeTextView, mAdminReasonTextView, mTransactionCreated;
        Button viewFile;

        ImageButton mTransactionDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //mUserIDTextView = itemView.findViewById(R.id.userID);
            mTransactionDeleteButton = itemView.findViewById(R.id.transaction_delete_button);
            viewFile = itemView.findViewById(R.id.viewFile);
            mApplicationStatusTextView = itemView.findViewById(R.id.application_status);
            mUserPurposeTextView = itemView.findViewById(R.id.user_purpose);
            mAdminReasonTextView = itemView.findViewById(R.id.admin_reason);
            mTransactionCreated = itemView.findViewById(R.id.transaction_created);
        }
    }
}
