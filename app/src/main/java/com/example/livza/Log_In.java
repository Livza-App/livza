package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class Log_In extends AppCompatActivity  {


    //view
    private ImageView Logbtn;
    private EditText Phone_Number;
    private TextView Sign_up;
    private AlertDialog alertDialog;
    private String PhoneNumber;

    //Firebase
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private PhoneAuthCredential credential;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    //Animation
    private Animation fadeIn, leftAnim,rightAnim,outleft,outright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));

        init();

        if(mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(Log_In.this,Menu.class);
            startActivity(intent);
            finish();
        }
        //Sign_up btn
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass=new Intent(Log_In.this,Sign_Up.class);
                startActivity(pass);
            }
        });

        //log_in btn
        Logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_the_data();
            }
        });

        //just fooor testttttt
        TextView p=findViewById(R.id.textView4);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Log_In.this,Carte_order.class);
                startActivity(i);
            }
        });

        //Animation
        fadeIn= AnimationUtils.loadAnimation(this,R.anim.anim_fade_in);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        leftAnim= AnimationUtils.loadAnimation(this,R.anim.anim_slaide_in_left);
        rightAnim=AnimationUtils.loadAnimation(this,R.anim.anim_slide_in_right);
        outleft=AnimationUtils.loadAnimation(this,R.anim.anim_slide_out_left);
        outright=AnimationUtils.loadAnimation(this,R.anim.anim_slide_out_right);

        ImageView logo=findViewById(R.id.icon);
        logo.setAnimation(fadeIn);
        TextView livzatxt=findViewById(R.id.txt1);
        livzatxt.setAnimation(leftAnim);
        TextView loginUptxt=findViewById(R.id.loginUptxt);
        loginUptxt.setAnimation(rightAnim);
        TextView PhoneNumberTxt=findViewById(R.id.PhoneNumberTxt);
        PhoneNumberTxt.setAnimation(leftAnim);
        EditText PhoneNumber=findViewById(R.id.PhoneNumber);
        PhoneNumber.setAnimation(rightAnim);
        TextView textView4=findViewById(R.id.textView4);
        textView4.setAnimation(fadeIn);
        Sign_up.setAnimation(fadeIn);

    }

    private void init(){

        //View
        Logbtn=findViewById(R.id.LoginBtn);
        Phone_Number=findViewById(R.id.PhoneNumber);
        Sign_up=findViewById(R.id.Signup);


        //firebase
        mReference= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.i("sign_up","complet");
                signUp(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Log_In.this,"failed",Toast.LENGTH_SHORT).show();
                Log.w("sign_up", "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.i("sign_up","code:"+s);
                verificationDialog(s);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };

    }

    //This function to show the Dialog of the Confirmation Code to
    private void verificationDialog(String code) {
        Log.i("sign_up","dialog show");
        AlertDialog.Builder builder=new AlertDialog.Builder(Log_In.this);
        final View mView=getLayoutInflater().inflate(R.layout.custom_confirmation_code_dialog,null);
        builder.setView(mView);
        alertDialog=builder.create();
        mView.findViewById(R.id.Confirme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sign_up","confirme");
                String enterCode=((EditText)mView.findViewById(R.id.confimation_code)).getText().toString();
                Log.i("sign_up","code:"+code+"  enter code:"+enterCode);
                credential = PhoneAuthProvider.getCredential(code, enterCode);
                signUp(credential);
                alertDialog.dismiss();
            }
        });
        mView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void signUp(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("sign_up","onComplite");
                            Intent intent=new Intent(Log_In.this,Menu.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("sign_up","onComplite else");
                        }
                    }
                });
    }

    private void Check_the_data(){
        Log.i("sign_up","Check_the_data");
        final CountDownLatch done=new CountDownLatch(1);
        PhoneNumber=Phone_Number.getText().toString();
        Log.i("sign_up","Check_the_data phone:"+PhoneNumber);
        if(!Character.toString(PhoneNumber.charAt(0)).equals("+")){
            PhoneNumber="+213"+PhoneNumber.substring(1);
            Log.i("sign_up","Check_the_data phone 2:"+PhoneNumber);
        }
        if(!isValidPhone(PhoneNumber)) {
            Toast.makeText(getApplicationContext(),"this phone is envalide",Toast.LENGTH_SHORT).show();
            Log.i("sign_up","Check_the_data phone: invalide");
        }
        Log.i("sign_up","Check_the_data phone: valide");
        mReference.child("Users").orderByChild("phone").equalTo(PhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.i("sign_up","Check_the_data phone: exist");
                    sendCode();
                }else{
                    Log.i("sign_up","Check_the_data phone: not exist");
                    Toast.makeText(getApplicationContext(),"no user with this phone",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Eroor",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendCode() {
        Log.i("sign_up","nume:"+PhoneNumber);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(PhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private boolean isValidPhone(String phone) {
        //TODO: isValidPhone

        return true;
    }

}