package com.example.e_barangay.Model;

public class User {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;
    private String mAddress;
    private String mBirthdate;
    private String mPlaceOfBirth;
    private String mGender;
    private String mCivilStatus;

    public User(){

    }

    public User(String mEmail, String mPassword)
    {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public User(String mFirstName, String mLastName, String mAddress, String mBirthdate,
                String mPlaceOfBirth, String mGender, String mCivilStatus) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mAddress = mAddress;
        this.mBirthdate = mBirthdate;
        this.mPlaceOfBirth = mPlaceOfBirth;
        this.mGender = mGender;
        this.mCivilStatus = mCivilStatus;
    }

    public String getFirstName() {
        return mFirstName;
    }
    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }
    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String mUserName) {
        this.mEmail = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }
    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getAddress(){ return mAddress; }
    public void setAddress(String address) {this.mAddress = address; }

    public String getBirthdate() {
        return mBirthdate;
    }
    public void setBirthdate(String mBirthdate) {
        this.mBirthdate = mBirthdate;
    }

    public String getPlaceOfBirth(){ return mPlaceOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth){ this.mPlaceOfBirth = placeOfBirth;}

    public String getGender(){return mGender;}
    public void setGender(String gender){this.mGender = gender;}

    public String getCivilStatus(){return mCivilStatus;}
    public void setCivilStatus(String mCivilStatus){this.mCivilStatus = mCivilStatus;}

}
