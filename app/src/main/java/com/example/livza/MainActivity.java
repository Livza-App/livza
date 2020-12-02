package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lets Go man


        mHandler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, Log_In.class);
                startActivity(intent);
            }
        }, 2000); // 2 seconds

    }
}