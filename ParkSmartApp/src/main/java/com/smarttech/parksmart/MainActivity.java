package com.smarttech.parksmart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets up the splash screen
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton availabilityButton = findViewById(R.id.availability);
        availabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the availability activity
                Intent intent = new Intent(MainActivity.this, AvailabilityActivity.class);
                startActivity(intent);
            }
        });

        ImageButton scheduleButton= findViewById(R.id.schedule);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the schedule View activity
                Intent intent = new Intent(MainActivity.this, ScheduleViewActivity.class);
                startActivity(intent);
            }
        });

        ImageButton gateButton= findViewById(R.id.gatecontrol);
        gateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the gatecontrol activity
                Intent intent = new Intent(MainActivity.this, GateControlActivity.class);
                startActivity(intent);
            }
        });

        ImageButton directionButton= findViewById(R.id.direction);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the direction activity
                Intent intent = new Intent(MainActivity.this, DirectionActivity.class);
                startActivity(intent);
            }
        });
/*
//Database connection setup
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();
        //Getting reference to "child 1" node
        myRef = myRef.child("Coordinates");
        //myRef.setValue("Vijay Matadeen");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String x = dataSnapshot.child("x").getValue().toString();
                String y = dataSnapshot.child("y").getValue().toString();


                String beginning = "google.navigation:q=";
                String comma = ",";
                String end = "&mode=d";

                final String URI_GPS = beginning + x + comma + y + end;


                launch_Button = findViewById(R.id.direction);

                launch_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(URI_GPS));

                        intent.setPackage("com.google.android.apps.maps");
                        //Intent chooser = Intent.createChooser(intent,"Launch Maps");

                        if(intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );
            }
        });

 */
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

}