package myapk.asm3;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    private String userEmail="";
    private String userPassword="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }


    public void goToInterest(View view) {
        TextView emailText = findViewById(R.id.email);
        userEmail = emailText.getText().toString();
        TextView passwordText = findViewById(R.id.password);
        userPassword = passwordText.getText().toString();
        Intent intent = new Intent(Signup.this, Interests.class);
        intent.putExtra("userEmail", userEmail);
        intent.putExtra("userPassword", userPassword);
        startActivity(intent);
        finish();
    }

}