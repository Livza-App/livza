package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Frame_2 extends AppCompatActivity {

    Animation upIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_2);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));
        skip();
        Button log_in=findViewById(R.id.login_btn);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_in.setAnimation(upIn);
                Intent Log_In=new Intent(Frame_2.this,Log_In.class);
                startActivity(Log_In);
            }
        });



        TextView Sign_Up=findViewById(R.id.sign_up);
        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Sign_up=new Intent(Frame_2.this,Sign_Up.class);
                startActivity(Sign_up);
            }
        });


        //Animation
        upIn= AnimationUtils.loadAnimation(this,R.anim.anim_push_up_in);
        ImageView logo=findViewById(R.id.logo);logo.setAnimation(upIn);
        TextView txt=findViewById(R.id.textView7); txt.setAnimation(upIn);
        log_in.setAnimation(upIn);
        Sign_Up.setAnimation(upIn);




    }

    private void skip(){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(Frame_2.this,Menu.class);
            startActivity(intent);
            finish();
        }
    }
}