package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
     Animation leftAnim,rightAnim,outleft,outright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));
        //lets Go man

        //Animation
        leftAnim= AnimationUtils.loadAnimation(this,R.anim.anim_slaide_in_left);
        rightAnim=AnimationUtils.loadAnimation(this,R.anim.anim_slide_in_right);
        outleft=AnimationUtils.loadAnimation(this,R.anim.anim_slide_out_left);
        outright=AnimationUtils.loadAnimation(this,R.anim.anim_slide_out_right);

        ImageView logo=findViewById(R.id.moto);
        logo.setAnimation(rightAnim);

        TextView Livza=findViewById(R.id.textLivza);
        Livza.setAnimation(leftAnim);


        mHandler.postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent(MainActivity.this, Frame_2.class);
                startActivity(intent);

            }
        }, 2000);// 2 seconds

    }


}