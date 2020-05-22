package com.example.android.chitchat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chitchat.Model.messages;
import com.example.android.chitchat.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hp on 24-07-2018.
 */

public class Chat_Adapter extends AppCompatActivity {
    EditText e1;
    RecyclerView rv;
    ImageView i1;
    ArrayList<messages> arrayList2 = new ArrayList<messages>();
    private FirebaseAuth fa;
    private DatabaseReference drf;
    user u;
    String uid;
    Chat ob;
    HashMap<String, String> hm = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch_recycle);

       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        e1=(EditText)findViewById(R.id.chat_rytext);
        i1=(ImageView) findViewById(R.id.chat_rysend);
        rv=(RecyclerView)findViewById(R.id.res_st_ryserch);
        u = (user) getIntent().getSerializableExtra("h");
        getSupportActionBar().setTitle(u.getFname());
        fa = FirebaseAuth.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        drf = FirebaseDatabase.getInstance().getReference().child("chat");
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = e1.getText().toString();
                e1.setText("");
                sendmess(s);
            }
        });
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //String st[]={"hi","there ","how","are you"};
         ob=new Chat(arrayList2,uid,u);
        rv.setAdapter(ob);
        drf.child(uid).child(u.getKey()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String a = dataSnapshot.getValue().toString();
                String mas[] = a.split(",");
                if (mas.length > 0) {
                    for (int i = 0; i < mas.length; i++) {
                        int f = mas[i].indexOf("=");
                        if (i != 3) {
                            mas[i] = mas[i].substring(f + 1);
                        } else {
                            mas[i] = mas[i].substring(f + 1, (mas[i].length()) - 1);
                        }
                        //postmodel1=new PostModel2(mas[1]);
                    }
                    messages m = new messages(mas[1], mas[3], mas[0], mas[2]);
                    // m.setMessage(mas[1]);
                    arrayList2.add(m);
                }
                ob.notifyDataSetChanged();
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
    private void sendmess(String s) {


        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmm");
        String currentDateTime= simpleDateFormat.format(new Date());
        currentDateTime=currentDateTime.substring(0,2)+":"+currentDateTime.substring(2,4);
        hm.put("time", currentDateTime);
        hm.put("type", "text");
        hm.put("massage", s);
        hm.put("sender", uid);
        drf.child(uid).child(u.getKey()).push().setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    drf.child(u.getKey()).child(uid).push().setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Toast.makeText(getApplicationContext(), "message send", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

}
class Chat extends RecyclerView.Adapter<Chat.iner>{
List<messages> l;
String uid;
    user u;
    public Chat(List l, String uid, user u)
    {
        this.l=l;
        this.uid=uid;
        this.u=u;
    }


    @Override
    public iner onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf=LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.received_message,parent,false);
        return new iner(v);
    }

    @Override
    public void onBindViewHolder(iner holder, int position) {
       messages a=  l.get(position);
        String ma;
        if(uid.equals(a.getSender())){

            //  RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)holder..getLayoutParams();
            //  lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
           // holder.t1.setBackgroundColor(Color.GRAY);
            holder.t2.setText("You");
            holder.t2.setTextColor(Color.BLUE);
        }
        else {
            Log.d("uids", "getView: "+uid+"   - "+a.getSender());
            String s=u.getFname();
            holder.t2.setTextColor(Color.GRAY);
            holder.t2.setText(s.substring(0,1).toUpperCase()+s.substring(1,s.length()).toLowerCase());
        }
        holder.t1.setText(a.getMessage()+"  ");
        holder.t3.setText(a.getTime());

    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    class iner extends RecyclerView.ViewHolder{
        TextView t1,t2,t3;
        public iner(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.text_message_body);
            t2=(TextView)itemView.findViewById(R.id.text_message_head);
            t3=(TextView)itemView.findViewById(R.id.text_message_time);
        }
    }
}
