package myapk.asm3;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private static String status ="";
    private String imgName;
    private String imgEmail;
    private String jsonString = "";
    private String email = HttpHandler.loginEmail;
    // Swipe functions
    private CustomImageAdapter imageAdapter;
    private ArrayList<Bitmap> aImage;
    private ArrayList<String> aName;
    private ArrayList<String> aDescription;
    private ArrayList<String> aAge;
    private ArrayList<String> aEmail;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    View view;
    SwipeFlingAdapterView flingContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
//        Toast.makeText(getContext(),"Create", Toast.LENGTH_SHORT).show();
        // Swipe function
        aImage = new ArrayList<>();
        aName = new ArrayList<>();

        aDescription = new ArrayList<>();
        aAge = new ArrayList<>();
        aEmail = new ArrayList<>();

        imageAdapter = new CustomImageAdapter(requireContext(), R.layout.item, aImage, aName, aAge, aDescription, aEmail);

        flingContainer = view.findViewById(R.id.frame);
//        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setAdapter(imageAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                imgEmail = aEmail.get(0);
                imgName = aName.get(0);
                aImage.remove(0);
                aName.remove(0);
                aAge.remove(0);
                aEmail.remove(0);
                aDescription.remove(0);

                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                Toast.makeText(requireContext(), "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                new PostMatches().execute();
//                Toast.makeText(requireContext(), "Name: " + imgName + ", " + "Email: " + imgEmail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//                Toast.makeText(requireContext(), "Out of image", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                String name = aName.get(itemPosition);
                String oemail = aEmail.get(itemPosition);
                makeToast(requireContext(), "Clicked! Name: " + name + ", " + "Email: " + oemail);
            }
        });

        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
//        new HomeFragment.GetMates().execute();
//        Toast.makeText(getContext(),"Resume", Toast.LENGTH_SHORT).show();
        // Set up the adapter with the updated data
        aImage = HomeScreen.aImage;
        aName = HomeScreen.aName;
        aAge = HomeScreen.aAge;
        aEmail = HomeScreen.aEmail;
        aDescription = HomeScreen.aDescription;
        if(aEmail.isEmpty() != true) {
            flingContainer.setVisibility(View.VISIBLE);
            imageAdapter = new CustomImageAdapter(requireContext(), R.layout.item, aImage, aName, aAge, aDescription, aEmail);
            flingContainer.setAdapter(imageAdapter);
            // Notify the adapter about the changes
            imageAdapter.notifyDataSetChanged();
        }else{
            flingContainer.setVisibility(View.GONE);
            TextView textView = view.findViewById(R.id.homeNoti);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private class GetMates extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = HttpHandler.getMates(email);
            Log.d("Profile: ", jsonString);
            Log.d("Profile: ", email);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            try {
                aImage.clear();
                aName.clear();
                aAge.clear();
                aDescription.clear();
                aEmail.clear();
                // Parse the response as a JSON object
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    aName.add(name);
                    String age = jsonObject.getString("age");
                    aAge.add(age);
                    String description = jsonObject.getString("description");
                    aDescription.add(description);
                    String oemail = jsonObject.getString("email");
                    aEmail.add(oemail);
                    Log.d("Mates: ", String.valueOf(jsonObject));
                    Log.d("Mates_name: ", name);
                    // Get the "avatarImg" object from the user object
                    JSONObject avatarImgObj = jsonObject.getJSONObject("avatarImg");

                    // Get the "data" object
                    JSONObject dataObj = avatarImgObj.getJSONObject("data");

                    // Get the raw byte data array
                    JSONArray dataArray = dataObj.getJSONArray("data");
//                    Log.d("Mates_pict: ", String.valueOf(dataArray));
                    byte[] imageBytes = new byte[dataArray.length()];

                    // Convert JSONArray to a byte array
                    for (int j = 0; j < dataArray.length(); j++) {
                        imageBytes[j] = (byte) dataArray.getInt(j);
                    }

//                    // Create a StringBuilder to concatenate the Base64 strings
//                    StringBuilder base64StringBuilder = new StringBuilder();
//
//                    // Concatenate Base64 strings from the JSONArray
//                    for (int j = 0; j < dataArray.length(); j++) {
//                        base64StringBuilder.append(dataArray.getString(j));
//                    }
//
//                    // Convert the Base64 string to a byte array
//                    byte[] imageBytes = Base64.decode(base64StringBuilder.toString(), Base64.DEFAULT);


                    // Decode the byte array into a Bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    aImage.add(bitmap);
                }
                // Set up the adapter with the updated data
                imageAdapter = new CustomImageAdapter(requireContext(), R.layout.item, aImage, aName, aAge, aDescription, aEmail);
                flingContainer.setAdapter(imageAdapter);

                // Notify the adapter about the changes
                imageAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private class PostMatches extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            status = HttpHandler.setMatches(email, imgEmail);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle post-execution tasks if needed
            // You can access the status returned by the postLocation method here
        }
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
}