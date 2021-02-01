package com.example.livza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_User extends AppCompatActivity {

    //view
    private TextView user_name, phone_num, current_location;
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

    //
    private FusedLocationProviderClient  fusedLocationClient;
    private Location location;
    private final int REQUEST_CHECK_CODE = 106;
    private LocationSettingsRequest.Builder builder;
    private LocationListener myLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__user);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));
        init();
        if (checkServeur()) checkPermission();
        //initOnClick();  hadi ida ma ts7a9hach supp ghano

        //reload data
        reloadData();

        Onback();


    }

    private boolean canToggleGPS() {
        PackageManager pacman = getPackageManager();
        PackageInfo pacInfo = null;

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            return false; //package not found
        }

        if (pacInfo != null) {
            for (ActivityInfo actInfo : pacInfo.receivers) {
                //test if recevier is exported. if so, we can toggle GPS.
                if (actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported) {
                    return true;
                }
            }
        }

        return false; //default
    }

    private void turnGPSOn() {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> result = client.checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                Log.i("profilesActivity", "onComplete");
                try {

                    task.getResult(ApiException.class);
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(Profile_User.this);
                    if (ActivityCompat.checkSelfPermission(Profile_User.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Profile_User.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                1000,
                                5000,
                                myLocationListener
                        );
                        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                // Got last known location. In some rare situations this can be null.
                                Log.i("profilesActivity", "lastLocation: success");

                                if (task.isSuccessful()) {
                                    // Logic to handle location object
                                    Geocoder geocoder = new Geocoder(Profile_User.this, Locale.getDefault());
                                    List<Address> addresses = null;
                                    location = task.getResult();
                                    if (location == null) {
                                        Log.i("profilesActivity", "location null");

                                    } else {
                                        Log.i("profilesActivity", "location not null");
                                        try {
                                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            String city = addresses.get(0).getLocality();
                                            current_location.setText(city);
                                        } catch (IOException e) {
                                            //e.printStackTrace();
                                            current_location.setText("erreur");
                                        }
                                    }
                                } else {
                                    Log.i("profilesActivity", "fused provider cant get location");

                                }

                            }
                        });


                        return;
                    }

                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:
                            try {
                                Log.i("profilesActivity", "RESOLUTION_REQUIRED");
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Profile_User.this, REQUEST_CHECK_CODE);
                            } catch (IntentSender.SendIntentException sendIntentException) {
                                Log.i("profilesActivity", "sendIntentException");
                                sendIntentException.printStackTrace();
                            } catch (ClassCastException ex) {
                                Log.i("profilesActivity", "ClassCastException");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {
                            Log.i("profilesActivity", "SETTINGS_CHANGE_UNAVAILABLE");
                            break;
                        }
                    }
                }catch (Exception e){
                    Log.i("profilesActivity", "Exception");
                }
            }
        });
    }


    private void init() {
        //init View
        cardUser = findViewById(R.id.cardUser);
        editProfile = findViewById(R.id.edit_profile);
        current_location = findViewById(R.id.possition);

        //edit profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u = new Intent(Profile_User.this, Profile_Edit.class);
                startActivity(u);
            }
        });
        user_name = findViewById(R.id.user_name);
        phone_num = findViewById(R.id.user_phone);
        profile_img = findViewById(R.id.user_profil_img);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //TO inisialize the OrderHistory_fragments
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    }
                }).attach();

        //add tab items with title..
        tabLayout.addTab(tabLayout.newTab().setText("Order history").setIcon(R.drawable.order_history));
        tabLayout.addTab(tabLayout.newTab().setText("My adresses ").setIcon(R.drawable.distance_1));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //init FireBase
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();

        //
        myLocationListener = new LocationListener() {

            public void onLocationChanged(Location location) {

            }
            public void onProviderDisabled(String provider) {}
            public void onProviderEnabled(String provider) {}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };

    }

    public void Onback() {

        Button back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                User user = snapshot.getValue(User.class);
                user_name.setText(user.getUsername());
                phone_num.setText(user.getPhone());
                StorageReference mStorageRef = mStorage.getReference().child("/Users").child("/" + user.getImageID());
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

    private boolean checkServeur() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Profile_User.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.i("profilesActivity", "serveur case 1");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.i("profilesActivity", "serveur case 2");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Profile_User.this, available, 9001);
        } else {
            Log.i("profilesActivity", "serveur case 3");
        }
        return false;
    }

    public void checkPermission() {
        Log.i("profilesActivity", "checkPermission");
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i("profilesActivity", "checkPermission: granted");
                createLocationRequest();

            } else {
                Log.i("profilesActivity", "checkPermission:not granted");
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else {
            finish();
        }
    }


}