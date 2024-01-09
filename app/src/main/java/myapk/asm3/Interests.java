package myapk.asm3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Interests extends AppCompatActivity {
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
    private Spinner interests;
    private Spinner gender;
    private Spinner partner;
    private Spinner programs;
    private ImageView profilePict;
    private Uri imageUri;
    private File imageFile;
    private TextView profileText;
    private final int GALLERY_REQ_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);

        email = getIntent().getStringExtra("userEmail");
        password = getIntent().getStringExtra("userPassword");

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

        // Insert profile picture
        profilePict = findViewById(R.id.profilePict);
        profileText = findViewById(R.id.profileText);
        profilePict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profPict = new Intent(Intent.ACTION_PICK);
                profPict.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(profPict, GALLERY_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode==GALLERY_REQ_CODE) {
                imageUri = data.getData();
                try {
                    profilePict.setImageURI(imageUri);
                    profileText.setText("");

                    // Convert Uri to File
                    String imagePath = getImagePath(imageUri);
                    imageFile = new File(imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // Convert Uri to File path
    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);
        cursor.close();
        return imagePath;
    }
    public void Submit(View view){
        Log.d("SelectedInterest", selectedInterest);
        Log.d("SelectedGender", selectedGender);
        Log.d("SelectedPartner", selectedPartner);
        Log.d("SelectedPrograms", selectedPrograms);
        Log.d("Pict", String.valueOf(profilePict));
        TextView emailText = findViewById(R.id.userName);
        userName = emailText.getText().toString();
        TextView descriptionText = findViewById(R.id.userDescription);
        userDescription = descriptionText.getText().toString();
        TextView myAge = findViewById(R.id.userAge);
        userAge = Integer.parseInt(myAge.getText().toString());
        TextView myPhone = findViewById(R.id.userPhone);
        userPhone = Integer.parseInt(myPhone.getText().toString());
        Intent intent = new Intent(Interests.this, OTP.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("name", userName);
        intent.putExtra("description", userDescription);
        intent.putExtra("age", userAge);
        intent.putExtra("phone", userPhone);
        intent.putExtra("interest", selectedInterest);
        intent.putExtra("gender", selectedGender);
        intent.putExtra("partner", selectedPartner);
        intent.putExtra("program", selectedPrograms);
        intent.putExtra("pict", imageFile);
        startActivity(intent);
    }

}