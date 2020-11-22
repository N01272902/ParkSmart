package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicReference;

public class SignUpActivity extends AppCompatActivity {


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private CheckBox mCheckbox;

   private EditText email;
   private EditText password;
    Button signUp;
    DatabaseReference reff;
    Button signIn;

    Member member;
    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mCheckbox = (CheckBox) findViewById(R.id.checkBox);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        signUp = findViewById(R.id.signUpButton);
        signIn = (Button) findViewById(R.id.signInButton);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // mPreferences = getSharedPreferences("",getApplicationContext().MODE_PRIVATE);
        mEditor = mPreferences.edit();

        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
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
                reff.child(String.valueOf(maxid +1)).setValue(member);
            }
        });

        checkSharedPreferences();

        signIn.setOnClickListener(new View.OnClickListener(){
            long childId=1;
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Member");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //String e = snapshot.child("emailAdress").getValue().toString();
                        //String p = snapshot.child("password").getValue().toString();
                        childId = snapshot.getChildrenCount();
                        //Toast.makeText(getApplicationContext(),String.valueOf(childId), Toast.LENGTH_SHORT).show();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String key = ds.getKey();
                            String e = ds.child("emailAdress").getValue(String.class);
                            if(ds.child("emailAdress").getValue(String.class).equals(email.getText().toString().trim())) {
                                //Toast.makeText(getApplicationContext(), ds.child("password").getValue(String.class), Toast.LENGTH_SHORT).show();
                                if(ds.child("password").getValue(String.class).equals(password.getText().toString().trim())){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    password.setError(getString(R.string.PasswordErr));
                                }
                            }
                            else{
                                email.setError(getString(R.string.emailUsrErr));
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(mCheckbox.isChecked()) {
                    //set a checkbox when the app starts
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    //save the email
                    String emailString = email.getText().toString();
                    mEditor.putString(getString(R.string.email), emailString);
                    mEditor.commit();

                    //save the password
                    String passString = password.getText().toString();
                    mEditor.putString(getString(R.string.password), passString);
                    mEditor.commit();

                }  else{
                    //set a checkbox when the app starts
                    mEditor.putString(getString(R.string.checkbox), "False");
                    mEditor.commit();

                    //save the email
                    mEditor.putString(getString(R.string.email),"");
                    mEditor.commit();

                    //save the password
                    mEditor.putString(getString(R.string.password),"");
                    mEditor.commit();

                }
            }
        });

        //Google signin button clicked
        SignInButton googleButton = findViewById(R.id.sign_in_google_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, R.string.google_button_toast, Toast.LENGTH_LONG).show();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        //Build a GoogleSignInClient with the options specified by gso
        GoogleSignInClient mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        //Check for existing Google Sign In account, if the user is already signed in
        //the GoogleSigInAccount will be non-null
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    }

    private void checkSharedPreferences() {
        String checkboxStr = mPreferences.getString(getString(R.string.checkbox), "False");
        String emailStr = mPreferences.getString(getString(R.string.email), "");
        String passwordStr = mPreferences.getString(getString(R.string.password), "");

        email.setText(emailStr);
        password.setText(passwordStr);

        if(checkboxStr.equals("True")){
            mCheckbox.setChecked(true);
        }else{
            mCheckbox.setChecked(false);
        }

    }

    @Override
    public void onBackPressed(){
        finishAffinity();
        finish();
    }
}