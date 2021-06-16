package com.example.animation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Top3Players extends AppCompatActivity implements OnMapReadyCallback {

    public boolean isPermissionGranted = StartGameActivity.permissionGranted;

    GoogleMap mGoogleMap;
    SupportMapFragment supportMapFragment;

    TextView player1,player2,player3,score1,score2,score3;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top3_players);

        player1 = findViewById(R.id.firstplayer);
        score1 = findViewById(R.id.firstplayerscore);
        player2 = findViewById(R.id.secondplayer);
        score2 = findViewById(R.id.secondplayerscore);
        player3 = findViewById(R.id.thirdplayer);
        score3 = findViewById(R.id.thirdplayerscore);

        DB = new DBHelper(this);

        String[] players = new String[3];
        String[] scores = new String[3];

        Cursor res = DB.getTop3();

        if(res.getCount() != 0){
            int i = 0;
            while (res.moveToNext() && i<3){
                players[i] = res.getString(0);
                scores[i] = String.valueOf(res.getInt(2));
                i++;
            }
        }else{
            Toast.makeText(getBaseContext(),"No Player Exists in DB",Toast.LENGTH_SHORT).show();
        }
        player1.setText(players[0]);
        player2.setText(players[1]);
        player3.setText(players[2]);
        score1.setText(scores[0]);
        score2.setText(scores[1]);
        score3.setText(scores[2]);

        if(isPermissionGranted){
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
            supportMapFragment.getMapAsync(this::onMapReady);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Toast.makeText(getBaseContext(),"Map is Ready",Toast.LENGTH_SHORT).show();

        String gAddress = "Rajahmundry";
        Geocoder geocoder = new Geocoder(Top3Players.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(gAddress, 1);
            if (addresses.size() > 0){
                Address address = addresses.get(0);
                double lat = addresses.get(0).getLatitude();
                double lon = addresses.get(0).getLongitude();
                LatLng long_and_lat = new LatLng(lat, lon);
                mGoogleMap.clear();
                mGoogleMap.addMarker(new MarkerOptions().position(long_and_lat).title("Player-1"));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(long_and_lat.latitude, long_and_lat.longitude)).zoom(14).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }else{
                new AlertDialog.Builder(Top3Players.this)
                        .setTitle("Oops!!")
                        .setMessage("No Map found for given Player-1 Please provide correct Address")
                        .setNegativeButton("Ok", null).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String gAddress1 = "Morampudi";
        Geocoder geocoder1 = new Geocoder(Top3Players.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder1.getFromLocationName(gAddress1, 1);
            if (addresses.size() > 0){
                Address address = addresses.get(0);
                double lat = addresses.get(0).getLatitude();
                double lon = addresses.get(0).getLongitude();
                LatLng long_and_lat = new LatLng(lat, lon);
                mGoogleMap.addMarker(new MarkerOptions().position(long_and_lat).title("Player-2"));
                //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(long_and_lat.latitude, long_and_lat.longitude)).zoom(16).build();
                //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }else{
                new AlertDialog.Builder(Top3Players.this)
                        .setTitle("Oops!!")
                        .setMessage("No Map found for given Address-1 Please provide correct Address")
                        .setNegativeButton("Ok", null).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String gAddress2 = "Vlpuram";
        Geocoder geocoder2 = new Geocoder(Top3Players.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder2.getFromLocationName(gAddress2, 1);
            if (addresses.size() > 0){
                Address address = addresses.get(0);
                double lat = addresses.get(0).getLatitude();
                double lon = addresses.get(0).getLongitude();
                LatLng long_and_lat = new LatLng(lat, lon);
                mGoogleMap.addMarker(new MarkerOptions().position(long_and_lat).title("Player-3"));
            }else{
                new AlertDialog.Builder(Top3Players.this)
                        .setTitle("Oops!!")
                        .setMessage("No Map found for given Address-1 Please provide correct Address")
                        .setNegativeButton("Ok", null).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(),StartGameActivity.class);
        startActivity(intent);
        finish();
    }
}