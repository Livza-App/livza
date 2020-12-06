package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lets Go man
        mAuth=FirebaseAuth.getInstance();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if(mAuth==null){
                    Intent intent = new Intent(MainActivity.this, Log_In.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, Log_In.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000); // 2 seconds

    }
}