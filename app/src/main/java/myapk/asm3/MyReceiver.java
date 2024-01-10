package myapk.asm3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;
import java.util.Random;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // Compare the action with the constant string
        if (Objects.equals(intent.getAction(), OTP.OTP_CODE)) {
            // Generate a random number between 101 and 999
            int otpNumber = new Random().nextInt((999 - 101) + 1) + 101;
            String otpString = String.valueOf(otpNumber);
            String OTP_NUMB = "Your OTP is: " + otpString;
            sendMessage(context, OTP_NUMB);
        } else {
            sendMessage(context, "Receive a broadcast");
        }
    }

    private void sendMessage(Context context, String message) {
        Intent newIntent = new Intent(context, SendMessageActivity.class);
        newIntent.putExtra("message", message);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("Message: ", message);
        context.startActivity(newIntent);
    }
}
