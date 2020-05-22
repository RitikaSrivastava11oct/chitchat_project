package com.example.android.chitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class login extends AppCompatActivity {

    private Button but_login,but_logout;
    private TextView textview_signup;
    public EditText edit_email,edit_pwd;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        but_login = (Button) findViewById(R.id.but_login);
        textview_signup = (TextView) findViewById(R.id.but_sign_up);
        /*but_logout=(Button)findViewById(R.id.but_logout);*/
        edit_email=(EditText)findViewById(R.id.edit_email_login);
        edit_pwd=(EditText)findViewById(R.id.edit_password_login);

        textview_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signUp.class);
                startActivity(intent);
            }
        });

        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,password;
                email=edit_email.getText().toString();
                password=edit_pwd.getText().toString();
                log(email,password);

            }
        });
}


    private  void log(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                      //  Toast.makeText(getApplicationContext(),"signInWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


                        if (!task.isSuccessful()) {

                           Toast.makeText(getApplicationContext(),"Sign In failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent i=new Intent(login.this,ChitChat.class);
                            startActivity(i);
                            finish();
                        }


                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthException) {
                   Log.e("sssssss",((FirebaseAuthException) e).getErrorCode()) ;
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


