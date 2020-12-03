package com.example.livza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Log_In extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        // LogIn
        ImageView Logbtn=findViewById(R.id.LoginBtn);
        EditText Ph=findViewById(R.id.PhoneNumber);
        String Phone_Number=Ph.getText().toString();

        Logbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Phone_Number==null){
                        Toast toast = Toast.makeText(Log_In.this, "Enter your phone number !", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        //Ghanou hna trfed PhoneNumber tba3to l fire base
                        ShowDialog();
                    }
                }
            });


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
        AlertDialog.Builder Alert;
        Alert=new AlertDialog.Builder(this);

        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.custom_confirmation_code_dialog,null);

        Alert.setView(view);
        Alert.setCancelable(false);

        //To get the code from the dialog Text and compar it with the code sende by the fierbase manager
        EditText CodeEdit=view.findViewById(R.id.confimation_code);
        TextView Confirme=view.findViewById(R.id.Confirme);
        Confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Code=CodeEdit.getText().toString();
                //GHanou hna u took this Code varible and you Compare with the Code that we have send it to the user -____*____-

            }
        });

        AlertDialog dialog=Alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        //To resend the COnfirmation Code
        TextView Resend=view.findViewById(R.id.Resend);
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }




}