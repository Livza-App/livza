package com.example.livza;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import de.hdodenhof.circleimageview.CircleImageView;

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
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));


        //edit profil img
        CardView profile_img=findViewById(R.id.cardUser);
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Profil_img();
            }
        });

        //Edit the UserName && Phonen Number
        TextView UserNameEdit=findViewById(R.id.user_name);
        UserNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_UserName();
            }
        });


        //Back btn
        Button back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(Profile_User.this, Menu.class);
                startActivity(b);
            }
        });


    }


    //this function to Edit the image profile
    public void Edit_Profil_img(){
        Intent Gallery=new Intent();
        Gallery.setType("image/*");
        Gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(Gallery, "Select your profil Image"), PICK_IMAGE );
    }

    //this function run when the user want to edit UserName && Phone Number
    public void Edit_UserName(){
        Dialog Edit_Profile_Dialog = new Dialog(this);
        Edit_Profile_Dialog.setContentView(R.layout.custom_edit_profile_dialog);
        Edit_Profile_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Edit_Profile_Dialog.show();

        //Save the modifications
        TextView Save_btn = Edit_Profile_Dialog.findViewById(R.id.Save);
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set The User Name **Ghanou hna tedi user name t3awd dirlo update f user firebase table**
                EditText user_name_edit = Edit_Profile_Dialog.findViewById(R.id.PersonName);
                TextView User_name = findViewById(R.id.user_name);
                User_name.setText(user_name_edit.getText().toString());

                //set the Phone number **Ghanou hna tedi Phone num t3awd dirlo update f user firebase table**
                EditText phone_number_edit = findViewById(R.id.editTextPhone);
                TextView Phone_number = findViewById(R.id.num);
                Phone_number.setText(phone_number_edit.getText());

                Edit_Profile_Dialog.hide();
            }
        });

        //Dont save the modifications
        TextView Cancel_btn = Edit_Profile_Dialog.findViewById(R.id.Cancel);
        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Profile_Dialog.hide();
            }
        });

    }

    //this function to get profil image from the external storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            CircleImageView profile_img = findViewById(R.id.profile_img);
            profile_img.setImageURI(imageUri);

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