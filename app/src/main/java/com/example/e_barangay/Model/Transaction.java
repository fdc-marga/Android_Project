package com.example.e_barangay.Model;

import java.util.ArrayList;

public class Transaction {
    String mUserID;
    String mApplicationStatus;
    String mUserPurpose;
    String mAdminReason;
    String mCreatedTransaction;
    String mTransactionID;

    public Transaction() {
    }

    public Transaction(/*String mUserID,*/ String mApplicationStatus, String mUserPurpose, String mAdminReason, String mCreatedTransaction) {
        //this.mUserID = mUserID;
        this.mApplicationStatus = mApplicationStatus;
        this.mUserPurpose = mUserPurpose;
        this.mAdminReason = mAdminReason;
        this.mCreatedTransaction = mCreatedTransaction;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getApplicationStatus() {
        return mApplicationStatus;
    }

    public void setApplicationStatus(String mApplicationStatus) {
        this.mApplicationStatus = mApplicationStatus;
    }

    public String getUserPurpose() {
        return mUserPurpose;
    }

    public void setUserPurpose(String mUserPurpose) {
        this.mUserPurpose = mUserPurpose;
    }

    public String getAdminReason() {
        return mAdminReason;
    }

    public void setAdminReason(String mAdminReason) {
        this.mAdminReason = mAdminReason;
    }

    public String getCreatedTransaction() {
        return mCreatedTransaction;
    }

    public void setCreatedTransaction(String mCreatedTransaction) {
        this.mCreatedTransaction = mCreatedTransaction;
    }
    public String getTransactionID() {
        return mTransactionID;
    }

    public void setTransactionID(String mTransactionID) {
        this.mTransactionID = mTransactionID;
    }
}
