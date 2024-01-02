package myapk.asm3;
// Swipe function are from: https://github.com/Diolor/Swipecards.git
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    // Swipe functions
    private CustomImageAdapter imageAdapter;
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    SwipeFlingAdapterView flingContainer;

    // Bottom navigation
    private BottomNavigationView bottomNav;
    private FrameLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Bottom nav
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.home){
                    Toast.makeText(HomeScreen.this, "Already at Home", Toast.LENGTH_SHORT).show();
                } else if (itemID == R.id.chat) {
                    Toast.makeText(HomeScreen.this, "Not exist yet", Toast.LENGTH_SHORT).show();
                } else if (itemID == R.id.profile) {
                    Intent intent = new Intent(HomeScreen.this, PersonalInfo.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        // Swipe function
//        al = new ArrayList<>();
//        al.add("php");
//        al.add("c");
//        al.add("python");
//        al.add("java");
//        al.add("html");
//        al.add("c++");
//        al.add("css");
//        al.add("javascript");
        al = new ArrayList<>();
        for (int j = 1; j <= 10; j++) {
            al.add("flower" + j);
        }

//        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );
        imageAdapter = new CustomImageAdapter(this, R.layout.item, al);

        flingContainer = findViewById(R.id.frame);
//        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setAdapter(imageAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
//                arrayAdapter.notifyDataSetChanged();
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(HomeScreen.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(HomeScreen.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
                Toast.makeText(HomeScreen.this, "Out of image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(HomeScreen.this, "Clicked!");
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


//    @OnClick(R.id.right)
//    public void right() {
//        /**
//         * Trigger the right event manually.
//         */
//        flingContainer.getTopCardListener().selectRight();
//    }
//
//    @OnClick(R.id.left)
//    public void left() {
//        flingContainer.getTopCardListener().selectLeft();
//    }
}