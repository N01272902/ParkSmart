package com.smarttech.parksmart;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AvailabilityActivity extends Fragment {

    TextView Lot1;
    TextView Lot2;
    TextView Lot3;
    TextView Lot1_status;
    TextView Lot2_status;
    TextView Lot3_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_availability, container, false);

        Lot1 = (TextView) view.findViewById(R.id.Lot_1);
        Lot2 = (TextView) view.findViewById(R.id.Lot_2);
        Lot3 = (TextView) view.findViewById(R.id.Lot_3);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("Parking_Spot");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Lot1_status = (String) dataSnapshot.child("Lot_1").getValue();
                String Lot2_status = (String) dataSnapshot.child("Lot_2").getValue();
                String Lot3_status = (String) dataSnapshot.child("Lot_3").getValue();


                if (Lot1_status.equals("true")) {
                    GradientDrawable gradientDrawable = (GradientDrawable) Lot1.getBackground().mutate();
                    gradientDrawable.setColor(Color.RED);

                } else {
                    GradientDrawable gradientDrawable = (GradientDrawable) Lot1.getBackground().mutate();
                    gradientDrawable.setColor(Color.GREEN);
                }

                if (Lot2_status.equals("true")) {
                    GradientDrawable gradientDrawable = (GradientDrawable) Lot2.getBackground().mutate();
                    gradientDrawable.setColor(Color.RED);

                } else {
                    GradientDrawable gradientDrawable = (GradientDrawable) Lot2.getBackground().mutate();
                    gradientDrawable.setColor(Color.GREEN);
                }

                if (Lot3_status.equals("true")) {
                    GradientDrawable gradientDrawable = (GradientDrawable) Lot3.getBackground().mutate();
                    gradientDrawable.setColor(Color.RED);

                } else {
                    GradientDrawable gradientDrawable = (GradientDrawable) Lot3.getBackground().mutate();
                    gradientDrawable.setColor(Color.GREEN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}