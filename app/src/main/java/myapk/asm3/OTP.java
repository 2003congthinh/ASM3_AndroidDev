package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        // Create account info
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        userName = getIntent().getStringExtra("name");
        userDescription = getIntent().getStringExtra("description");
        userAge = Integer.parseInt(getIntent().getStringExtra("age"));
        userPhone = Integer.parseInt(getIntent().getStringExtra("phone"));
        selectedInterest = getIntent().getStringExtra("interest");
        selectedGender = getIntent().getStringExtra("gender");
        selectedPartner = getIntent().getStringExtra("partner");
        selectedPrograms = getIntent().getStringExtra("program");
    }
    public void Update(){
        new PostInterests().execute();
    }
    //    POST DATA
    private class PostInterests extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            status = HttpHandler.postInterests(email,userPhone,userName,password,userDescription,userAge,selectedInterest,selectedGender,selectedPartner,selectedPrograms);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Interest", "Status: " + status);
            if(status.equals("Success: OK")){
                Toast.makeText(OTP.this, status, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OTP.this, HomeScreen.class);
                startActivity(intent);
            } else {
                Toast.makeText(OTP.this, "Something's wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}