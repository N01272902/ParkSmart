package com.smarttech.parksmart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleActivity extends AppCompatActivity {
    Spinner spinnerDay;
    Spinner spinnerAM;
    Spinner spinnerPM;
    Button button;
    String timeM;
    String timeE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        spinnerDay = (Spinner) findViewById(R.id.Weekday);
        spinnerAM = (Spinner) findViewById(R.id.AM_Time);
        spinnerPM = (Spinner) findViewById(R.id.PM_Time);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDay = spinnerDay.getSelectedItem().toString();
                String selectStart = spinnerAM.getSelectedItem().toString();
                String selectEnd = spinnerPM.getSelectedItem().toString();
                Toast.makeText(ScheduleActivity.this, selectDay + " " + selectStart + " " + selectEnd, Toast.LENGTH_SHORT).show();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                //Getting Reference to Root Node
                DatabaseReference myRef = database.getReference();
                DatabaseReference myRefEnd = database.getReference();
                myRef = myRef.child("Schedule_Start/" + selectDay);
                myRef.setValue(selectStart);
                myRefEnd = myRefEnd.child("Schedule_End/" + selectDay);
                myRefEnd.setValue(selectEnd);

                DatabaseReference myRefN = database.getReference();
                DatabaseReference myRefNEnd = database.getReference();

                //Getting reference to "child 1" node
                myRefN = myRefN.child("Schedule_Start_Num/" + selectDay);
                switch (selectStart) {
                    case "12:00 AM":
                        timeM = "0";
                        break;
                    case "1:00 AM":
                        timeM = "1";
                        break;
                    case "2:00 AM":
                        timeM = "2";
                        break;
                    case "3:00 AM":
                        timeM = "3";
                        break;
                    case "4:00 AM":
                        timeM = "4";
                        break;
                    case "5:00 AM":
                        timeM = "5";
                        break;
                    case "6:00 AM":
                        timeM = "6";
                        break;
                    case "7:00 AM":
                        timeM = "7";
                        break;
                    case "8:00 AM":
                        timeM = "8";
                        break;
                    case "9:00 AM":
                        timeM = "9";
                        break;
                    case "10:00 AM":
                        timeM = "10";
                        break;
                    case "11:00 AM":
                        timeM = "11";
                        break;
                }
                myRefN.setValue(timeM);

                myRefNEnd = myRefNEnd.child("Schedule_End_Num/" + selectDay);
                switch (selectEnd) {
                    case "12:00 PM":
                        timeE = "12";
                        break;
                    case "1:00 PM":
                        timeE = "13";
                        break;
                    case "2:00 PM":
                        timeE = "14";
                        break;
                    case "3:00 PM":
                        timeE = "15";
                        break;
                    case "4:00 PM":
                        timeE = "16";
                        break;
                    case "5:00 PM":
                        timeE = "17";
                        break;
                    case "6:00 PM":
                        timeE = "18";
                        break;
                    case "7:00 PM":
                        timeE = "19";
                        break;
                    case "8:00 PM":
                        timeE = "20";
                        break;
                    case "9:00 PM":
                        timeE = "21";
                        break;
                    case "10:00 PM":
                        timeE = "22";
                        break;
                    case "11:00 PM":
                        timeE = "23";
                        break;
                }
                myRefNEnd.setValue(timeE);
            }
        });
    }
}