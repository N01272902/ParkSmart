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

        ImageButton lightButton = findViewById(R.id.lightcontrol);
        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens the availability activity
                Intent intent = new Intent(MainActivity.this, LightActivity.class);
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
                //opens the gate control activity
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

        ImageButton signOutButton = findViewById(R.id.signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(R.string.DialogExitMsg);
        builder.setPositiveButton(R.string.DialogYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
                finish();
            }
        });
        builder.setNegativeButton(R.string.DialogNo,new DialogInterface.OnClickListener() {
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