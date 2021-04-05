package com.sccengineer;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sccengineer.maps.MyLocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private DatabaseReference reference;
    private LocationManager manager;
    private final int MIN_TIME = 1000; // 1sec
    private final int MIN_DISTANCE = 1; // 1meter
    Marker myMarker;
    double lat = 0.0;
    double longi = 0.0;
    String cityName = "No Name";
    LatLng position = null;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        position = new LatLng(lat,longi);
        reference = FirebaseDatabase.getInstance().getReference().child("user001");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocationupdate();

        readChange();
    }

    private void readChange() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    try {
                        MyLocation location = dataSnapshot.getValue(MyLocation.class);
                        if (location !=null){
                            myMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                            lat = location.getLatitude();
                            longi = location.getLongitude();
                            getLocationName(lat,longi);
                            myMarker.setTitle(cityName);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLocationupdate() {
        if (manager !=null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                } else {
                    Toast.makeText(this, "No Provider Enable", Toast.LENGTH_SHORT).show();
                }
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==101){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLocationupdate();
            }else {
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, longi);

        myMarker = mMap.addMarker(new MarkerOptions().position(position));
        myMarker.setTitle(cityName);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
//        mMap.setMinZoomPreference(12);

//        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        position = new LatLng(lat,longi);
        if (location!=null){
            saveLocation(location);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
//            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            getLocationName(lat,longi);
            myMarker.setTitle(cityName);
        }else {
            Toast.makeText(this,"No Location",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    private void saveLocation(Location location) {
        reference.setValue(location);
    }
    public String getLocationName(double lattitude, double longitude) {


        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {

            List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
                    10);

            for (Address adrs : addresses) {
                if (adrs != null) {

                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        System.out.println("city ::  " + cityName);

                    } else {

                    }
                    // // you should also try with addresses.get(0).toSring();

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }
}