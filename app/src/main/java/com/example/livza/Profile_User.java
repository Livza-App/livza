package com.example.livza;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.io.IOException;

public class Profile_User extends AppCompatActivity {


    private TextView Save, Cancel;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadtask;
    private StorageReference storageProfilePicReference;

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__user);

        //init from the firase **Ghano hna jibna m firbase istens et tt..
        /*firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseStorage.getInstance().getReference().child("user");
        storageProfilePicReference=FirebaseStorage.getInstance().getReference().child("Profile Pic");*/


        //Edit the profile
        Button Edit = findViewById(R.id.Edit_profile);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_profile_Dialog();
            }
        });

        //Back btn
        Button back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(Profile_User.this, Menu.class);
                startActivity(b);
            }
        });

    }

    //this function run when the user want to edit the Profile_Ditails
    public void Edit_profile_Dialog() {
        Dialog Edit_Profile_Dialog = new Dialog(this);
        Edit_Profile_Dialog.setContentView(R.layout.custom_edit_profile_dialog);
        Edit_Profile_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Edit_Profile_Dialog.show();

        //Save the modifications
      TextView Save_btn = findViewById(R.id.Save);
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set The User Name **Ghanou hna tedi user name t3awd dirlo update f user firebase table**
                EditText user_name_edit = Edit_Profile_Dialog.findViewById(R.id.PersonName);
                TextView User_name = findViewById(R.id.user_name);
                User_name.setText(user_name_edit.getText());

                //set the Phone number **Ghanou hna tedi Phone num t3awd dirlo update f user firebase table**
                EditText phone_number_edit = findViewById(R.id.editTextNumber);
                TextView Phone_number = findViewById(R.id.num);
                Phone_number.setText(phone_number_edit.getText());

                //set Profile _img **Ghanou hna tedi Profile _img t3awd dirlo update f user firebase table**


            }
        });

        CardView ProfileChange_btn = findViewById(R.id.User1);
        ProfileChange_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Gallery = new Intent();
                Gallery.setType("image/*");
                Gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(Gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        //Dont save the modifications
        TextView Cancel_btn = findViewById(R.id.Cancel);
        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Profile_Dialog.hide();
            }
        });

    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                ImageView profile_img = findViewById(R.id.profile_img2);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    profile_img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}