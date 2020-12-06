package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Log_In extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        // LogIn
        ImageView Logbtn=findViewById(R.id.LoginBtn);
        EditText Ph=findViewById(R.id.PhoneNumber);
        String Phone_Number=Ph.getText().toString();

        if (Phone_Number!=null){
            Logbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog();
                    //Ghanou hna trfed PhoneNumber tba3to l fire base
                }
            });
        }else{
            Toast toast = Toast.makeText(Log_In.this, "Enter your phone number !", Toast.LENGTH_SHORT);
            toast.show();
        }


        //To move to the signe_up layout and  Create a new account
        TextView Sign_up=findViewById(R.id.Signup);
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass=new Intent(Log_In.this,Sign_Up.class);
                startActivity(pass);
            }
        });
    }

    //This function to show the Dialog of the Confirmation Code to
    public void ShowDialog(){
        Dialog DialOg=new Dialog(this);
        DialOg.setContentView(R.layout.custom_confirmation_code_dialog);
        DialOg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DialOg.show();

        //To get the code from the dialog Text and compar it with the code sende by the fierbase manager
        EditText CodeEdit=DialOg.findViewById(R.id.confimation_code);
        TextView Confirme=DialOg.findViewById(R.id.Confirme);
        Confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Code=CodeEdit.getText().toString();
                //GHanou hna u took this Code varible and you Compare with the Code that we have send it to the user -____*____-

            }
        });

        //To resend the COnfirmation Code
        TextView Resend=DialOg.findViewById(R.id.cancel);
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialOg.hide();
                //"Here to send Reqest tho the firebase manger and resend the code
            }
        });
    }




}