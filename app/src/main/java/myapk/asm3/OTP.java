package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class OTP extends AppCompatActivity {
    private String status ="";
    private String email;
    private String password = "";
    private String userName;
    private String userDescription;
    private int userAge;
    private int userPhone;
    private String selectedInterest;
    private String selectedGender;
    private String selectedPartner;
    private String selectedPrograms;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        // Create account info
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        userName = getIntent().getStringExtra("name");
        userDescription = getIntent().getStringExtra("description");
        userAge = getIntent().getIntExtra("age", 0);
        userPhone = getIntent().getIntExtra("phone", 0);
        selectedInterest = getIntent().getStringExtra("interest");
        selectedGender = getIntent().getStringExtra("gender");
        selectedPartner = getIntent().getStringExtra("partner");
        selectedPrograms = getIntent().getStringExtra("program");
        imageUri = getIntent().getParcelableExtra("pict");

    }
    public void Update(View view){
        new PostInterests().execute();
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
                            m1.addFormField("name", userName);
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
                            m1.finish();
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
            Log.d("Interest", "Status: " + status);
            if(status.equals("Success: OK")){
                Log.d("SelectedInterest", selectedInterest);
                Log.d("SelectedGender", selectedGender);
                Log.d("SelectedPartner", selectedPartner);
                Log.d("SelectedPrograms", selectedPrograms);
                Log.d("Pict", String.valueOf(imageUri));
                Toast.makeText(OTP.this, status, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OTP.this, HomeScreen.class);
                startActivity(intent);
            } else {
                Toast.makeText(OTP.this, "Something's wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}