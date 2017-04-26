package com.example.pp.gpsapp;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.R.id.toggle;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    double longitude;
    double latitude;
    private TrackGps gps;
    ToggleButton start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //inizializzo il bottone
        start= (ToggleButton) findViewById(R.id.start);
        start.setTextOn("on");
        start.setTextOff("off");
        start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gps.setStartActivity(true);
                    gps.clearPoints();
                } else {
                    gps.setStartActivity(false);
                    gps.clearPoints();
                    gps.clearPolyLines();
                }
            }
        });
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //check gps, ti traccia e piazza un marker in current location
        gps = new TrackGps(MapsActivity.this);
        gps.setMap(googleMap);
        if (gps.canGetLocation()) {
          /*  longitude = gps.getLongitude();
            latitude = gps.getLatitude();
            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            LatLng curLoc = new LatLng(latitude, longitude);
            gps.getMap().addMarker(new MarkerOptions().position(curLoc).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            gps.getMap().moveCamera(CameraUpdateFactory.newLatLng(curLoc));*/
            gps.getMap().animateCamera( CameraUpdateFactory.zoomTo( 19.0f ) );
        } /*else {
            gps.showSettingsGps();
        }*/
    }
}
