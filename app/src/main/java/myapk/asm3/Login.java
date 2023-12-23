package myapk.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private String userEmail="";
    private String userPassword="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //hehe
    }
    public void goToHome(View view) {
        TextView emailText = findViewById(R.id.email);
        userEmail = emailText.getText().toString();
        TextView passwordText = findViewById(R.id.password);
        userPassword = passwordText.getText().toString();
//        new PostAccount().execute();
    }

    public void goToSignup(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
    }
}