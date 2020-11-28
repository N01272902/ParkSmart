package com.smarttech.parksmart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DirectionActivity extends Fragment{

    private static final String TAG = "MainActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_direction,container, false);

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


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_GPS));
                intent.setPackage("com.google.android.apps.maps");
                //Intent chooser = Intent.createChooser(intent,"Launch Maps");

                if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );
            }
        });

        return view;
    }
}
