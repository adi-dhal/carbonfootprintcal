package com.example.adityadhall.carbon;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class personal_cal_travel extends AppCompatActivity {

    private Button Car;
    private Button walk;
    private Button bus;
    private Button stop;
    private Button next;
    private EditText flight_hours;
    private TextView distance_travellled;
    private float carbon_fp = 0;
    private LocationManager locationmanager;
    private LocationListener locationlistener;
    private int button_flag = 0;
    private float travel_fp=0;
    private double lat;
    private double lon;
    private float distance_cov=0;
    private int mode=0;
    private float save=0;
    SharedPreferences sharedPreference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_cal_travel);
        Car = (Button) findViewById(R.id.car_opt);
        walk = (Button) findViewById(R.id.walk_opt);
        bus = (Button) findViewById(R.id.public_opt);
        stop = (Button) findViewById(R.id.stop);
        next = (Button) findViewById(R.id.next_food);
        flight_hours = (EditText) findViewById(R.id.flight_hrs);
        distance_travellled = (TextView) findViewById(R.id.distance);
        locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                loc_update();
                break;
            default:
                break;
        }
    }
    private float cal_dist(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;

        double dLat = Math.toRadians(lat1-lat2);
        double dLng = Math.toRadians(lng1-lng2);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
       // Toast.makeText(this,"sfbdh",Toast.LENGTH_LONG).show();
        return (float) dist;
    }

    private void loc_update() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        locationmanager.requestLocationUpdates("gps", 1000, 0, locationlistener);
    }


    public void onClickcar(View view) {
        locationlistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (button_flag != 1) {
                    mode=1;
                    if (button_flag == 0) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        button_flag = 2;
                    } else {
                        float distance = cal_dist(lat, lon, location.getLatitude(), location.getLongitude());
                        if(distance >0.0001) {
                            distance_cov = distance_cov + distance;
                            //travel_fp = travel_fp + (distance);
                            distance_travellled.setText(String.valueOf(distance_cov));
                        }
                    }

                }
            }

                @Override
                public void onStatusChanged (String s,int i, Bundle bundle){

                }

                @Override
                public void onProviderEnabled (String s){

                }

                @Override
                public void onProviderDisabled (String s){
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);

                }
        };
        loc_update();
    }

    public void onClickwalk(View view) {
            locationlistener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(button_flag != 1){
                        mode=2;
                        if (button_flag == 0) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            button_flag = 2;
                        } else {
                            float distance = cal_dist(lat, lon, location.getLatitude(), location.getLongitude());
                            if (distance > 0.0001) {
                                distance_cov = distance_cov + distance;
                              //  travel_fp = travel_fp + (distance);
                                distance_travellled.setText(String.valueOf(distance_cov));
                            }
                        }
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            };
            loc_update();
    }
    public void onClickpublic(View view) {
            locationlistener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (button_flag != 1) {
                        mode=3;
                        if (button_flag == 0) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            button_flag = 2;
                        } else {
                            float distance = cal_dist(lat, lon, location.getLatitude(), location.getLongitude());
                            if(distance >0.0001) {
                                distance_cov = distance_cov + distance;
                                //travel_fp = travel_fp + (distance);
                                distance_travellled.setText(String.valueOf(distance_cov));
                            }                        }

                    }
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);

                }
            };
            loc_update();
    }

    public void onClicknext(View view) {
        Intent next = new Intent(this,food.class);
        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(next);
        finish();
    }

    public void onClickstop(View view) {
        button_flag =1;
        if (mode == 1)
        {
            travel_fp= travel_fp+(float) ((float)(distance_cov/100.0)*186.0);
        }
        else if(mode == 3)
        {
            travel_fp= travel_fp+(float) ((float)(distance_cov/100.0)*93.0);
        }
        else if(mode == 2)
        {
            save=save+(float) ((float)(distance_cov/100.0)*93.0);
        }
        mode=0;
        SharedPreferences.Editor editor= sharedPreference.edit();
        travel_fp=travel_fp+sharedPreference.getFloat("carb_fp",-1);
        editor.putFloat("carb_fp",travel_fp);
        editor.putFloat("save_carb",save);
        editor.commit();
    }

    public void submit_flt(View view) {
        String hours = String.valueOf(flight_hours.getText());
        if (hours != ""){
            int flt=Integer.valueOf(hours);
            if(flt < 4)
                travel_fp= (float) (travel_fp+2771.0);
            else
                travel_fp= (float) (travel_fp+ 10886.0);
            SharedPreferences.Editor editor= sharedPreference.edit();
            travel_fp=travel_fp+sharedPreference.getFloat("carb_fp",-1);
            editor.putFloat("carb_fp",travel_fp);
            editor.commit();
        }

    }
}
