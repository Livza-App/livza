package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Sign_Up extends AppCompatActivity {

    // view variables
    private EditText User_Name;
    private EditText Phone_Number;
    private ImageView SignUpBtn;
    private AlertDialog alertDialog;

    // Firebase variables
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private PhoneAuthCredential credential;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    // save variables
    private String UserName;
    private String PhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));

        init();
        //SignUp Btn
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=getBaseContext();
                if (Check_the_data()){
                    UserName=User_Name.getText().toString();
                    PhoneNumber=Phone_Number.getText().toString();
                    sendCode(PhoneNumber);
                }
            }
        });

        //logIn Btn
        TextView Login=findViewById(R.id.LoginTxt);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass=new Intent(Sign_Up.this,Log_In.class);
                startActivity(pass);
            }
        });


        //testTest intent tesssstttt brk
        ImageView logo=findViewById(R.id.icon2);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o=new Intent(Sign_Up.this,Profile_User.class);
                startActivity(o);

            }
        });
    }

    private void init() {

        // init view
        User_Name=findViewById(R.id.UserName);
        Phone_Number=findViewById(R.id.PhoneNumber);
        SignUpBtn=findViewById(R.id.SigneUpBtn);

        //init Firebase
        mReference=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        //help
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.i("sign_up","complet");
                signUp(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Sign_Up.this,"failed",Toast.LENGTH_SHORT).show();
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

    private void verificationDialog(String code) {
        Log.i("sign_up","dialog show");
        AlertDialog.Builder builder=new AlertDialog.Builder(Sign_Up.this);
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
                            saveData();
                            Intent intent=new Intent(Sign_Up.this,Log_In.class);
                            startActivity(intent);
                            //finish();

                        } else {
                            Log.i("sign_up","onComplite else");
                        }
                    }
                });
    }

    private void saveData() {
        Log.i("sign_up","savedata");
        mReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("username").setValue(UserName);
        mReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("phone").setValue(PhoneNumber);

    }

    private Boolean Check_the_data(){
        return true;
    }

    private void sendCode(String phoneNumber) {
        if(Character.toString(phoneNumber.charAt(0)).equals("+")){
            Log.i("sign_up","nume:"+phoneNumber);
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            Log.i("sign_up","nume:"+phoneNumber);
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
        else{
            Log.i("sign_up","nume:"+"+213"+Phone_Number.getText().toString().substring(1));
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+213"+Phone_Number.getText().toString().substring(1),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks          // OnVerificationStateChangedCallbacks
            );
            Log.i("sign_up","nume:"+"+213"+Phone_Number.getText().toString().substring(1));
        }
    }
}


