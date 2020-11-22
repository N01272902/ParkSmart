package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightActivity extends AppCompatActivity {

    TextView Light1;
    TextView Light2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        Light1=(TextView)findViewById(R.id.StatusLight1);
        Light2=(TextView)findViewById(R.id.StatusLight2);

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

        Button GateOpen1= findViewById(R.id.lightOn1);
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

        Button GateOpen2= findViewById(R.id.lightOn2);
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

        Button GateClose1= findViewById(R.id.lightOff1);
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

        Button GateClose2= findViewById(R.id.lightOff2);
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

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.navigation_availability:
                    Intent intent2 = new Intent(getApplicationContext(), AvailabilityActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.navigation_direction:
                    Intent intent3 = new Intent(getApplicationContext(), DirectionActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.navigation_schedule:
                    Intent intent4 = new Intent(getApplicationContext(), ScheduleViewActivity.class);
                    startActivity(intent4);
                    break;
                default:
            }
            return false;
        }
    };
}