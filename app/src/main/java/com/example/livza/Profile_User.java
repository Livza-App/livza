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
    private TextView user_name,phone_num;
    private ImageView profile_img;
    private CardView cardUser;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Button editProfile;
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

        //initOnClick();  hadi ida ma ts7a9hach supp ghano

        //reload data
        reloadData();

        Onback();



    }

    private void init() {
        //init View
        cardUser=findViewById(R.id.cardUser);
        editProfile=findViewById(R.id.edit_profile);
        //edit profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u=new Intent(Profile_User.this,Profile_Edit.class);
                startActivity(u);
            }
        });
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

    public void  Onback(){

        Button back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
            }
        });

    }

    //hadi teaa Qr Code ida ma ts7a9hach ghnou supp
   /* private void initOnClick(){
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
    }*/

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



}