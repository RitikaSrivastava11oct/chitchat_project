package com.example.android.chitchat.Adapter;

/**
 * Created by hp on 16-07-2018.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.android.chitchat.Chat_Adapter;
import com.example.android.chitchat.Model.user;
import com.example.android.chitchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Komal on 20-07-2017.
 */

public class CustomListAdapter extends ArrayAdapter {
    Context context;
    ArrayList arrayList;
    String uid;
    URL newurl;
    String imageUrl;
    private FirebaseStorage firestor;
    private StorageReference fs;
    StorageReference sf;
    static int i = 1;
    Bitmap mIcon_val;
    ViewHolder holder;

    //user u;
    //View rowView;
    public CustomListAdapter(Context context, ArrayList arrayList) {
        super(context, android.R.layout.simple_list_item_1, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }




    public class ViewHolder {
        TextView textview_name;
        CircleImageView circleImageView;
        LinearLayout linearLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.textview_name = (TextView) rowView.findViewById(R.id.textview_name);
            holder.circleImageView = (CircleImageView) rowView.findViewById(R.id.circle_image_dp_chatlist);
            holder.linearLayout = (LinearLayout) rowView.findViewById(R.id.user_laout);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        user u = (user) arrayList.get(position);
        holder.textview_name.setText(u.getFname().toString());
        fs = FirebaseStorage.getInstance().getReference();
        sf = fs.child("profile").child(u.getUserId());

       sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUrl) {
                    Glide.with(getContext()).load(downloadUrl).into(holder.circleImageView);
                }
            });

        final user us = u;
        final DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("friends");
        //.child(u.getKey());
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                d.child(uid).child(us.getKey()).setValue("friend").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(getContext(), "done", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Intent i = new Intent(getContext(), Chat_Adapter.class);
                i.putExtra("h", us);
                context.startActivity(i);
            }
        });


        return rowView;
    }

}
