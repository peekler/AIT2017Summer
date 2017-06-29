package hu.ait.locationdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MyLocationMonitor.MyLocationListener, OnMapReadyCallback {

    private TextView tvLocationData = null;
    private MyLocationMonitor myLocationMonitor;

    private Location prevLocation = null;
    private float totalDistance = 0;

    private GoogleMap mMap;
    private Marker markerTarget = null;
    private Marker markerMyPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationMonitor = new MyLocationMonitor(this, this);

        tvLocationData = (TextView) findViewById(R.id.tvLocationData);

        tvLocationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });


        requestNeededPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Toast...
            }

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1001);
        } else {
            // we already have the permission
            myLocationMonitor.startLocationMonitoring();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted, jupeee!", Toast.LENGTH_SHORT).show();
                myLocationMonitor.startLocationMonitoring();

            } else {
                Toast.makeText(this, "Permission not granted :(", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void newLocationReceived(Location location) {
        if (prevLocation != null) {
            totalDistance += location.distanceTo(prevLocation);
        }

        prevLocation = location;

        String locText = location.getProvider()+"\n"+
                "Lat: "+location.getLatitude()+"\n"+
                "Lng: "+location.getLongitude()+"\n"+
                "Accuracy: "+location.getAccuracy()+"\n"+
                "Altitude: "+location.getAltitude()+"\n"+
                "Speed: "+location.getSpeed()+"\n"+
                "Timestamp: "+new Date(location.getTime()).toString()+"\n"+
                "Distance (m): "+totalDistance;

        tvLocationData.setText(locText);

        setMyMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    protected void onDestroy() {
        myLocationMonitor.stopLocationMonitoring();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setTrafficEnabled(true);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (markerTarget == null) {
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Target");
                    markerTarget = mMap.addMarker(markerOptions);
                    markerTarget.setDraggable(true);
                } else {
                    markerTarget.setPosition(latLng);
                }

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                try {
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    List<Address> addressList = null;
                    addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String address = addressList.get(0).getAddressLine(0)+"\n"+
                            addressList.get(0).getAddressLine(1)+"\n"+
                            addressList.get(0).getAddressLine(2)+"\n"+
                            addressList.get(0).getAddressLine(3);

                    Toast.makeText(MainActivity.this,
                            "Target: "+address, Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        PolylineOptions polyLineOpts = new PolylineOptions().add(new LatLng(44, 19),
                new LatLng(44, 26),
                new LatLng(48, 26));

        Polyline polyline = mMap.addPolyline(polyLineOpts);
        polyline.setColor(Color.GREEN);

    }


    public void setMyMarker(LatLng myPosition) {
        if (markerMyPosition == null) {
            MarkerOptions markerOptions = new MarkerOptions().
                    position(myPosition).
                    title("I'm here").
                    icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

            markerMyPosition = mMap.addMarker(markerOptions);
        } else {
            markerMyPosition.setPosition(myPosition);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLng(myPosition));


        if (markerTarget != null) {
            float[] result = new float[1];
            Location.distanceBetween(myPosition.latitude, myPosition.longitude,
                    markerTarget.getPosition().latitude, markerTarget.getPosition().longitude, result);

            Toast.makeText(this, "Distance: " + result[0] + " m", Toast.LENGTH_SHORT).show();
        }
    }


}
