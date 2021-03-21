package com.smarttech.parksmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ScheduleViewActivity extends Fragment {

    TextView monS, monE;
    TextView tueS, tueE;
    TextView wedS, wedE;
    TextView thurS, thurE;
    TextView friS, friE;
    TextView satS, satE;
    TextView sunS, sunE;

    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule_view_activity, container, false);

        final Button editButton = view.findViewById(R.id.editSchedule);

        //Admin Visibility settings
        fStore = FirebaseFirestore.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fStore.collection("Users").document(fUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                if (documentSnapshot.getString("isUser") != null) {
                    editButton.setVisibility(View.GONE);
                }
                else{
                    editButton.setVisibility(View.VISIBLE);
                }
            }
        });

        monS = (TextView) view.findViewById(R.id.monStart);
        monE = (TextView) view.findViewById(R.id.monEnd);
        tueS = (TextView) view.findViewById(R.id.tueStart);
        tueE = (TextView) view.findViewById(R.id.tueEnd);
        wedS = (TextView) view.findViewById(R.id.wedStart);
        wedE = (TextView) view.findViewById(R.id.wedEnd);
        thurS = (TextView) view.findViewById(R.id.thurStart);
        thurE = (TextView) view.findViewById(R.id.thurEnd);
        friS = (TextView) view.findViewById(R.id.friStart);
        friE = (TextView) view.findViewById(R.id.friEnd);
        satS = (TextView) view.findViewById(R.id.satStart);
        satE = (TextView) view.findViewById(R.id.satEnd);
        sunS = (TextView) view.findViewById(R.id.sunStart);
        sunE = (TextView) view.findViewById(R.id.sunEnd);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("Schedule_Start");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mS = (String) dataSnapshot.child("Monday").getValue();
                monS.setText(mS);
                String tS = (String) dataSnapshot.child("Tuesday").getValue();
                tueS.setText(tS);
                String wS = (String) dataSnapshot.child("Wednesday").getValue();
                wedS.setText(wS);
                String thS = (String) dataSnapshot.child("Thursday").getValue();
                thurS.setText(thS);
                String fS = (String) dataSnapshot.child("Friday").getValue();
                friS.setText(fS);
                String sS = (String) dataSnapshot.child("Saturday").getValue();
                satS.setText(sS);
                String suS = (String) dataSnapshot.child("Sunday").getValue();
                sunS.setText(suS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference myRef1 = database.getReference();
        myRef1 = myRef1.child("Schedule_End");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mE = (String) dataSnapshot.child("Monday").getValue();
                monE.setText(mE);
                String tE = (String) dataSnapshot.child("Tuesday").getValue();
                tueE.setText(tE);
                String wE = (String) dataSnapshot.child("Wednesday").getValue();
                wedE.setText(wE);
                String thE = (String) dataSnapshot.child("Thursday").getValue();
                thurE.setText(thE);
                String fE = (String) dataSnapshot.child("Friday").getValue();
                friE.setText(fE);
                String sE = (String) dataSnapshot.child("Saturday").getValue();
                satE.setText(sE);
                String suE = (String) dataSnapshot.child("Sunday").getValue();
                sunE.setText(suE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the edit schedule activity
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
