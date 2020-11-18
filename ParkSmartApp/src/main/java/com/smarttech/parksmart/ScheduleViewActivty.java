package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ScheduleViewActivty extends AppCompatActivity {

    TextView monS, monE;
    TextView tueS, tueE;
    TextView wedS, wedE;
    TextView thurS, thurE;
    TextView friS, friE;
    TextView satS, satE;
    TextView sunS, sunE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view_activty);

        monS=(TextView)findViewById(R.id.monStart);
        monE=(TextView)findViewById(R.id.monEnd);
        tueS=(TextView)findViewById(R.id.tueStart);
        tueE=(TextView)findViewById(R.id.tueEnd);
        wedS=(TextView)findViewById(R.id.wedStart);
        wedE=(TextView)findViewById(R.id.wedEnd);
        thurS=(TextView)findViewById(R.id.thurStart);
        thurE=(TextView)findViewById(R.id.thurEnd);
        friS=(TextView)findViewById(R.id.friStart);
        friE=(TextView)findViewById(R.id.friEnd);
        satS=(TextView)findViewById(R.id.satStart);
        satE=(TextView)findViewById(R.id.satEnd);
        sunS=(TextView)findViewById(R.id.sunStart);
        sunE=(TextView)findViewById(R.id.sunEnd);


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



        Button editButton= findViewById(R.id.editSchedule);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the activity_availability activity
                Intent intent = new Intent(ScheduleViewActivty.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
}