package myapk.asm3;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private String imgName;
    private String imgEmail;
    private String jsonString = "";
    private String email = HttpHandler.loginEmail;
    // Swipe functions
    private CustomImageAdapter imageAdapter;
    private ArrayList<Bitmap> al;
    private ArrayList<String> al2;
    private ArrayList<String> al3;
    private ArrayList<String> al4;
    private ArrayList<String> al5;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    SwipeFlingAdapterView flingContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Swipe function
//        al = new ArrayList<>();
//        al.add("php");
//        al.add("c");
//        al.add("python");
//        al.add("java");
//        al.add("html");
//        al.add("c++");
//        al.add("css");
//        al.add("javascript");
        al = new ArrayList<>();
        al2 = new ArrayList<>();
        al3 = new ArrayList<>();
        al4 = new ArrayList<>();
        al5 = new ArrayList<>();
//        for (int j = 1; j <= 10; j++) {
//            al.add("flower" + j);
//        }
//
////        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );
        imageAdapter = new CustomImageAdapter(requireContext(), R.layout.item, al, al2, al3, al4, al5);

        flingContainer = view.findViewById(R.id.frame);
//        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setAdapter(imageAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                imgEmail = al5.get(0);
                imgName = al2.get(0);
                al.remove(0);
                al2.remove(0);
                al3.remove(0);
                al4.remove(0);
                al5.remove(0);
//                arrayAdapter.notifyDataSetChanged();
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(requireContext(), "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                String name = al2.get(itemPosition);
//                String oemail = al5.get(itemPosition);
                Toast.makeText(requireContext(), "Name: " + imgName + ", " + "Email: " + imgEmail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Toast.makeText(requireContext(), "Out of image", Toast.LENGTH_SHORT).show();
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
                String name = al2.get(itemPosition);
                String oemail = al5.get(itemPosition);
                makeToast(requireContext(), "Clicked! Name: " + name + ", " + "Email: " + oemail);
            }
        });

        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        new HomeFragment.GetMates().execute();
    }

    private class GetMates extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = HttpHandler.getMates(email);
            Log.d("Profile: ", jsonString);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            try {
                al.clear();
                al2.clear();
                al3.clear();
                al4.clear();
                al5.clear();
                // Parse the response as a JSON object
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    al2.add(name);
                    String age = jsonObject.getString("age");
                    al3.add(age);
                    String description = jsonObject.getString("description");
                    al4.add(description);
                    String oemail = jsonObject.getString("email");
                    al5.add(oemail);
                    Log.d("Mates: ", String.valueOf(jsonObject));
                    Log.d("Mates_name: ", name);
                    // Get the "avatarImg" object from the user object
                    JSONObject avatarImgObj = jsonObject.getJSONObject("avatarImg");

                    // Get the "data" object
                    JSONObject dataObj = avatarImgObj.getJSONObject("data");

                    // Get the raw byte data array
                    JSONArray dataArray = dataObj.getJSONArray("data");
                    Log.d("Mates_pict: ", String.valueOf(dataArray));
                    byte[] imageBytes = new byte[dataArray.length()];

                    // Convert JSONArray to a byte array
                    for (int j = 0; j < dataArray.length(); j++) {
                        imageBytes[j] = (byte) dataArray.getInt(j);
                    }

                    // Decode the byte array into a Bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    al.add(bitmap);
                }
                // Set up the adapter with the updated data
                imageAdapter = new CustomImageAdapter(requireContext(), R.layout.item, al, al2, al3, al4, al5);
                flingContainer.setAdapter(imageAdapter);

                // Notify the adapter about the changes
                imageAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }



}