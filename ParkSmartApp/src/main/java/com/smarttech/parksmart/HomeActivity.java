package com.smarttech.parksmart;

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

public class HomeActivity extends Fragment {

    TextView Announcement;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        Announcement = (TextView) view.findViewById(R.id.annoucement);

        //DataBase Connection
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("Announcement");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lang = getString(R.string.language);
                String appAn = (String) dataSnapshot.child(lang).getValue();
                Announcement.setText(appAn);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}