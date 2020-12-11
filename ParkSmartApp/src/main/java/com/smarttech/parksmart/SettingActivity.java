package com.smarttech.parksmart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends Fragment {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences, sharedPreferences2 = null;
    SwitchCompat switchCompat, switchCompat2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);

        //Gets current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        switchCompat = view.findViewById(R.id.switchNightMode);
        switchCompat2 = view.findViewById(R.id.switchNotifications);

        sharedPreferences = getActivity().getSharedPreferences("night", 0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    editor.commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    editor.commit();
                }
            }
        });

        sharedPreferences2 = getActivity().getSharedPreferences("notifs", 0);
        Boolean booleanValue2 = sharedPreferences2.getBoolean("notifs_mode", true);
        if (booleanValue2) {
            switchCompat2.setChecked(true);
        }

        switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchCompat2.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putBoolean("notifs_mode", true);
                    editor.apply();
                } else {
                    switchCompat2.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putBoolean("notifs_mode", false);
                    editor.apply();
                }
            }
        });

        final EditText oldPass = view.findViewById(R.id.oldPassword);
        final EditText newPass = view.findViewById(R.id.newPassword);
        final EditText confirm_newPass = view.findViewById(R.id.confirm_newPassword);
        final Button confirmPass = view.findViewById(R.id.confirmPassbtn);
        final Button cancelPass = view.findViewById(R.id.cancelBtn);
        final Button deleteAcc = view.findViewById(R.id.deleteAccountbtn);

        //gets the change pass attribute visibility
        final TextInputLayout inputLayout1 = view.findViewById(R.id.inputlayout1);
        final TextInputLayout inputLayout2 = view.findViewById(R.id.inputlayout2);
        final TextInputLayout inputLayout3 = view.findViewById(R.id.inputlayout3);

        Button chgPass = view.findViewById(R.id.changePasswordbtn);
        chgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLayout1.setVisibility(View.VISIBLE);
                inputLayout2.setVisibility(View.VISIBLE);
                inputLayout3.setVisibility(View.VISIBLE);
                confirmPass.setVisibility(View.VISIBLE);
                cancelPass.setVisibility(View.VISIBLE);
            }
        });

        //Removes the password change option for google sign in users
        String userInformation = user.getIdToken(false).getResult().getSignInProvider();
        if (userInformation.equals("google.com")) {
            chgPass.setVisibility(View.GONE);
        }

        cancelPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //refreshes the activity
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingActivity()).commit();
            }
        });

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };


        confirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = oldPass.getText().toString();
                final String new_password = newPass.getText().toString();
                final String confirm_new_password = confirm_newPass.getText().toString();


                final String email = user.getEmail();

                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = fAuth.getCurrentUser();
                if (user != null && !old_password.equals(new_password) && new_password.length() >= 6
                        && old_password.length() != 0) {
                    if (new_password.equals(confirm_new_password)) {
                        AuthCredential credential = EmailAuthProvider.getCredential(email, old_password);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(confirm_new_password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task1) {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Password updated",
                                                        Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getActivity(), "Unsuccessful, check old password",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Incorrect old password OR signed in with G-mail",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "New passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Password must be more than 6 letters and Old password cannot equal New password!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setMessage(R.string.DialogDeleteMsg);
                builder.setPositiveButton(R.string.DialogYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser fuser = firebaseAuth.getCurrentUser();
                        //if user pressed "yes", goes to sign up screen
                        if (fuser != null) {
                            FirebaseUser firebaseuser = firebaseAuth.getCurrentUser();
                            firebaseuser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getActivity(), SignUpActivity.class));
                                                getActivity().finish();
                                            } else {
                                                Toast.makeText(getActivity(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
                builder.setNegativeButton(R.string.DialogNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }
}
