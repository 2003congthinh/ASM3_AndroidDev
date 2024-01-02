package myapk.asm3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PersonalInfo extends AppCompatActivity {
    // Bottom navigation
    private BottomNavigationView bottomNav;
    private FrameLayout menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        // Bottom nav
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.home){
                    Intent intent = new Intent(PersonalInfo.this, HomeScreen.class);
                    startActivity(intent);
                } else if (itemID == R.id.chat) {
                    Toast.makeText(PersonalInfo.this, "Not exist yet", Toast.LENGTH_SHORT).show();
                } else if (itemID == R.id.profile) {
                    Toast.makeText(PersonalInfo.this, "Already at Profile", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}