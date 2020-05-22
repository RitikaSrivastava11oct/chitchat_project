package com.example.android.chitchat;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.chitchat.Adapter.CustomListAdapter3;
import com.example.android.chitchat.Model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class Status extends Fragment {


    ListView l1;
    private DatabaseReference dref;

    ListView listView;
    user usr;
    ArrayList<user> arrayList = new ArrayList<>();
    DatabaseReference dr;
    String uid;
    FirebaseAuth f;
    ArrayList<String> users=new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_status, container, false);


        listView = (ListView) rootView.findViewById(R.id.listview_status);
        dref = FirebaseDatabase.getInstance().getReference().child("users");
        dr=FirebaseDatabase.getInstance().getReference().child("chat");
        FirebaseUser uf = FirebaseAuth.getInstance().getCurrentUser();
        if(uf==null)
        {
            Intent i=new Intent(getContext(),login.class);
            startActivity(i);
        }
        else
        {
            uid=uf.getUid();
        }
        // dr.keepSynced(true);
//        dr.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               // Log.d("data", "onDataChange: "+dataSnapshot.toString());
//                users.clear();
//                for(DataSnapshot d:dataSnapshot.getChildren()) {
//                  //  Toast.makeText(getContext(), d.toString(), Toast.LENGTH_LONG).show();
//                    users.add(d.getKey().toString());
//                }
//                populate(users);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Iterator i=user.iterator();
//        while (i.hasNext()) {
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    String fname =c.child("fname").getValue().toString();
                    String lname = c.child("lname").getValue().toString();
                    String uname = c.child("uname").getValue().toString();
                    String status = c.child("status").getValue().toString();
                    String imageUrl=c.child("image").getValue().toString();
                    String userId=c.child("userId").getValue().toString();
                    String key = c.getKey().toString();
                    usr = new user(fname, lname, status, uname, key,imageUrl,userId);
                    arrayList.add(usr);
                }
                CustomListAdapter3 customListAdapter = new CustomListAdapter3(getContext(), arrayList);
                listView.setAdapter(customListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;

    }

    private void populate(ArrayList<String> user) {
        Iterator i=user.iterator();
        while (i.hasNext()) {
            dref.keepSynced(true);
            dref.child(i.next().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // for (DataSnapshot c : dataSnapshot.getChildren()) {
                    String fname = dataSnapshot.child("fname").getValue().toString();
                    String lname = dataSnapshot.child("lname").getValue().toString();
                    String uname = dataSnapshot.child("uname").getValue().toString();
                    String status = dataSnapshot.child("status").getValue().toString();
                    String userId=dataSnapshot.child("userId").getValue().toString();
                    String key = dataSnapshot.getKey().toString();
                    String imageUrl=dataSnapshot.child("image").getValue().toString();
                    usr = new user(fname, lname, status, uname, key,imageUrl,userId);
                    arrayList.add(usr);
                    // }
                    CustomListAdapter3 customListAdapter = new CustomListAdapter3(getContext(), arrayList);
                    listView.setAdapter(customListAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}
    }
}

