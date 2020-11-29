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

public class GateControlActivity extends Fragment {

    TextView Gate1;
    TextView Gate2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gate_control, container, false);

        Gate1 = (TextView) view.findViewById(R.id.StatusGate1);
        Gate2 = (TextView) view.findViewById(R.id.StatusGate2);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("Gate_Control");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String EntryGate = (String) dataSnapshot.child("Gate_1").getValue();
                Gate1.setText(EntryGate);
                String ExitGate = (String) dataSnapshot.child("Gate_2").getValue();
                Gate2.setText(ExitGate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button GateOpen1 = view.findViewById(R.id.openGateButton1);
        GateOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Gate_Control/Gate_1");
                myRef.setValue("OPEN");

            }
        });

        Button GateOpen2 = view.findViewById(R.id.openGateButton2);
        GateOpen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Gate_Control/Gate_2");
                myRef.setValue("OPEN");

            }
        });

        Button GateClose1 = view.findViewById(R.id.closeGateButton1);
        GateClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Gate_Control/Gate_1");
                myRef.setValue("CLOSE");

            }
        });

        Button GateClose2 = view.findViewById(R.id.closeGateButton2);
        GateClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Gate_Control/Gate_2");
                myRef.setValue("CLOSE");

            }
        });
        return view;
    }
}