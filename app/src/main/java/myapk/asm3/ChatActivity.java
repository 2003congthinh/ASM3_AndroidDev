package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity implements MyMatchesRecyclerViewAdapter.ItemClickListener{

    String data;
    String email,id;
    String uEmail = HttpHandler.loginEmail;
    String jsonString;
    ArrayList<String> messages;
    MyMatchesRecyclerViewAdapter adapter;
    Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Create account info
        data = getIntent().getStringExtra("email");
        String[] dtas = data.split(":");
        email = dtas[0];
        id = dtas[1];


        try {
            System.out.println("Before creating socket");
            socket = IO.socket("https://asm3android-a0efc67bf4a3.herokuapp.com/");
            System.out.println("After creating socket");
            System.out.println(socket.connect());

            socket.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected");
                }

            });
            socket.on("disconnect", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected");
                }

            });

            socket.emit("joinMatchRoom", id);



        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    @Override
    public void onItemClick(View view, int position) {

    }


    private class GetMatch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = HttpHandler.getMatch(email, uEmail);
            Log.d("Match: ", jsonString);
            return null;
        }

        protected void onPostExecute(Void avoid) {
            try {
                // Parse the response as a JSON object
                messages = new ArrayList<>();
                JSONObject responseArr = new JSONObject(jsonString);

                JSONArray conversationObj = responseArr.getJSONArray("conversation");
                for (int i = 0; i < conversationObj.length(); i++) {
                   JSONObject messageObj = conversationObj.getJSONObject(i);
                   String sender = messageObj.getString("sender");
                   String message = messageObj.getString("message");
                   messages.add(sender + " : " + message);
                }

                RecyclerView recyclerView = findViewById(R.id.messageListView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new MyMatchesRecyclerViewAdapter(getApplicationContext(), messages);
                adapter.setClickListener((MyMatchesRecyclerViewAdapter.ItemClickListener) getApplicationContext());
                recyclerView.setAdapter(adapter);


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void sendMessage(View view) {
        TextView message = findViewById(R.id.messageInputField);
        String messageData = (String) message.getText();
        messages.add(email + " : " + messageData);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sender", email);
            jsonObject.put("message", messageData);
            jsonObject.put("matchId", id);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket.emit("sendMessage", jsonObject);
        socket.on("messageReceived", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // JSONObject userJsonObject = new JSONObject(args[0]);
                // System.out.println(args[0]);
                try {
                    JSONObject userJsonObject = new JSONObject(args[0].toString());
                    // System.out.println(args[0]);
                    String sender = userJsonObject.getString("sender");
                    String message = userJsonObject.getString("message");
                    messages.add(sender + " : " + message);
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    public void quitMessage(View view) {
        finish();
    }
}