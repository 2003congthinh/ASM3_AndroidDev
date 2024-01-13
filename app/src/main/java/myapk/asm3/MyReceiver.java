package myapk.asm3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class MyReceiver extends BroadcastReceiver {
    private String otpMessage = OTP.otpMessage;
    private String userPhone = OTP.userPhone;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("myapk.asm3.ACTION_OTP_CODE")){
            generateAndSendOTP();
            Log.d("OTP: ", "123");
        }
    }
    private void generateAndSendOTP() {
        // Send the OTP message via SMS
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(userPhone, null, otpMessage, null, null);

//        registerService(otpString);
    }
}
