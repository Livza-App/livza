package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__user);

        //get the user_information from th SignUp layout
        Bundle bundle= getIntent().getExtras();
        User User=new User();

        //User_Name
        User.setUser_name(bundle.get("user_name").toString());
        TextView User_name=findViewById(R.id.user_name);
        User_name.setText(User.getUser_name());

        //Phone_Number
        User.setPhone_number(bundle.get("phone_number").toString());
        TextView Phone_number=findViewById(R.id.phone_number);
        Phone_number.setText(User.getPhone_number());

        //Profile_img
        /*int id_profile_img=findViewById(R.id.profile_img);
        User.setProfile_img(id_profile);*/


    }
}