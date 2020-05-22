package com.example.android.chitchat.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by hp on 18-07-2018.
 */
@IgnoreExtraProperties
public class messages {
    String message;
    String sender;
    String time;
    String type;

    public messages() {

    }

    public messages(String message, String sender, String time, String type) {
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
