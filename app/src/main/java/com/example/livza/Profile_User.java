package com.example.livza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.livza.Adapters.ViewPagerAdapter;
import com.example.livza.FireClasses.User;
import com.example.livza.Fragments.MyAdressesFragment;
import com.example.livza.Fragments.OrederHistoryFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_User extends AppCompatActivity {

    //view
    private TextView Save, Cancel,UserNameEdit,user_name,phone_num;
    private Uri imageUri;
    private ImageView profile_img;
    private CardView cardUser;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Button editProfile;


    private static final int PICK_IMAGE = 1;

    //Firebase var
    private FirebaseUser mAuth;
    private FirebaseStorage mStorage;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__user);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));

        init();
        initOnClick();
        //reload data
        reloadData();

        //edit profile img
        cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Profil_img();
            }
        });

        //Edit the UserName && Phone Number
        /*UserNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_UserName();
            }
        });*/


        //Back btn
        Button back = findViewById(R.id.btn_back);
        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent b = new Intent(Profile_User.this, Menu.class);
                startActivity(b);
                finish();
            }
        });*/


    }

    private void init() {
        //init View
        cardUser=findViewById(R.id.cardUser);
        editProfile=findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //UserNameEdit=findViewById(R.id.user_name);
        user_name=findViewById(R.id.user_name);
        phone_num=findViewById(R.id.user_phone);
        profile_img=findViewById(R.id.user_profil_img);

        //TO inisialize the OrderHistory_fragments
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    }
                }).attach();

        //init FireBase
        mAuth=FirebaseAuth.getInstance().getCurrentUser();
        mRef= FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();

    }

    private void initOnClick(){
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Profile_User.this);
                dialog.setContentView(R.layout.code_qr);
                ImageView qrCode=dialog.findViewById(R.id.qr_image);
                MultiFormatWriter writer=new MultiFormatWriter();
                try {
                    BitMatrix matrix=writer.encode(mAuth.getUid(), BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder encoder=new BarcodeEncoder();
                    Bitmap bitmap=encoder.createBitmap(matrix);
                    qrCode.setImageBitmap(bitmap);
                    //InputMethodManager methodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                dialog.show();
            }
        });
    }

    private void reloadData() {
        mRef.child("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                user_name.setText(user.getUsername());
                phone_num.setText(user.getPhone());
                StorageReference mStorageRef=mStorage.getReference().child("/Users").child("/"+user.getImageID());
                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(profile_img);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Glide.with(getApplicationContext())
                                .load(R.drawable.addtocart_cochetrue)
                                .into(profile_img);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        /*Dialog Edit_Profile_Dialog = new Dialog(this);
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
                TextView Phone_number = findViewById(R.id.user_phone);
                Phone_number.setText(phone_number_edit.getText());

                Edit_Profile_Dialog.hide()
            }
        });

        //Dont save the modifications
        TextView Cancel_btn = Edit_Profile_Dialog.findViewById(R.id.Cancel);
        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Profile_Dialog.hide();
            }
        });*/

    }

    //this function to get profil image from the external storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ImageView profile_img = findViewById(R.id.user_profil_img);
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