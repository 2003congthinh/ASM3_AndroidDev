package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

public class SendMessageActivity extends AppCompatActivity {
    private String phone = "+84961460419";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        sendMessage();
    }
    private void sendMessage() {
        //get message detail
        Intent incomingIntent = getIntent();
        String text = (String) incomingIntent.getExtras().get("message");
        //sending Sms Message
        SmsManager smsManager = SmsManager.getDefault();
        Log.d("Phone: ", phone);
        smsManager.sendTextMessage(phone, null, text, null, null);
        Log.d("Message: ", text);
        // Go back to main activity
        Intent intent = new Intent(SendMessageActivity.this, OTP.class);
        setResult(100, intent);
        finish();
    }
}