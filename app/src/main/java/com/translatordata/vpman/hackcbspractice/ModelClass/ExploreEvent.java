package com.translatordata.vpman.hackcbspractice.ModelClass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.translatordata.vpman.hackcbspractice.Adapter;
import com.translatordata.vpman.hackcbspractice.R;

import java.util.ArrayList;
import java.util.List;

public class ExploreEvent extends AppCompatActivity {

    RecyclerView recyclerView;
    List<UpvoteData> list;
    Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_event);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        list=new ArrayList<>();

        final RecyclerView recyclerView=findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(ExploreEvent.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UpvoteData upvoteData=new UpvoteData();
                String date=dataSnapshot.child("Event Detail").child("date").getValue(String.class);
                String time=dataSnapshot.child("Event Detail").child("time").getValue(String.class);
                String eventName=dataSnapshot.child("Event Detail").child("eventName").getValue(String.class);
                String eventAddress=dataSnapshot.child("Event Detail").child("eventAddress").getValue(String.class);

                upvoteData.setDate(date);
                upvoteData.setEventAddress(eventAddress);
                upvoteData.setEventName(eventName);
                upvoteData.setTime(time);
                list.add(upvoteData);
                adapter=new Adapter(list,ExploreEvent.this);
                recyclerView.setAdapter(adapter);
                //  Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
                /*TextView date1=view.findViewById(R.id.date);
                date1.setText(date);
                TextView time1=view.findViewById(R.id.time);
                time1.setText(time);
                TextView Event=view.findViewById(R.id.eventn);
                Event.setText(eventName);
                TextView Address1=view.findViewById(R.id.address);
                Address1.setText(eventAddress);*/
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
