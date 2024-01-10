package myapk.asm3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(OTP.OTP_CODE)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Object[] pdus = (Object[]) extras.get("pdus");

                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);

                        // Get the sender's phone number and the message body
                        String sender = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();

                        // Check if the sender's phone number matches the expected userPhone
                        if (sender != null && sender.equals(OTP.userPhone)) {
                            // Extract the OTP from the messageBody (assuming it's the last 3 digits)
                            String otp = messageBody.substring(messageBody.length() - 3);
                            Log.d("OTP", otp);

                            // Do something with the OTP (e.g., compare it with user input)
//                            OTP.handleReceivedOTP(otp);
                        }
                    }
                }
            }
        }
    }
}
