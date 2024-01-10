package myapk.asm3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class MyReceiver extends BroadcastReceiver {
    private static final String HAPPY_TEXT = " Mom, I am happy" ;
    private static final String MISS_TEXT = "Mom I miss you";
    private static final String BATTERY_TEXT = "Hey Mom. Gonna out of battery soon.
    Bai";
    private int lastState = TelephonyManager.CALL_STATE_IDLE;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.HAPPY_BUTTON)){
            sendMessage(context, HAPPY_TEXT);
        }
        else if (intent.getAction().equals(MainActivity.MISS_BUTTON)){
            sendMessage(context, MISS_TEXT);
        }
        else if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            reportPhoneState(context, intent);
        }
        else
            sendMessage(context, "Receive a broadcast");
    }
    private void reportPhoneState(Context context, Intent intent){
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                && lastState == TelephonyManager.CALL_STATE_IDLE){
            lastState = TelephonyManager.CALL_STATE_RINGING;
            String phoneNumber =
                    intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            sendMessage(context, "Your child received a call from " + phoneNumber);
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)
                && lastState == TelephonyManager.CALL_STATE_IDLE){
            lastState = TelephonyManager.CALL_STATE_OFFHOOK;
            String phoneNumber =
                    intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            sendMessage(context, "Your child is calling to " + phoneNumber);
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            lastState = TelephonyManager.CALL_STATE_IDLE;
            String phoneNumber =
                    intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            sendMessage(context, "Your child just finished the call with " +
                    phoneNumber);
        }
    }
    private void sendMessage(Context context, String message){
        Intent newIntent = new Intent(context, SendMessageActivity.class);
        newIntent.putExtra("message", message);
        context.startActivity(newIntent);
    }
}
