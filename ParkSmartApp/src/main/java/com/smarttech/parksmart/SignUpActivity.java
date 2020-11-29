package com.smarttech.parksmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signUp;
    DatabaseReference reff;
    Button signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    Member member;
    long maxid = 0;
    private static int RC_SIGN_IN = 123;
    CheckBox savelogincheckbox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        signUp = findViewById(R.id.signUpButton);
        signIn = findViewById(R.id.signInButton);//
        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        savelogincheckbox = findViewById(R.id.checkBox);
        editor = sharedPreferences.edit();


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setEmailAdress(email.getText().toString().trim());
                member.setPassword(password.getText().toString().trim());
                reff.child(String.valueOf(maxid + 1)).setValue(member);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            long childId = 1;

            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Member");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //String e = snapshot.child("emailAdress").getValue().toString();
                        //String p = snapshot.child("password").getValue().toString();
                        childId = snapshot.getChildrenCount();
                        //Toast.makeText(getApplicationContext(),String.valueOf(childId), Toast.LENGTH_SHORT).show();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String key = ds.getKey();
                            String e = ds.child("emailAdress").getValue(String.class);
                            if (ds.child("emailAdress").getValue(String.class).equals(email.getText().toString().trim())) {
                                //Toast.makeText(getApplicationContext(), ds.child("password").getValue(String.class), Toast.LENGTH_SHORT).show();
                                if (ds.child("password").getValue(String.class).equals(password.getText().toString().trim())) {
                                    email.setError(null);
                                    password.setError(null);
                                    //Saving data in the device
                                    if (savelogincheckbox.isChecked()) {
                                        editor.putBoolean("savelogin", true);
                                        editor.putString("username", email.getText().toString().trim());
                                        editor.putString("password", password.getText().toString().trim());
                                        editor.commit();
                                    }
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    password.setError("Incorrect password");
                                }
                            } else {
                                email.setError("User does not exist");
                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        SignInButton googleButton = findViewById(R.id.sign_in_google_button);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        //Build a GoogleSignInClient with the options specified by gs
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Check for existing Google Sign In account, if the user is already signed in
        //the GoogleSigInAccount will be non-null
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        savelogin = sharedPreferences.getBoolean("savelogin", true);
        if (savelogin == true)
            email.setText(sharedPreferences.getString("username", null));
        password.setText(sharedPreferences.getString("password", null));
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    private void signUp() {
        //Toast.makeText(SignUpActivity.this, "Sign in with Google Clicked", Toast.LENGTH_LONG).show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Sign failed", e.toString());
        }
    }


}