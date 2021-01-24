package com.example.livza;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile_Edit extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__edit);

        Edit_Profil_img();
        Save();
        Cancel();
    }


    //this function to Edit the image profile
    public void Edit_Profil_img(){
        ImageView edit=findViewById(R.id.edit_img);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Gallery=new Intent();
                Gallery.setType("image/*");
                Gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(Gallery, "Select your profil Image"), PICK_IMAGE );
            }
        });

    }
    //Save the modifications
    public void Save(){
        EditText User_Name=findViewById(R.id.editUserName);
        EditText User_Phone=findViewById(R.id.editPhonenumber);
        Button Save_btn=findViewById(R.id.save_btn);
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ghanou hna dir update l user name w phone number f fire base brk


            }
        });
    }


    public void Cancel(){
        Button Cancel_btn=findViewById(R.id.cancel_btn);
        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Back_to_user_profile=new Intent(Profile_Edit.this,Profile_User.class);
                startActivity(Back_to_user_profile);
            }
        });
    }

    //this function to get profil image from the external storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ImageView profile_img = findViewById(R.id.user_profil_img);
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


}