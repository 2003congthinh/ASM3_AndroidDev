package myapk.asm3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class OTP extends AppCompatActivity {
    // SMS
    private EditText otp1;
    protected MyReceiver myReceiver;
    protected IntentFilter intentFilter;
    public static final String OTP_CODE = "myapk.asm3.ACTION_OTP_CODE";


    private String status ="";
    private String email;
    private String password = "";
    private String userName;
    private String userDescription;
    private int userAge;
    public static String userPhone;
    private String selectedInterest;
    private String selectedGender;
    private String selectedPartner;
    private String selectedPrograms;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        otp1 = findViewById(R.id.otp1);

        // Create account info
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        userName = getIntent().getStringExtra("name");
        userDescription = getIntent().getStringExtra("description");
        userAge = getIntent().getIntExtra("age", 0);
//        String p = getIntent().getStringExtra("phone");
//        userPhone = "+84" + p;
        userPhone = getIntent().getStringExtra("phone");
        selectedInterest = getIntent().getStringExtra("interest");
        selectedGender = getIntent().getStringExtra("gender");
        selectedPartner = getIntent().getStringExtra("partner");
        selectedPrograms = getIntent().getStringExtra("program");
        imageUri = getIntent().getParcelableExtra("pict");

        TextView phone = findViewById(R.id.phone);
        phone.setText(userPhone);

        generateAndSendOTP();

        // Register the broadcast receiver
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter(OTP.OTP_CODE);
        registerReceiver(myReceiver, intentFilter);
    }
    private void generateAndSendOTP() {
        // Generate a random number between 101 and 999
        int otpNumber = new Random().nextInt((999 - 101) + 1) + 101;

        // Convert the generated number to a string
        String otpString = String.valueOf(otpNumber);

        // Concatenate the generated OTP with a message
        String otpMessage = "Your OTP is: " + otpString;

        // Send the OTP message via SMS
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(userPhone, null, otpMessage, null, null);
    }

    public void Update(View view){
//        new PostInterests().execute();
    }
    //    POST DATA
    private class PostInterests extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Multipart m1 = null;
            try {
                m1 = new Multipart();

                // Check for READ_EXTERNAL_STORAGE permission
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Get the file path from the Uri
                    String imagePath = getImagePath(imageUri);
                    if (imagePath != null) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            m1.addFilePart("image", imageFile);
                            m1.addFormField("userName", userName);
                            m1.addFormField("userName", userName);
                            Log.d("FileSend: ", "File: " + userName);
                            m1.addFormField("email", email);
                            m1.addFormField("password", password);
                            m1.addFormField("role", "user");
                            m1.addFormField("latitude", "10.2222");
                            m1.addFormField("longitude", "67.89989");
                            m1.addFormField("description", userDescription);
                            m1.addFormField("gender", selectedGender);
                            m1.addFormField("partner", selectedPartner);
                            m1.addFormField("interest", selectedInterest);
                            m1.addFormField("program", selectedPrograms);
                            m1.addFormField("status", "normal");
                            m1.addFormField("age", String.valueOf(userAge));
                            m1.addFormField("phone", String.valueOf(userPhone));
                            status = m1.finish();
                        } else {
                            Log.d("FileError", "Image file does not exist!");
                        }
                    } else {
                        Log.d("FileError", "Failed to get image path from URI.");
                    }
                } else {
                    // Handle the case when READ_EXTERNAL_STORAGE permission is not granted
                    Log.d("PermissionError", "READ_EXTERNAL_STORAGE permission not granted.");
                }
            } catch (IOException e) {
                Log.d("IOException", "Error creating Multipart: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        private String getImagePath(Uri uri) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String imagePath = cursor.getString(column_index);
                cursor.close();
                return imagePath;
            } else {
                return null; // Handle the case when the cursor is null or empty
            }
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            if(status.equals("")){
                Log.d("SelectedInterest", selectedInterest);
                Log.d("SelectedGender", selectedGender);
                Log.d("SelectedPartner", selectedPartner);
                Log.d("SelectedPrograms", selectedPrograms);
                Log.d("Pict", String.valueOf(imageUri));
                Intent intent = new Intent(OTP.this, HomeScreen.class);
                startActivity(intent);
            } else {
                Toast.makeText(OTP.this, "Something's wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}