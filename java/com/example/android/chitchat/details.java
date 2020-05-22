package com.example.android.chitchat;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class details extends AppCompatActivity {

    private DatabaseReference dref;
    TextView un,fn,ln,st;
    ImageView pencil,tick;
    EditText edittext;
    String text;
    private StorageReference fs;
    StorageReference sf;
    String newtext;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_new);

        un=(TextView)findViewById(R.id.text_uname_value);
        fn=(TextView)findViewById(R.id.text_fname_value);
        ln=(TextView)findViewById(R.id.text_lname_value);
        st=(TextView)findViewById(R.id.text_status_value);
        edittext=(EditText)findViewById(R.id.edittext_status);
        pencil=(ImageView)findViewById(R.id.image_pencil);
        tick=(ImageView)findViewById(R.id.image_tick_status);
        circleImageView=(CircleImageView)findViewById(R.id.profile_image_details);
        String s= FirebaseAuth.getInstance().getCurrentUser().getUid();
        dref= FirebaseDatabase.getInstance().getReference().child("users").child(s);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                String fname=dataSnapshot.child("fname").getValue().toString();
                String lname=dataSnapshot.child("lname").getValue().toString();
                String uname=dataSnapshot.child("uname").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String userId=dataSnapshot.child("userId").getValue().toString();
                un.setText(uname);
                fn.setText(fname);
                ln.setText(lname);
                st.setText(status);
                fs = FirebaseStorage.getInstance().getReference();
                sf = fs.child("profile").child(userId);

                sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        Glide.with(details.this).load(downloadUrl).into(circleImageView);
                    }
                });


                pencil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st.setVisibility(View.GONE);
                        edittext.setVisibility(View.VISIBLE);
                        edittext.setText(text);
                        pencil.setVisibility(View.GONE);
                        tick.setVisibility(View.VISIBLE);

                    }
                });
                tick.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        newtext=edittext.getText().toString();
                        edittext.setVisibility(View.GONE);
                        tick.setVisibility(View.GONE);
                        pencil.setVisibility(View.VISIBLE);
                        st.setVisibility(View.VISIBLE);
                        st.setText(newtext);
                        String userId=dataSnapshot.child("userId").getValue().toString();
                        dref=FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("status");
                        dref.setValue(newtext);
                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(details.this,ChitChat.class);
        startActivity(in);
        finish();
    }
}
