package myapk.asm3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private String jsonString = "";
    private String email = HttpHandler.loginEmail;
//    private ArrayList<Users> mUsers ;
    private RecyclerView recyclerView;
    ArrayList<String> participants;
    ArrayList<String> messages;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_chat, container, false);
//        Toast.makeText(getContext(),"Create", Toast.LENGTH_SHORT).show();

        participants = HomeScreen.participants;

        ScrollView scrollView = view.findViewById(R.id.matches);
        scrollView.removeAllViews();

        LinearLayout.LayoutParams paramsForText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsForText.setMargins(50, 10, 50, 10);
        ViewGroup.LayoutParams paramsForLin = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayoutWrapper = new LinearLayout(requireContext());
        linearLayoutWrapper.setLayoutParams(paramsForLin);
        linearLayoutWrapper.setOrientation(LinearLayout.VERTICAL);

        if (participants.isEmpty() == true) {
            TextView textView = new TextView(requireContext());
            textView.setLayoutParams(paramsForText);
            textView.setText("Sorry \n No Available \n Matches");
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            scrollView.addView(textView);
        } else {
            draw(participants, linearLayoutWrapper, paramsForText);
            scrollView.addView(linearLayoutWrapper);
        }

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
//        new GetProfile().execute();
//        participants = new ArrayList<>();
//        participants.add("Hoang:999999");
//        participants.add("Hoan:888888");
//        participants.add("Hoa:7777777");


//
//     Toast.makeText(getContext(),"Resume", Toast.LENGTH_SHORT).show();
    }

    public void draw(ArrayList<String> matches, LinearLayout linearLayout, LinearLayout.LayoutParams params){
        for (int i = 0; i < matches.size(); i++) {
            String email = matches.get(i);

            TextView textView = new TextView(requireContext());
            textView.setText(email);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            textView.setLayoutParams(params);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(requireContext(), ChatActivity.class);
                    intent.putExtra("email", email);
//                    Toast.makeText(requireContext(), intent.toString() + email, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
            textView.setTextColor(Color.parseColor("#000000"));
//            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            textView.setTypeface(Typeface.DEFAULT_BOLD);


            LinearLayout textRow = new LinearLayout(requireContext());
            textRow.setLayoutParams(params);
            textRow.setOrientation(LinearLayout.HORIZONTAL);
            textRow.addView(textView);
            textRow.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_background));

            linearLayout.addView(textRow);
        }
    }






    private class GetProfile extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = HttpHandler.getMatches(email);
            Log.d("Matches: ", jsonString);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            try {
                // Parse the response as a JSON object
                participants = new ArrayList<>();
                JSONArray responseArr = new JSONArray(jsonString);
                for (int i = 0; i < responseArr.length(); i++) {
                    participants.add(responseArr.getString(i));
                }

                ScrollView scrollView = view.findViewById(R.id.matches);
                scrollView.removeAllViews();

                LinearLayout.LayoutParams paramsForText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsForText.setMargins(50, 10, 50, 10);
                ViewGroup.LayoutParams paramsForLin = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                LinearLayout linearLayoutWrapper = new LinearLayout(requireContext());
                linearLayoutWrapper.setLayoutParams(paramsForLin);
                linearLayoutWrapper.setOrientation(LinearLayout.VERTICAL);

                if (participants.isEmpty() == true) {
                    TextView textView = new TextView(requireContext());
                    textView.setLayoutParams(paramsForText);
                    textView.setText("Sorry \n No Available \n Matches");
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    scrollView.addView(textView);
                } else {
                    draw(participants, linearLayoutWrapper, paramsForText);
                    scrollView.addView(linearLayoutWrapper);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

}