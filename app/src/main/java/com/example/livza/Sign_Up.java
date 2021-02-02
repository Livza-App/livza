package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Sign_Up extends AppCompatActivity {

    // view variables
    private EditText User_Name;
    private EditText Phone_Number;
    private ImageView SignUpBtn;
    private Dialog dialog;

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
                Check_the_data();
            }
        });

        //logIn Btn
        TextView Login = findViewById(R.id.LoginTxt);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass = new Intent(Sign_Up.this, Log_In.class);
                startActivity(pass);
            }
        });


        //testTest intent tesssstttt brk
        ImageView logo = findViewById(R.id.icon2);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(Sign_Up.this, Profile_User.class);
                startActivity(o);

            }
        });
    }

    private void init() {

        // init view
        User_Name = findViewById(R.id.UserName);
        Phone_Number = findViewById(R.id.PhoneNumber);
        SignUpBtn = findViewById(R.id.SigneUpBtn);

        //init Firebase
        mReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //help
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.i("sign_up", "complet");
                signUp(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Sign_Up.this, "failed", Toast.LENGTH_SHORT).show();
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
                Log.i("sign_up", "code:" + s);
                verificationDialog(s);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };

    }

    private void verificationDialog(String code) {
        Log.i("sign_up", "dialog show");
        dialog = new Dialog(Sign_Up.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_confirmation_code_dialog);
        dialog.findViewById(R.id.Confirme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sign_up", "confirme");
                String enterCode = ((EditText) dialog.findViewById(R.id.confimation_code)).getText().toString();
                Log.i("sign_up", "code:" + code + "  enter code:" + enterCode);
                credential = PhoneAuthProvider.getCredential(code, enterCode);
                signUp(credential);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void signUp(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("sign_up", "onComplite");
                            saveData();
                            Intent intent = new Intent(Sign_Up.this, Menu.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.i("sign_up", "onComplite else");
                        }
                    }
                });
    }

    //save data in RealTime_Database
    private void saveData() {
        Log.i("sign_up", "savedata");
        mReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("username").setValue(UserName);
        mReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("phone").setValue(PhoneNumber);
        mReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("ImageID").setValue("Default.png");

    }

    //verifai the number if he is valide
    private void Check_the_data() {
        Log.i("sign_up", "Check_the_data");
        final CountDownLatch done = new CountDownLatch(1);
        PhoneNumber = Phone_Number.getText().toString();
        Log.i("sign_up", "Check_the_data phone:" + PhoneNumber);
        if (!Character.toString(PhoneNumber.charAt(0)).equals("+")) {
            PhoneNumber = "+213" + PhoneNumber.substring(1);
            Log.i("sign_up", "Check_the_data phone 2:" + PhoneNumber);
        }
        if (!isValidPhone(PhoneNumber)) {
            Toast.makeText(getApplicationContext(), "this phone is envalide", Toast.LENGTH_SHORT).show();
            Log.i("sign_up", "Check_the_data phone: invalide");
        }
        Log.i("sign_up", "Check_the_data phone: valide");
        mReference.child("Users").orderByChild("phone").equalTo(PhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.i("sign_up", "Check_the_data phone: exist");
                    Toast.makeText(getApplicationContext(), "this phone is used", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("sign_up", "Check_the_data phone: not exist");
                    UserName = User_Name.getText().toString();
                    sendCode();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Eroor", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendCode() {
        Log.i("sign_up", "nume:" + PhoneNumber);
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
        //TODO: isValidPhone nkamalha

        return true;
    }
}


