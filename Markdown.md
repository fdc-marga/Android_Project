# Database 
* Connect App to Database - https://firebase.google.com/docs/database/android/start
* Write and Read data in Database - https://firebase.google.com/docs/database/android/read-and-write
* Create database first in Firebase 
# Store data to database
- e-barangay -> rootnode
  -  user -> reference
      - userID(user UID - Auth in firebase) -> child
        - userImage -> setValue from model(UserProfile - DataClass) 
        - firstName -> setValue from model(UserProfile - DataClass) 
        - lastName -> setValue from model(UserProfile - DataClass) 
        - email -> setValue from model(UserProfile - DataClass) 
        - password -> setValue from model(UserProfile - DataClass) 
        - address -> setValue from model(UserProfile - DataClass) 
        - birthdate -> setValue from model(UserProfile - DataClass) 
        - placeOfBirth
        - gender
        - civilStatus

```
FirebaseDatabase rootnode;
DatabaseReference reference ;

rootnode = FirebaseDatabase.getInstance(); (GetInstance not us-central1)https://firebase.google.com/docs/reference/android/com/google/firebase/database/FirebaseDatabase#getInstance(java.lang.String)
reference = rootnode.getReferece("user")

referece.child(userID).setValue(Dataclass (firstname)) //add data to reference
```

# Get User UID
rootNode.getCurrentUser
rootNode.getUID()
String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

# AlertDialog for EditInformation
Layout Inflater 
- responsible for building a view given a layout resource xml file. Basically, they instantiate the layout XML files into view objects.
- can be used if you have custom layout and display via AlertDialog

- use AlertDialog so that user is required to input user Information. Add to Registration.java after StartActivity(intent) 

```
 public class StartGameDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        // Add action buttons
               .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                       // sign in the user ...
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       LoginDialogFragment.this.getDialog().cancel();
                   }
               });
        return builder.create();
    }
}
```

# Barangay Clearance Data needed
- Full Name
- Address
- Date of Birth
- Place of Birth
- Gender
- Civil Status
- Purpose

- CTC No. 
- Place of Issue:
- Date of Issue:
- O.R. No.:
- Date of Issue:

- Image
- Baranggay Captain Signature

URL: https://www.facebook.com/279722942042287/posts/new-barangay-clearance-certificate/2370315342983026/

# Auto Increment userID
##Option 1
1. Declare maxID = 0;
2. call referece.addValueEventListener
3. under onDataChange method add the following: if(dataSnapshot.exists()) {maxID = dataSnapshot.getChildrenCount}
4. under onClick reff.child(maxID+1).setValue(Dataclass (firstname)); //butangan nimo ug value ang child

### User
- UserID
- FirstName
- LastName
- Email
- Passsword
- Birthdate
- Status
- Gender
### Transaction
- UserID
- Application_Status
- Admin_Reason
- Date_Created

#PERMISSION
Internet = <uses-permission android:name="android.permission.INTERNET" />

# Add Auth
SPRINT # 1
* Create account in Firebase and add project
* Add admin account to database manually - admin/admin123 - N/A

* Add firebase to adnroid project - https://firebase.google.com/docs/android/setup#console

Add residence data to firebase:
- https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account
- https://firebase.google.com/docs/database/android/read-and-write

Signin user:
- https://firebase.google.com/docs/auth/android/password-auth#sign_in_a_user_with_an_email_address_and_password

SPRINT # 2
ViewPager using Button Tab for Home Activity

- Apply Barangay Clearance Tab
  > Add User Reason to Database

- Transaction
  > RecycleView for the display
  > Retrieve data from database (B.C Tab -> database -> Transaction)
  > Document will be open to new Activity(Edit Text then change variable only

- Edit Information
  > Retrieve Data from dataabse
  > Update data to data base

- Logout
  > If back button is pressed/clicked dapat dli ra siya ma balik sa login page

SPRING # 3
ViewPager using Button Tab for Home Activity

- Registered Residence
  > Retrieved data from database
  > Delete User -> if not found send info to user nga please contact your administrator

- Application for Baranggay Clearnce 
  > Retrieve data from data base
  > Update data to database -> Approved or Denied

- Logout
  > If back button is pressed/clicked dapat dli ra siya ma balik sa login page

# Project Structure
- Activity
  - Login
  - Registration
  - UserHomePage
  - AdminHomePage
- Enum
  - Gender
  - MaritalStatus
- Model
  - ResidenceProfile
- Fragment
  - ApplicationForBarangayCleanrance
  - EditInformation
  - RegisteredResidence
  - BarangayClearnaceApplicants

# TO DO LIST - FUNCTIONALITY
- [ ] Connect Application to Firebase API
- [ ] Add residence data during registration to database
- [ ] Retrieve data from database

Improvement
Payment Method 
Before niya ma view ang file need siya mo bayad

https://www.youtube.com/watch?v=QAKq8UBv4GI&t=1851s

limitation:
> Viewing lang dli sa ma DL ang application


