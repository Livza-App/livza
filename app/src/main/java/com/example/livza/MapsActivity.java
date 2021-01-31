package com.example.livza;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private EditText mSearchText;
    private TextView save,cancel;
    private GoogleMap mMap;
    private View mapView;
    public static String TAG = "Maps";
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng markerPos;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapView = mapFragment.getView();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        init();
        saveAdressDialoge();
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 180, 180, 0);
        //rlp.addRule(RelativeLayout.BELOW, R.id.activity_maps_searchcard);

        //
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(latLng);
                markerPos=latLng;
                markerOptions.title("you choosed this position");
                mMap.clear();
                mMap.addMarker(markerOptions);
                save.setEnabled(true);
            }
        });

        //correct location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        LatLng user_location = new LatLng(location.getLatitude(), location.getLongitude());


                        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_location));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(user_location)      // Sets the center of the map to user_location
                                .zoom(10)                   // Sets the zoom to 15 (city zoom)
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                    }
                }
            });
            mMap.setMyLocationEnabled(true);
        }
    }

    private void init() {
        //view
        mSearchText = findViewById(R.id.activity_maps_searchadresse);
        cancel=findViewById(R.id.activity_maps_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save=findViewById(R.id.activity_maps_saveadresse);
        save.setEnabled(false);


        //Firebase
        mRef= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    search();

                }
                return false;
            }
        });
    }

    private void search() {
        String searchText = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchText, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            //TODO : search cantry
            moveCamera(address);
        }
    }

    private void moveCamera(Address addressSearch) {
        save.setEnabled(true);
        LatLng adresse = new LatLng(addressSearch.getLatitude(), addressSearch.getLongitude());
        markerPos=adresse;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(adresse)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .tilt(30)
                // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(adresse);
        markerOptions.title("you choosed this position");
        mMap.clear();
        mMap.addMarker(markerOptions);

    }

    private void saveAdressDialoge(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MapsActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.add_adresse);
                dialog.setCancelable(false);
                EditText addName=dialog.findViewById(R.id.add_adresse_edit_adresse);
                List<Address> addresses = null;
                try {

                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(markerPos.latitude, markerPos.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses != null) {
                    String city = addresses.get(0).getLocality();
                    addName.setText(city);
                }
                dialog.findViewById(R.id.add_adresse_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference addressesRef=mRef.child("addresses").child(mAuth.getCurrentUser().getUid()).push();
                        addressesRef.child("latitude").setValue(markerPos.latitude);
                        addressesRef.child("longitude").setValue(markerPos.longitude);
                        addressesRef.child("name").setValue(addName.getText().toString());
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.findViewById(R.id.add_adresse_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }

}