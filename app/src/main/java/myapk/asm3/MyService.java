package myapk.asm3;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyService extends Service {
    private static String userEmail = HomeScreen.userEmail;
    private static String status, statusMates, statusProfile, statusMatches ="";
    static Location cur_loc;
    private ArrayList<Location> locList;
    protected FusedLocationProviderClient fusedLocationProviderClient;
    protected LocationRequest mLocationRequest;
    private static final long UPDATE_INTERVAL = 20*1000 ;
    private static final long FASTEST_INTERVAL = 10*1000 ;
    public MyService() {
        locList = new ArrayList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        new GetProfile().execute();
//        new GetMatches().execute();
//        new GetMates().execute();
        startLocationUpdate();
        return super.onStartCommand(intent,flags,startId);
    }

    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void startLocationUpdate() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                cur_loc = locationResult.getLastLocation();

                if (cur_loc != null) {
                    if (locList.size() > 2) {
                        locList.remove(0);
                    }

                    if (locList.isEmpty() || (locList.get(0).getLatitude() - cur_loc.getLatitude() > 0.1)
                            && (locList.get(0).getLongitude() - cur_loc.getLongitude() > 0.1)) {
                        new PostLocation().execute();
//                        new GetMates().execute();

                        Log.d("Location: ", cur_loc.getLongitude() + ", " + cur_loc.getLatitude());
                        Toast.makeText(MyService.this, "Location: " + cur_loc.getLongitude() + ", " + cur_loc.getLatitude(), Toast.LENGTH_SHORT).show();
                    }
                    // Add the current location to the list
                    locList.add(cur_loc);
                }
            }
        }, null);
    }


    private static class PostLocation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("About to update: ", cur_loc.getLongitude() + ", " + cur_loc.getLatitude());
            status = HttpHandler.postLocation(userEmail,cur_loc.getLatitude(),cur_loc.getLongitude());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle post-execution tasks if needed
            // You can access the status returned by the postLocation method here
        }
    }



}