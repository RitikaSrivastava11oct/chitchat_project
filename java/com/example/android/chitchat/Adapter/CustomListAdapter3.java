package com.example.android.chitchat.Adapter;


import android.app.Activity;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.android.chitchat.Model.user;
import com.example.android.chitchat.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomListAdapter3 extends ArrayAdapter {
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
    public CustomListAdapter3(Context context, ArrayList arrayList) {
        super(context, android.R.layout.simple_list_item_1, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    public class ViewHolder {
        TextView textview_name;
        TextView textView_status;
        CircleImageView circleImageView;
        LinearLayout linearLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.list_item3, parent, false);
            holder = new ViewHolder();
            holder.textview_name = (TextView) rowView.findViewById(R.id.textview_name_status);
            holder.textView_status=(TextView)rowView.findViewById(R.id.textview_content_status);
            holder.circleImageView = (CircleImageView) rowView.findViewById(R.id.circle_image_status);
           // holder.linearLayout = (LinearLayout) rowView.findViewById(R.id.user_laout);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        //PostModel postModel=(PostModel)arrayList.get(position);
        //messages postModel=(messages) arrayList.get(position);
        user u = (user) arrayList.get(position);
        holder.textview_name.setText(u.getFname().toString());
        holder.textView_status.setText(u.getStatus().toString());
        fs = FirebaseStorage.getInstance().getReference();
        sf = fs.child("profile").child(u.getUserId());

      /*  if(i%2==0) {
            Glide.with(context).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZ2E03V00lvH9UC8LL2oPkCI6PgAt0Y94bJXpnzhB_NCkjpiKH").into(holder.circleImageView);
            i++;
        }
        else {
            Glide.with(getContext()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRACVO6G7Gs5S-E4PbcqmQW8Gx30nHYXMigekwEagZzY8pooqk").into(holder.circleImageView);
            i++;
        }*/
        sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                //do something with downloadurl
                Glide.with(getContext()).load(downloadUrl).into(holder.circleImageView);
            }
        });

        final user us = u;
        final DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("frinds");
        //.child(u.getKey());
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                d.child(uid).child(us.getKey()).setValue("friend").addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                    }
//                });
//                Intent i = new Intent(getContext(), Chat_Adapter.class);
//                i.putExtra("h", us);
//                context.startActivity(i);
//            }
//        });


        return rowView;
    }
}
