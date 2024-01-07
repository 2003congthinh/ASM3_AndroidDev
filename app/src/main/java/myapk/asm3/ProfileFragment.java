package myapk.asm3;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProfileFragment extends Fragment {
    private String jsonString = "";
    private String email = HttpHandler.loginEmail;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        text1 = view.findViewById(R.id.name);
        text2 = view.findViewById(R.id.age);
        text3 = view.findViewById(R.id.description);
        text4 = view.findViewById(R.id.interests);
        text5 = view.findViewById(R.id.program);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        new FetchProfile().execute();
    }

    private class FetchProfile extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = HttpHandler.getMyInfo(email);
            Log.d("Profile: ", jsonString);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            try {
                // Parse the response as a JSON object
                JSONObject responseObj = new JSONObject(jsonString);

                // Extract user and preference information
                JSONObject userObj = responseObj.getJSONObject("user");
                JSONObject preferenceObj = responseObj.getJSONObject("preference");

                // Get data from user object
                String name = userObj.getString("name");
                String age = userObj.getString("age");
                String description = userObj.getString("description");
                // Add similar lines for other user fields

                // Get data from preference object
                String interest = preferenceObj.getString("interest");
                String program = preferenceObj.getString("program");

                // Set text views with the extracted data
                text1.setText(name);
                text2.setText(age);
                text3.setText(description);
                text4.setText(interest);
                text5.setText(program);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}