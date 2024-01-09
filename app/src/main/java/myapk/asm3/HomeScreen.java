package myapk.asm3;
// Swipe function are from: https://github.com/Diolor/Swipecards.git
// Current location functions are from Week 5

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    // Bottom navigation
    private BottomNavigationView bottomNav;
    private FrameLayout menu;

    // Get cur loc
    Location cur_loc;
    protected FusedLocationProviderClient fusedLocationProviderClient;
    protected LocationRequest mLocationRequest;
    private static final long UPDATE_INTERVAL = 20*1000 ;
    private static final long FASTEST_INTERVAL = 10*1000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Get cur loc
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdate();


        // Bottom nav
        bottomNav = findViewById(R.id.bottomNav);
        menu = findViewById(R.id.fragment);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.home) {
                    switchFragment(new HomeFragment(), false);
                } else if (itemID == R.id.chat) {
                    switchFragment(new ChatFragment(), false);
                } else if (itemID == R.id.profile) {
                    switchFragment(new ProfileFragment(), false);
                } else if (itemID == R.id.heat) {
                    switchFragment(new HeatFragment(), false);
                }
                return true;
            }
        });

        switchFragment( new HomeFragment(), true);
    }

    // Bottom nav handler
    private void switchFragment(Fragment fragment, boolean isOn){
        FragmentManager manage = getSupportFragmentManager();
        FragmentTransaction transaction = manage.beginTransaction();
        if (isOn) {
            transaction.add(R.id.fragment, fragment);
        } else {
            transaction.replace(R.id.fragment, fragment);
        }
        transaction.commit();
    }

    // Get cur loc
    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void startLocationUpdate(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                cur_loc = locationResult.getLastLocation();

                Log.d("Current location: ", "Lat: " + cur_loc.getLatitude() + ", " + "Long: " + cur_loc.getLongitude());
                Toast.makeText(HomeScreen.this, "Current location: " + "Lat: " + cur_loc.getLatitude() + ", " + "Long: " + cur_loc.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }, null);
    }
}