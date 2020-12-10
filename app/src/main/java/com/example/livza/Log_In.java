package com.example.livza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Log_In extends AppCompatActivity  {
    private ImageView Logbtn;
    private EditText Ph;
    private TextView Sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);
        //init all objects
        initobjects();
        // LogIn
        login();



        //To move to the sign_up layout and  Create a new account

        signup();

        //testTest intent tesssstttt brk
        ImageView logo=findViewById(R.id.icon);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o=new Intent(Log_In.this,Menu.class);
                startActivity(o);

            }
        });

    }

    public void initobjects(){
        Logbtn=findViewById(R.id.LoginBtn);
        Ph=findViewById(R.id.PhoneNumber);
        Sign_up=findViewById(R.id.Signup);
    }

    private void signup() {
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass=new Intent(Log_In.this,Sign_Up.class);
                startActivity(pass);
            }
        });
    }

    public void login(){

        Logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Phone_Number=Ph.getText().toString();
                if (Phone_Number.isEmpty()){
                    Toast toast = Toast.makeText(Log_In.this, "Enter your phone number !", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    ShowDialog();
                }

                //Ghanou hna trfed PhoneNumber tba3to l fire base
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
                //String Code=CodeEdit.getText().toString();
                startActivity(new Intent(Log_In.this,Menu.class));
                //GHanou hna u took this Code varible and you Compare with the Code that we have send it to the user -____*____-

            }
        });

        //To resend the confirmation Code...
        //cancel dialog
        TextView Cancel=DialOg.findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialOg.dismiss();
            }
        });
    }

}