package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile_User extends AppCompatActivity  {

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
        TextView Phone_number=findViewById(R.id.num);
        Phone_number.setText(User.getPhone_number());

        //Profile_img
        ImageView id_profile_img=findViewById(R.id.profile_img);
        //User.setProfile_img(id_profile); GHanou hna tediy profile_img


        //Edit the profile
        Button Edit=findViewById(R.id.Edit_profile);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_profile_Dialog();
            }
        });

        //Back btn
        Button back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b=new Intent(Profile_User.this,Menu.class);
                startActivity(b);
            }
        });

    }

    //this function run when the user want to edit the Profile_Ditails
    public void Edit_profile_Dialog(){
        Dialog Edit_Profile_Dialog=new Dialog(this);
        Edit_Profile_Dialog.setContentView(R.layout.custom_edit_profile_dialog);
        Edit_Profile_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Edit_Profile_Dialog.show();

        //set The User Name **Ghanou hna tedi user name t3awd dirlo update f user firebase table**
        EditText user_name_edit=Edit_Profile_Dialog.findViewById(R.id.PersonName);
        TextView User_name=findViewById(R.id.user_name);
        User_name.setText(user_name_edit.getText());

        //set the Phone number **Ghanou hna tedi Phone num t3awd dirlo update f user firebase table**
        EditText phone_number_edit=findViewById(R.id.editTextNumber);
        TextView Phone_number=findViewById(R.id.num);
        Phone_number.setText(phone_number_edit.getText());

        //set Profile _img **Ghanou hna tedi Profile _img t3awd dirlo update f user firebase table**
        ImageView profile_img=findViewById(R.id.profile_img2);
        ImageView profile_im=findViewById(R.id.profile_img);
        profile_im.setImageDrawable(profile_img.getDrawable());

    }
}