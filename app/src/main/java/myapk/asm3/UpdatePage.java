package myapk.asm3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePage extends AppCompatActivity {
    private String status ="";
    private String email = HttpHandler.loginEmail;
    private String userName;
    private String userDescription;
    private int userAge;
    private String userPhone;
    private String selectedInterest;
    private String selectedGender;
    private String selectedPartner;
    private String selectedPrograms;
    private Spinner interests;
    private Spinner gender;
    private Spinner partner;
    private Spinner programs;
    private ImageView profilePict;
    private Uri imageUri;
    private TextView profileText;
    private final int GALLERY_REQ_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_page);

        // Get references to filter spinners
        // Interest spinner
        interests = findViewById(R.id.interest);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.interest,
                android.R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interests.setAdapter(adapter1);

        // Gender spinner
        gender = findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.gender,
                android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter2);

        // Partner spinner
        partner = findViewById(R.id.interested_partner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.partner,
                android.R.layout.simple_spinner_item
        );
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partner.setAdapter(adapter3);

        // Program spinner
        programs = findViewById(R.id.program);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this,
                R.array.programs,
                android.R.layout.simple_spinner_item
        );
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programs.setAdapter(adapter4);

        // Set up the listeners for the spinners
        // Interest spinner
        interests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedInterest = interests.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        // Gender spinner
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = gender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        // Partner spinner
        partner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPartner = partner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        // Programs spinner
        programs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrograms = programs.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        Button back = (Button) findViewById(R.id.quitUpdate);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Submit(View view){
        Log.d("SelectedInterest", selectedInterest);
        Log.d("SelectedGender", selectedGender);
        Log.d("SelectedPartner", selectedPartner);
        Log.d("SelectedPrograms", selectedPrograms);
        Log.d("Pict", String.valueOf(imageUri));
        TextView emailText = findViewById(R.id.userName);
        userName = emailText.getText().toString();
        TextView descriptionText = findViewById(R.id.userDescription);
        userDescription = descriptionText.getText().toString();
        TextView myAge = findViewById(R.id.userAge);
        userAge = Integer.parseInt(myAge.getText().toString());
        TextView myPhone = findViewById(R.id.userPhone);
        userPhone = myPhone.getText().toString();
        new PostProfile().execute();
    }

    private class PostProfile extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            status = HttpHandler.updateProfile(email,userPhone,userName,userDescription,selectedGender,selectedPrograms,selectedInterest,userAge,selectedPartner);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Update", "Status: " + status);
            if(status.equals("Success: OK")){
                Toast.makeText(UpdatePage.this, status, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdatePage.this, HomeScreen.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(UpdatePage.this, "Something's wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}