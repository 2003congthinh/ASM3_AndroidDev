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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class OTP extends AppCompatActivity {
    // SMS
    private String otpString;
    public static String otpMessage;
    private TextInputEditText otp1;
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

        if(getIntent() != null) {
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

            generateOTP();

            TextView phone = findViewById(R.id.phone);
            phone.setText(userPhone);

        registerService();
        Intent broadcastIntent = new Intent("myapk.asm3.ACTION_OTP_CODE");
        sendBroadcast(broadcastIntent);
        }
    }

    @Override
    protected void onResume() {
        Button send = findViewById(R.id.sendSubmit);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otp1.getText().toString();
                if(Objects.equals(otpString, code)){
                    new PostInterests().execute();
                } else {
                    Toast.makeText(OTP.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        super.onResume();
    }

    private void registerService(){
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter();

        intentFilter.addAction("myapk.asm3.ACTION_OTP_CODE");

        registerReceiver(myReceiver, intentFilter);
    }

    private void generateOTP() {
        // Generate a random number between 101 and 999
        int otpNumber = new Random().nextInt((999 - 101) + 1) + 101;

        // Convert the generated number to a string
        otpString = String.valueOf(otpNumber);

        // Concatenate the generated OTP with a message
        otpMessage = "Your OTP is: " + otpString;
        Toast.makeText(this,otpMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        Intent intent = new Intent(OTP.this, MyService.class);
//        stopService(intent);
    }



//    public void Update(View view){
//        String code = otp1.getText().toString();
//        if(Objects.equals(otpString, code)){
//            new PostInterests().execute();
//        } else {
//            Toast.makeText(OTP.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
//        }
//    }
    //    POST DATA
    private class PostInterests extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Multipart m1 = new Multipart("https://fair-lime-crocodile.cyclic.app");
//                Toast.makeText(getApplicationContext(),"before",Toast.LENGTH_SHORT).show();
                // Check for READ_EXTERNAL_STORAGE permission
                Log.d("File: ", "before");
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(),"after",Toast.LENGTH_SHORT).show();
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
////                    Log.d("File: ", String.valueOf(imageFile));
                            Log.d("File: ", String.valueOf(userName));
//                            Log.d("File: ", String.valueOf(m1));
                            m1.finish();
//                            Toast.makeText(getApplicationContext(),"afterAll"+m1.finish(),Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("FileError", "Image file does not exist!");
                        }

                    } else {
                        Log.d("FileError", "Failed to get image path from URI.");
                    }

//                    m1.addFormField("userName", userName);
//                    m1.addFormField("userName", userName);
//                    Log.d("FileSend: ", "File: " + userName);
//                    m1.addFormField("email", email);
//                    m1.addFormField("password", password);
//                    m1.addFormField("role", "user");
//                    m1.addFormField("latitude", "10.2222");
//                    m1.addFormField("longitude", "67.89989");
//                    m1.addFormField("description", userDescription);
//                    m1.addFormField("gender", selectedGender);
//                    m1.addFormField("partner", selectedPartner);
//                    m1.addFormField("interest", selectedInterest);
//                    m1.addFormField("program", selectedPrograms);
//                    m1.addFormField("status", "normal");
//                    m1.addFormField("age", String.valueOf(userAge));
//                    m1.addFormField("phone", String.valueOf(userPhone));
//////                    Log.d("File: ", String.valueOf(imageFile));
////                            Log.d("File: ", String.valueOf(userName));
////                            Log.d("File: ", String.valueOf(m1));
//                    m1.finish();


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
                Toast.makeText(getApplicationContext(),"Successfully Created Account", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OTP.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(OTP.this, "Something's wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}