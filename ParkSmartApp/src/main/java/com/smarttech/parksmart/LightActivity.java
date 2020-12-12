package com.smarttech.parksmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightActivity extends Fragment {

    TextView Light1;
    TextView Light2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_light, container, false);

        Light1 = (TextView) view.findViewById(R.id.StatusLight1);
        Light2 = (TextView) view.findViewById(R.id.StatusLight2);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("Light_Control");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String EntryGate = (String) dataSnapshot.child("Light_1").getValue();
                Light1.setText(EntryGate);
                String ExitGate = (String) dataSnapshot.child("Light_2").getValue();
                Light2.setText(ExitGate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button GateOpen1 = view.findViewById(R.id.lightOn1);
        GateOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Light_Control/Light_1");
                myRef.setValue("ON");
            }
        });

        Button GateOpen2 = view.findViewById(R.id.lightOn2);
        GateOpen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Light_Control/Light_2");
                myRef.setValue("ON");
            }
        });

        Button GateClose1 = view.findViewById(R.id.lightOff1);
        GateClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Light_Control/Light_1");
                myRef.setValue("OFF");
            }
        });

        Button GateClose2 = view.findViewById(R.id.lightOff2);
        GateClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Light_Control/Light_2");
                myRef.setValue("OFF");
            }
        });
        return view;
    }
}