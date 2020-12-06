package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Frame_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_2);

        Button log_in=findViewById(R.id.login_btn);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Log_In=new Intent(Frame_2.this,Log_In.class);
                startActivity(Log_In);
            }
        });


        TextView Sign_Up=findViewById(R.id.sign_up);
        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Sign_up=new Intent(Frame_2.this,Sign_Up.class);
            }
        });

    }
}