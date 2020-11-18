package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicReference;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signUp;
    DatabaseReference reff;
    Button signIn;

    Member member;
    long maxid=0;
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

        signIn.setOnClickListener(new View.OnClickListener(){
            long childId=1;
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
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String key = ds.getKey();
                            String e = ds.child("emailAdress").getValue(String.class);
                            if(ds.child("emailAdress").getValue(String.class).equals(email.getText().toString().trim())) {
                                //Toast.makeText(getApplicationContext(), ds.child("password").getValue(String.class), Toast.LENGTH_SHORT).show();
                                if(ds.child("password").getValue(String.class).equals(password.getText().toString().trim())){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    password.setError("Incorrect password");
                                }
                            }
                            else{
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
    }
}