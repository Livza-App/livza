package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Log_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        // LogIn
        ImageView Logbtn=findViewById(R.id.SigneUpBtn);
        EditText Phone_Number=findViewById(R.id.PhoneNumber);
        if (Phone_Number.getText()!=null){
            /*Logbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
        }else {
            Toast toast = Toast.makeText(this, "Enter your phone number !", Toast.LENGTH_SHORT);
            toast.show();
        }

        //To Create a new account
        TextView Sign_up=findViewById(R.id.Signup);
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass=new Intent(Log_In.this,Sign_Up.class);
                startActivity(pass);
            }
        });
    }
}