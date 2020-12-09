package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCheckBox;

    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //LOGS USER IN ONCE IT FINDS HE HAD LOGGED IN!
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        btSignIn = findViewById(R.id.sign_in_google_button);

        //Assign variable
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("150528410702-3dr1s2mhg73prpm4kfdi3vdps804utr0.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize sign in intent
                Intent intent = googleSignInClient.getSignInIntent();

                //Start activity for result
                startActivityForResult(intent, 100);
            }
        });

        checkSharedPreferences();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this,
                                                getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                //Shared Preferences checkbox
                if (mCheckBox.isChecked()) {
                    //save the checkbox pref.
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.apply();

                    //saved the email
                    String sEmail = inputEmail.getText().toString();
                    mEditor.putString(getString(R.string.user_email), sEmail);
                    //mEditor.commit();

                    //save the password
                    String sPassword = inputPassword.getText().toString();
                    mEditor.putString(getString(R.string.user_password), sPassword);

                } else {
                    //Sets the checkbox when the application starts
                    mEditor.putString(getString(R.string.checkbox), "False");
                    // mEditor.commit();

                    //saved the email
                    mEditor.putString(getString(R.string.user_email), "");
                    // mEditor.commit();

                    //save the password
                    mEditor.putString(getString(R.string.user_password), "");
                }
                mEditor.commit();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if (requestCode == 100) {
            //When request code is equal to 100
            //Initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            //Check connection
            if (signInAccountTask.isSuccessful()) {
                //When google sign in successfully
                //Initialize string
                final String s = "Google sign in successful";

                //Display toast
                displayToast(s);

                //Initialize sign in account
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);

                    //Check condition
                    if (googleSignInAccount != null) {
                        //When sign in account is not equal to null
                        //Initialize auth credentials
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        , null);

                        //Check credentials
                        auth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //Check condition
                                        if (task.isSuccessful()) {
                                            //When task is successful, redirect to main screen
                                            startActivity(new Intent(LoginActivity.this,
                                                    MainActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                            //Display Toast
                                            displayToast("Firebase authentication successful");
                                        } else {
                                            //WHen task is unsuccessful
                                            //Display toast
                                            displayToast("Authentication Failed" + task.getException().
                                                    getMessage());
                                        }

                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void checkSharedPreferences() {
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String email = mPreferences.getString(getString(R.string.user_email), "");
        String password = mPreferences.getString(getString(R.string.user_password), "");

        inputEmail.setText(email);
        inputPassword.setText(password);

        mCheckBox.setChecked(checkbox.equals("True"));
    }


}
