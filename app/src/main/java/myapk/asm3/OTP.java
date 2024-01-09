package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

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
            status = HttpHandler.postInterests(email,userPhone,userName,password,userDescription,userAge,selectedInterest,selectedGender,selectedPartner,selectedPrograms, String.valueOf(imageUri));
            return null;
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

        private String getImagePath(Uri uri) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(column_index);
            cursor.close();
            return imagePath;
        }
    }
}