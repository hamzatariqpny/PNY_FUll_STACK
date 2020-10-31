package com.pny.pny67_68.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pny.pny67_68.R;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button Bzoomin, Bzoomout, BMaptype, Bsearch;
    EditText addressEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mMap);
        mapFragment.getMapAsync(this);

        Bzoomin = findViewById(R.id.Bzoomin);
        Bzoomout = findViewById(R.id.Bzoomout);
        BMaptype = findViewById(R.id.Btype);
        Bsearch = findViewById(R.id.Bsearch);
        addressEdt = findViewById(R.id.TFaddress);


        Bzoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });


        Bzoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });


        BMaptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else if (mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else if (mMap.getMapType() == GoogleMap.MAP_TYPE_HYBRID) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });


        Bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = addressEdt.getText().toString();

                if (address.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "Please enter valid address", Toast.LENGTH_SHORT).show();
                } else {
                    List<Address> addressList = null;

                    // initialize geocoder API
                    Geocoder geocoder = new Geocoder(MapsActivity.this);

                    try {
                        addressList = geocoder.getFromLocationName(address, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null) {
                        if (addressList.size() > 0) {

                            Address address1 = addressList.get(0);

                            // for lat long
                            LatLng latLng = new LatLng(address1.getLatitude(), address1.getLongitude());

                          //  to adjust camera
                            mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                        }
                    } else {
                        Toast.makeText(MapsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocationPermission();

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //  Toast.makeText(MapsActivity.this, location.toString(), Toast.LENGTH_SHORT).show();

               // mMap.addMarker(new MarkerOptions().position(latLng).title(address));
               // mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });

    }


    public void getLocationPermission() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int isPermissionGranted = getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

            if (isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }


    }

}