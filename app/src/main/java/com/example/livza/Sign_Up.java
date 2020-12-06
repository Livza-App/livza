package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_Up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        EditText User_Name=findViewById(R.id.UserName);
        EditText Phone_Number=findViewById(R.id.PhoneNumber);
        ImageView SignUpBtn=findViewById(R.id.SigneUpBtn);

        //SignUp
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=getBaseContext();
                if ((User_Name.toString()).isEmpty()){
                    Toast EmptyUserName= Toast.makeText(context,"please enter your Name",Toast.LENGTH_SHORT);
                    EmptyUserName.show();
                }else if ((Phone_Number.toString()).isEmpty()){
                    Toast EmptyPhoneNumber= Toast.makeText(context,"please enter your Phone Number",Toast.LENGTH_SHORT);
                    EmptyPhoneNumber.show();
                }else {
                    Intent user=new Intent(Sign_Up.this,Profile_User.class);
                    user.putExtra("user_name",User_Name.toString());
                    user.putExtra("phone_number",Phone_Number.toString());

                    //GHanou hna tedi user name w phone number l firebasee

                }
            }
        });


        //logIn Btnn
        TextView Login=findViewById(R.id.LoginTxt);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass=new Intent(Sign_Up.this,Log_In.class);
                startActivity(pass);
            }
        });


    }
}