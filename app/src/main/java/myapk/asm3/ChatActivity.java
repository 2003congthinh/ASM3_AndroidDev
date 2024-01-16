package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {

    String uEmail = HomeScreen.curUser.getEmail();
    String email;
    FirebaseListAdapter<ChatMessage> adapter;
    ArrayList<ChatMessage> messages;
    DatabaseReference roomReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                email = (String) bundle.get("email");
            }
        }
        email = getIntent().getStringExtra("email");


//
        // Get the pair of users (replace these with the actual user IDs)

        if(email != null) {
            TextView textView = (TextView) findViewById(R.id.sender);
            textView.setText(email);
            // Generate a unique room key based on the pair of users
            String roomKey = getRoomKey( uEmail, email);

            // Create a DatabaseReference for the specific room

            roomReference = FirebaseDatabase.getInstance().getReference().child("chatRooms").child(roomKey).child("messages");
//            Toast.makeText(this, roomReference.toString(), Toast.LENGTH_SHORT).show();

            messages = new ArrayList<>();
            if(roomReference != null) {
//                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                displayChatMessages(roomReference);


                // Listen for new messages
                roomReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                        messages.add(chatMessage);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        // Handle changed messages if needed
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        // Handle removed messages if needed
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        // Handle moved messages if needed
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors if needed
                    }
                });


                Button sendBtn = findViewById(R.id.sendMessage);
                sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText input = findViewById(R.id.inputMessage);
                        // Read the input field and push a new instance
                        // of ChatMessage to the Firebase database
                        roomReference.push().setValue(new ChatMessage(input.getText().toString(), uEmail));
                        // Clear the input
                        input.setText("");
                    }
                });
            }

        }

        Button back = (Button) findViewById(R.id.quitMessage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getRoomKey(String user1, String user2) {
        ArrayList<String> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Collections.sort(userList);
        // Concatenate and encode the sorted user strings
        String encodedUsers = Base64.encodeToString(String.join("_", userList).getBytes(), Base64.NO_WRAP);
        return encodedUsers;
    }

    private void displayChatMessages(DatabaseReference roomReference) {
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, roomReference) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Your existing code to populate the view
                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}
