package myapk.asm3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import android.util.Base64;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    private String jsonString = "";
//    private String email = HttpHandler.loginEmail;
    private ImageView imageView;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button updatePage = view.findViewById(R.id.updatePage);

        updatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), UpdatePage.class);
                startActivity(intent);
            }
        });

        Button quitPage = view.findViewById(R.id.logout);

        quitPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the hosting activity
                if (getActivity() != null) {
//                    Intent intent = new Intent(requireContext(), Login.class);
//                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        imageView = view.findViewById(R.id.avatar);
        text1 = view.findViewById(R.id.name);
        text2 = view.findViewById(R.id.age);
        text3 = view.findViewById(R.id.description);
        text4 = view.findViewById(R.id.interests);
        text5 = view.findViewById(R.id.program);
        text6 = view.findViewById(R.id.phone);
        text7 = view.findViewById(R.id.email);

        Users curU = HomeScreen.curUser;
        if(curU != null) {
            imageView.setImageBitmap(curU.getImage());
            text1.setText("Name: " + curU.getName());
            text2.setText("Age: " + curU.getAge());
            text3.setText("Description: " + curU.getDescription());
            text4.setText("Hobby: " + curU.getInterest());
            text5.setText("Program: " + curU.getProgram());
            text6.setText("Phone: " + curU.getPhone());
            text7.setText("Email: " + curU.getEmail());
        }
//        Toast.makeText(getContext(),"Create Frag", Toast.LENGTH_SHORT).show();


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
//        new FetchProfile().execute();
        // Set the ImageView with the decoded image

    }

//    private class FetchProfile extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            jsonString = HttpHandler.getMyInfo(email);
//            Log.d("Profile: ", jsonString);
//            return null;
//        }
//
//        protected void onPostExecute(Void avoid) {
//            try {
//                // Parse the response as a JSON object
//                JSONObject responseObj = new JSONObject(jsonString);
//
//                // Extract user and preference information
//                JSONObject userObj = responseObj.getJSONObject("user");
//                Log.d("User: ", String.valueOf(userObj));
//                JSONObject preferenceObj = responseObj.getJSONObject("preference");
//                Log.d("Preference: ", String.valueOf(preferenceObj));
//
//                // Get data from user object
//                String name = userObj.getString("name");
//                String age = userObj.getString("age");
//                String phone = userObj.getString("phone");
//                String description = userObj.getString("description");
//
//                // Get the "avatarImg" object from the user object
//                JSONObject avatarImgObj = userObj.getJSONObject("avatarImg");
//
//                // Get the "data" object
//                JSONObject dataObj = avatarImgObj.getJSONObject("data");
//
//                // Get the raw byte data array
//                JSONArray dataArray = dataObj.getJSONArray("data");
//                byte[] imageBytes = new byte[dataArray.length()];
//
//                // Convert JSONArray to a byte array
//                for (int i = 0; i < dataArray.length(); i++) {
//                    imageBytes[i] = (byte) dataArray.getInt(i);
//                }
////                StringBuilder base64StringBuilder = new StringBuilder();
////
////                // Concatenate Base64 strings from the JSONArray
////                for (int j = 0; j < dataArray.length(); j++) {
////                    base64StringBuilder.append(dataArray.getString(j));
////                }
////
////                // Convert the Base64 string to a byte array
////                byte[] imageBytes = Base64.decode(base64StringBuilder.toString(), Base64.DEFAULT);
//
//
//
//                // Decode the byte array into a Bitmap
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//
//                // Set the ImageView with the decoded image
//                imageView.setImageBitmap(bitmap);
//
//                // Get data from preference object
//                String interest = preferenceObj.getString("interest");
//                String program = preferenceObj.getString("program");
//
//                // Set text views with the extracted data
//                text1.setText("Name: " + name);
//                text2.setText("Age: " + age);
//                text3.setText("Description: " + description);
//                text4.setText("Hobby: " + interest);
//                text5.setText("Program: " + program);
//                text6.setText("Phone: " + phone);
//
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    }

}