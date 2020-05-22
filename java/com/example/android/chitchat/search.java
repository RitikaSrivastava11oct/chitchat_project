package com.example.android.chitchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.chitchat.Adapter.CustomListAdapter;
import com.example.android.chitchat.Model.user;
import com.google.android.gms.stats.internal.G;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class search extends AppCompatActivity {
    DatabaseReference data;
    FirebaseAuth fauth;
    EditText e1;
    ImageView b1;
    ListView l1;

    user usr;
    ArrayList<user> arrayList = new ArrayList<>();
    String h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        e1=(EditText)findViewById(R.id.e1_at_serch);
        b1=(ImageView)findViewById(R.id.b2_at_serch);
        l1=(ListView)findViewById(R.id.lenir_at_serch);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h=e1.getText().toString();
                arrayList.clear();
                load();
            }


        });

    }
    private void load()
    {    if(h!=null&&h!=" ")
    {

        data = FirebaseDatabase.getInstance().getReference().child("users");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    String fname = c.child("fname").getValue().toString();
                    String lname = c.child("lname").getValue().toString();
                    String uname = c.child("uname").getValue().toString();
                    String status = c.child("status").getValue().toString();
                    String imageUrl = c.child("image").getValue().toString();
                    String userId=c.child("userId").getValue().toString();
                    String key = c.getKey().toString();
                    Pattern p= Pattern.compile("\\w*"+h+"\\w*");
                    Matcher m=p.matcher(fname);
                    if(m.find()) {
                        usr = new user(fname, lname, status, uname, key,imageUrl,userId);
                        arrayList.add(usr);
                    }
                }
                CustomListAdapter customListAdapter = new CustomListAdapter(search.this, arrayList);
                l1.setAdapter(customListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}
}
