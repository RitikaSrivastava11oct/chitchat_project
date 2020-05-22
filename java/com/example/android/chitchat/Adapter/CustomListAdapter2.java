package com.example.android.chitchat.Adapter;

/**
 * Created by hp on 17-07-2018.
 */




import android.app.Activity;
        import android.content.Context;
        import android.graphics.Color;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
        import android.widget.TextView;

import com.example.android.chitchat.Model.messages;
import com.example.android.chitchat.R;

import java.util.ArrayList;


/**
 * Created by prachijn on 17-07-2018.
 */

public class CustomListAdapter2 extends ArrayAdapter{


    Context context2;
    ArrayList arrayList2;
    String uid;
    public CustomListAdapter2(Context context2, ArrayList arrayList2,String ui){
        super(context2,android.R.layout.simple_list_item_1,arrayList2);
        this.context2=context2;
        this.arrayList2=arrayList2;
        uid=ui;
       // String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public class ViewHolder{
        TextView textview_chat_msg;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        CustomListAdapter2.ViewHolder holder;
        messages postModel2 = (messages) arrayList2.get(position);
        if (rowView == null) {
            LayoutInflater layoutInflater = ((Activity) context2).getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.list_item_2, parent, false);
            holder = new CustomListAdapter2.ViewHolder();
            holder.textview_chat_msg = (TextView) rowView.findViewById(R.id.textview_msg_while_chatting);
            if(uid.equals(postModel2.getSender())){

                RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)holder.textview_chat_msg.getLayoutParams();
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.textview_chat_msg.setBackgroundColor(Color.GRAY);

                holder.textview_chat_msg.setX((holder.textview_chat_msg.getX())+30);
            }
            else {
                Log.d("uids", "getView: "+uid+"   - "+postModel2.getSender());
                holder.textview_chat_msg.setBackgroundColor(Color.BLUE);

            }



            rowView.setTag(holder);
        } else {
            holder = (CustomListAdapter2.ViewHolder) rowView.getTag();
        }
       // PostModel2 postModel2 = (PostModel2) arrayList2.get(position);
       // messages postModel2 = (messages) arrayList2.get(position);
        holder.textview_chat_msg.setText(postModel2.getMessage().toString());

        return rowView;
    }
}