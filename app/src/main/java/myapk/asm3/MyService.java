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
//    public static ArrayList<Bitmap> aImage;
//    public static ArrayList<String> aName;
//    public static ArrayList<String> aAge;
//    public static ArrayList<String> aDescription;
//    public static ArrayList<String> aEmail;
//
//    public static ArrayList<String> participants;
//    public static  Users curUser;
    public MyService() {
        locList = new ArrayList<>();
//        aImage = new ArrayList<>();
//        aName = new ArrayList<>();
//        aAge = new ArrayList<>();
//        aDescription = new ArrayList<>();
//        aEmail = new ArrayList<>();
//        participants = new ArrayList<>();
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

//    private class GetMates extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            userEmail = "handsomethinh0@gmail.com";
//            if(userEmail != null) {
//                statusMates = HttpHandler.getMates(userEmail);
//            }
//            Log.d("Status: ", statusMates);
//            Log.d("Status: ", String.valueOf(statusMates.split(" ")[0].equalsIgnoreCase("Error")));
//            return null;
//        }
//        protected void onPostExecute(Void avoid) {
//            if(!statusMates.split(" ")[0].equalsIgnoreCase("Error")) {
//                try {
//                    JSONArray jsonArray = new JSONArray(statusMates);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        String name = jsonObject.getString("name");
//                        HomeScreen.aName.add(name);
//
//                        String age = jsonObject.getString("age");
//                        HomeScreen.aAge.add(age);
//
//                        String description = jsonObject.getString("description");
//                        HomeScreen.aDescription.add(description);
//
//                        String email = jsonObject.getString("email");
//                        HomeScreen.aEmail.add(email);
//
//                        // Get the "avatarImg" object from the user object
//                        JSONObject avatarImgObj = jsonObject.getJSONObject("avatarImg");
//                        // Get the "data" object
//                        JSONObject dataObj = avatarImgObj.getJSONObject("data");
//                        // Get the raw byte data array
//                        JSONArray dataArray = dataObj.getJSONArray("data");
//                        byte[] imageBytes = new byte[dataArray.length()];
//                        // Convert JSONArray to a byte array
//                        for (int j = 0; j < dataArray.length(); j++) {
//                            imageBytes[j] = (byte) dataArray.getInt(j);
//                        }
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                        HomeScreen.aImage.add(bitmap);
//
//
//
//                    }
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//
//
//        }
//
//    }

//    private class GetProfile extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            statusProfile = HttpHandler.getMyInfo(userEmail);
//            Log.d("Status: ", statusProfile);
//            return null;
//        }
//
//        protected void onPostExecute(Void avoid) {
//            if(!statusProfile.split(" ")[0].equalsIgnoreCase("Error")) {
//                try {
//                    // Parse the response as a JSON object
//                    JSONObject responseObj = new JSONObject(statusProfile);
//
//                    // Extract user and preference information
//                    JSONObject userObj = responseObj.getJSONObject("user");
//                    Log.d("User: ", String.valueOf(userObj));
//                    JSONObject preferenceObj = responseObj.getJSONObject("preference");
//                    Log.d("Preference: ", String.valueOf(preferenceObj));
//
//                    // Get data from user object
//                    String name = userObj.getString("name");
//                    String age = userObj.getString("age");
//                    String gender = userObj.getString("gender");
//                    String phone = userObj.getString("phone");
//                    String description = userObj.getString("description");
//
//                    // Get the "avatarImg" object from the user object
//                    JSONObject avatarImgObj = userObj.getJSONObject("avatarImg");
//
//                    // Get the "data" object
//                    JSONObject dataObj = avatarImgObj.getJSONObject("data");
//
//                    // Get the raw byte data array
//                    JSONArray dataArray = dataObj.getJSONArray("data");
//                    byte[] imageBytes = new byte[dataArray.length()];
//
//                    // Convert JSONArray to a byte array
//                    for (int i = 0; i < dataArray.length(); i++) {
//                        imageBytes[i] = (byte) dataArray.getInt(i);
//                    }
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                    // Get data from preference object
//                    String interest = preferenceObj.getString("interest");
//                    String program = preferenceObj.getString("program");
//                    String partner = preferenceObj.getString("partner");
//                    HttpHandler.curUser = new Users(name,age,description,interest,program,userEmail,gender,partner,phone,bitmap);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else{
//                HttpHandler.curUser = new Users("name","age","description","interest","program",userEmail,"gender","partner","phone",null);
//            }
//
//            Log.d("Status: ", HttpHandler.curUser.getName());
//
//
//        }
//
//    }
//
//    private class GetMatches extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            statusMatches = HttpHandler.getMatches(userEmail);
//            Log.d("Status: ", statusMatches);
//            return null;
//        }
//
//        protected void onPostExecute(Void avoid) {
//            if(!statusMatches.split(" ")[0].equalsIgnoreCase("Error")) {
//                try {
//                    JSONArray responseArr = new JSONArray(statusMatches);
//                    for (int i = 0; i < responseArr.length(); i++) {
//                        HttpHandler.participants.add(responseArr.getString(i));
//                    }
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//    }


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