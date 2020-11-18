package com.smarttech.parksmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AvailabilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

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
                    Intent intent4 = new Intent(getApplicationContext(), ScheduleActivity.class);
                    startActivity(intent4);
                    break;
                default:
            }
            return false;
        }
    };

}