package myapk.asm3;
// Swipe function are from: https://github.com/Diolor/Swipecards.git
// Current location functions are from Week 5

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    // Bottom navigation
    private BottomNavigationView bottomNav;
//    private static String userEmail = HttpHandler.loginEmail;
    static String userEmail;

    private static String status, statusMates, statusProfile, statusMatches ="";
    private FrameLayout menu;
    private Fragment homeFragment;
    private Fragment chatFragment;
    private Fragment profileFragment;

    public static ArrayList<Bitmap> aImage;
    public static ArrayList<String> aName;
    public static ArrayList<String> aAge;
    public static ArrayList<String> aDescription;
    public static ArrayList<String> aEmail;

    public static ArrayList<String> participants;
    public static  Users curUser;

    private int tasksCompleted = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        HomeScreen.aImage = new ArrayList<>();
        HomeScreen.aName= new ArrayList<>();
        HomeScreen.aAge= new ArrayList<>();
        HomeScreen.aDescription= new ArrayList<>();
        HomeScreen.aEmail= new ArrayList<>();
        HomeScreen.participants= new ArrayList<>();
        HomeScreen.curUser = null;
        if(getIntent().getStringExtra("uemail") != null) {
            userEmail = getIntent().getStringExtra("uemail");
        }
        // Start the LocationUpdateService
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        stopService(serviceIntent);
//        Toast.makeText(getApplicationContext(),"Start", Toast.LENGTH_SHORT).show();
        new GetProfile().execute();
        new GetMatches().execute();
        new GetMates().execute();


        // Bottom nav
        bottomNav = findViewById(R.id.bottomNav);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(getApplicationContext(), "Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HomeScreen.aImage.clear();
        HomeScreen.aImage = null;
        HomeScreen.aName.clear();
        HomeScreen.aName = null;
        HomeScreen.aAge.clear();
        HomeScreen.aAge = null;
        HomeScreen.aDescription.clear();
        HomeScreen.aDescription = null;
        HomeScreen.aEmail= new ArrayList<>();
        HomeScreen.participants.clear();
        HomeScreen.participants = null;
        HomeScreen.curUser = null;
//        Toast.makeText(getApplicationContext(), "Stop", Toast.LENGTH_SHORT).show();
    }





    // Bottom nav handler
    private void switchFragment(Fragment fragment, String tag){
        FragmentManager manage = getSupportFragmentManager();
        FragmentTransaction transaction = manage.beginTransaction();
        Fragment searchfragment = manage.findFragmentByTag(tag);

//        // Hide all existing fragments
        for (Fragment frag : manage.getFragments()) {
            transaction.detach(frag);
        }

//         If fragment doesn't exist yet, create one
        if (searchfragment == null) {
            transaction.add(R.id.fragment, fragment, tag);
        }
        else {
            transaction.attach(searchfragment);
        }
        transaction.commit();
    }

    private void checkTasksCompletion() {
        tasksCompleted++;
        if (tasksCompleted == 3) {
            // All tasks are completed, execute the code after tasks
            Log.d("StatusFrom: ", String.valueOf(curUser));
            if (curUser != null) {
                switchFragment(new HomeFragment(), "Home");
                bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // Handle bottom navigation item clicks
                        int itemID = item.getItemId();
                        if (itemID == R.id.home) {
                            switchFragment(new HomeFragment(), "Home");
                        } else if (itemID == R.id.chat) {
                            switchFragment(new ChatFragment(), "Chat");
                        } else if (itemID == R.id.profile) {
                            switchFragment(new ProfileFragment(), "Profile");
                        }
                        return true;
                    }
                });
            }
        }
    }


    private class GetMates extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            statusMates = HttpHandler.getMates(userEmail);
            Log.d("Status: ", statusMates);
            Log.d("Status: ", String.valueOf(statusMates.split(" ")[0].equalsIgnoreCase("Error")));
            return null;
        }
        protected void onPostExecute(Void avoid) {
            if(!statusMates.split(" ")[0].equalsIgnoreCase("Error")) {
                try {
                    JSONArray jsonArray = new JSONArray(statusMates);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        aName.add(name);

                        String age = jsonObject.getString("age");
                        aAge.add(age);

                        String description = jsonObject.getString("description");
                        aDescription.add(description);

                        String email = jsonObject.getString("email");
                        aEmail.add(email);

                        // Get the "avatarImg" object from the user object
                        JSONObject avatarImgObj = jsonObject.getJSONObject("avatarImg");
                        // Get the "data" object
                        JSONObject dataObj = avatarImgObj.getJSONObject("data");
                        // Get the raw byte data array
                        JSONArray dataArray = dataObj.getJSONArray("data");
                        byte[] imageBytes = new byte[dataArray.length()];
                        // Convert JSONArray to a byte array
                        for (int j = 0; j < dataArray.length(); j++) {
                            imageBytes[j] = (byte) dataArray.getInt(j);
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        aImage.add(bitmap);


                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            checkTasksCompletion();
        }

    }
    private class GetProfile extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            statusProfile = HttpHandler.getMyInfo(userEmail);
            Log.d("Status: ", statusProfile);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            if(!statusProfile.split(" ")[0].equalsIgnoreCase("Error")) {
                try {
                    // Parse the response as a JSON object
                    JSONObject responseObj = new JSONObject(statusProfile);

                    // Extract user and preference information
                    JSONObject userObj = responseObj.getJSONObject("user");
                    Log.d("User: ", String.valueOf(userObj));
                    JSONObject preferenceObj = responseObj.getJSONObject("preference");
                    Log.d("Preference: ", String.valueOf(preferenceObj));

                    // Get data from user object
                    String name = userObj.getString("name");
                    String age = userObj.getString("age");
                    String gender = userObj.getString("gender");
                    String phone = userObj.getString("phone");
                    String description = userObj.getString("description");

                    // Get the "avatarImg" object from the user object
                    JSONObject avatarImgObj = userObj.getJSONObject("avatarImg");

                    // Get the "data" object
                    JSONObject dataObj = avatarImgObj.getJSONObject("data");

                    // Get the raw byte data array
                    JSONArray dataArray = dataObj.getJSONArray("data");
                    byte[] imageBytes = new byte[dataArray.length()];

                    // Convert JSONArray to a byte array
                    for (int i = 0; i < dataArray.length(); i++) {
                        imageBytes[i] = (byte) dataArray.getInt(i);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    // Get data from preference object
                    String interest = preferenceObj.getString("interest");
                    String program = preferenceObj.getString("program");
                    String partner = preferenceObj.getString("partner");
                    curUser = new Users(name,age,description,interest,program,userEmail,gender,partner,phone,bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else{
                curUser = new Users("name","age","description","interest","program",userEmail,"gender","partner","phone",null);
            }

            Log.d("Status: ", curUser.getName());
            checkTasksCompletion();


        }

    }

    private class GetMatches extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            statusMatches = HttpHandler.getMatches(userEmail);
            Log.d("Status: ", statusMatches);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            if(!statusMatches.split(" ")[0].equalsIgnoreCase("Error")) {
                try {
                    JSONArray responseArr = new JSONArray(statusMatches);
                    for (int i = 0; i < responseArr.length(); i++) {
                        participants.add(responseArr.getString(i));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            checkTasksCompletion();
        }

    }


}