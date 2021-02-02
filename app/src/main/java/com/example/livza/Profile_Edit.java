package com.example.livza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.TimeUnit;

public class Profile_Edit extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    public static final int codeSave = 5;
    private Boolean changPhoto = false;
    private String userName, PhoneNumber;
    private ImageView EditProfileImage;
    private EditText userNameEdit, phoneNumberEdit;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ImageView edit;

    //firebase
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__edit);

        init();
        loudData();
        //Edit_Profil_img();
        Save();
        Cancel();
    }

    public void init() {
        //view
        EditProfileImage = findViewById(R.id.Activity_Profile_edit_user_profile_img);
        userNameEdit = findViewById(R.id.Activity_Profile_edit_user_userName);
        phoneNumberEdit = findViewById(R.id.Activity_Profile_edit_user_phoneNumber);
        edit = findViewById(R.id.edit_img);

        //firebase
        mStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.i("editProfile", "mCallbacks:onVerificationCompleted");
                updatePhone(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("editProfile", "mCallbacks:onVerificationFailed");
                Toast.makeText(Profile_Edit.this, "failed", Toast.LENGTH_SHORT).show();
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
                Log.i("editProfile", "mCallbacks:onCodeSent");
                verificationDialog(s);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };
    }


    //this function to Edit the image profile
    public void Edit_Profil_img() {

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Gallery = new Intent();
                Gallery.setType("image/*");
                Gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(Gallery, "Select your profil Image"), PICK_IMAGE);
            }
        });

    }

    //Save the modifications
    public void Save() {
        Button Save_btn = findViewById(R.id.Activity_Profile_edit_save_btn);
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save in Firebase
                chechData();
            }
        });
    }


    public void Cancel() {
        Button Cancel_btn = findViewById(R.id.cancel_btn);
        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this function to get profil image from the external storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ImageView profile_img = findViewById(R.id.Activity_Profile_edit_user_profile_img);
            profile_img.setImageURI(imageUri);
            //Ghanou hna dir update l userprofile f fire base brk

            //this cmnt to crop the image if we need it futerlly *_____*
            /*CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);*/
        }
       /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK) {
                Uri resultUri = result.getUri();

               // ImageView profile_img2 = findViewById(R.id.profile_img2);
                CircleImageView profile_img = findViewById(R.id.profile_img);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    //profile_img2.setImageBitmap(bitmap);
                    profile_img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    //Intent just for test
                    Intent o=new Intent(Profile_User.this,Menu.class);
                    startActivity(o);
                }
            }
        }*/
    }

    private void loudData() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child("username").getValue(String.class);
                PhoneNumber = snapshot.child("phone").getValue(String.class);
                PhoneNumber = "0" + PhoneNumber.substring(4);
                userNameEdit.setText(userName);
                phoneNumberEdit.setText(PhoneNumber);
                String imageID = snapshot.child("ImageID").getValue(String.class);
                StorageReference mStorageRef = mStorage.getReference().child("/Users").child("/" + imageID);
                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(EditProfileImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Glide.with(getApplicationContext())
                                .load(R.drawable.addtocart_cochetrue)
                                .into(EditProfileImage);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void verificationDialog(String code) {
        Log.i("editProfile", "verificationDialog");
        Dialog dialog = new Dialog(Profile_Edit.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_confirmation_code_dialog);
        dialog.findViewById(R.id.Confirme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterCode = ((EditText) dialog.findViewById(R.id.confimation_code)).getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code, enterCode);
                updatePhone(credential);
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

    private void sendCode(String Phone) {
        Log.i("editProfile", "nume:" + Phone);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(Phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void chechData() {
        if (phoneNumberEdit.getText().toString().equals(PhoneNumber)) {
            returnData(!userNameEdit.getText().toString().equals(userName), false, changPhoto);
        } else {
            DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
            mReference.child("Users").orderByChild("phone").equalTo("+213" + phoneNumberEdit.getText().toString().substring(1)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(Profile_Edit.this, "this phone is used", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.i("editProfile", "send code:" + "+213" + phoneNumberEdit.getText().toString().substring(1));
                        sendCode("+213" + phoneNumberEdit.getText().toString().substring(1));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Eroor", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void returnData(Boolean username, Boolean phone, Boolean photo) {
        Log.i("editProfile", "returnData:username: " + username + "  phone:" + phone);
        if (username) mRef.child("username").setValue(userNameEdit.getText().toString());
        if (phone)
            mRef.child("phone").setValue("+213" + phoneNumberEdit.getText().toString().substring(1));
        if (photo) {
            //TODO: photo update
        }
        Intent intent = new Intent();
        intent.putExtra("userName", username);
        intent.putExtra("phone", phone);
        intent.putExtra("photo", photo);
        setResult(codeSave, intent);
        finish();
    }

    private void updatePhone(PhoneAuthCredential phoneAuthCredential) {
        Log.i("editProfile", "updatePhone");

        mAuth.getCurrentUser().updatePhoneNumber(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("editProfile", "updatePhone:isSuccessful");
                    returnData(!userNameEdit.getText().toString().equals(userName), true, changPhoto);
                } else {
                    Toast.makeText(getApplicationContext(), "Eroor", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}