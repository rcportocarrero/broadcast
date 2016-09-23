package com.mov.tracker;

import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mov.tracker.GPS.LocationTrackerService;

public class MainActivity extends AppCompatActivity {

    LocationTrackerService gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickComenzar(View v) {
        gps = new LocationTrackerService(MainActivity.this);

        // check if GPS enabled
        if(gps.getStateLocation()){

            double latitude = gps.obtenerLatitude();
            double longitude = gps.obtenerLongitude();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            gps.showSettingsAlert();
        }
    }

    public void onClickDetener(View v) {
        stopService(new Intent(getBaseContext(), TrackerService.class));
    }

}
