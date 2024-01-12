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
        if (intent.getAction().equals("myapk.asm3")){
            Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();
            Log.d("OTP: ", "123");
        }
        throw new UnsupportedOperationException("Not yet implement");
    }
}
