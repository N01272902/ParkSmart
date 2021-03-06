package com.smarttech.parksmart;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_profile, container, false);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Update user inputs
        final EditText displayName = view.findViewById(R.id.displayName);
        final EditText displayEmail = view.findViewById(R.id.displayEmail);
        Button savebtn = view.findViewById(R.id.profileSaveBtn);
        final TextView displayEmail2 = view.findViewById(R.id.displayEmail2);

        //final TextView errorMsg = view.findViewById(R.id.errorMsgTV);

        //check validation
        if (user != null) {
            //Sets name and email textView
            displayName.setText(user.getDisplayName());
            displayEmail.setText(user.getEmail());
            displayEmail2.setText(user.getEmail());
        }

        //Removes the edit email option
        String userInformation = user.getIdToken(false).getResult().getSignInProvider();
        if (userInformation.equals("google.com")) {
            displayEmail.setVisibility(View.GONE);
            displayEmail2.setVisibility(View.VISIBLE);
        }

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = displayEmail.getText().toString();
                String newName = displayName.getText().toString();

                //Updates the user's display name
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName).build();
                if (user != null && !newName.trim().equals("")) {
                    user.updateProfile(profileUpdates);
                }

                //Allows user to change email if not signed in with gmail account
                String userInfo = user.getIdToken(false).getResult().getSignInProvider();
                if (!newEmail.trim().equals("") && Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
                        && !userInfo.equals("google.com")) {
                    user.updateEmail(newEmail.trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), getString(R.string.emailName_update_successful), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.invalid_email_msg), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    // errorMsg.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), getString(R.string.name_updated_msg), Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
