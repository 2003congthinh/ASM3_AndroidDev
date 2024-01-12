package myapk.asm3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements MyMatchesRecyclerViewAdapter.ItemClickListener{
    private String jsonString = "";
    private String email = HttpHandler.loginEmail;
    private ArrayList<Users> mUsers ;
    private RecyclerView recyclerView;
    ArrayList<String> participants;
    ArrayList<String> messages;
    View view;
    MyMatchesRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewMatches);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        new GetProfile().execute();

    }

    @Override
    public void onItemClick(View view, int position) {
        String email = adapter.getItem(position);
        Intent intent = new Intent(requireContext(), ChatActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        Toast.makeText(requireContext(), email,Toast.LENGTH_SHORT).show();
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
                    JSONObject matchObj = responseArr.getJSONObject(i);
                    JSONArray participantsObj = matchObj.getJSONArray("participants");
                    String id = matchObj.getString("_id");
                    for (int j = 0; j < participantsObj.length(); j++) {
                        if(!participantsObj.getString(j).equalsIgnoreCase(HttpHandler.loginEmail)) {
                            participants.add(participantsObj.getString(j) +":" +id);
                        }
                    }
                }

                RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMatches);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new MyMatchesRecyclerViewAdapter(requireContext(), participants);
                adapter.setClickListener((MyMatchesRecyclerViewAdapter.ItemClickListener) requireContext());
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

}