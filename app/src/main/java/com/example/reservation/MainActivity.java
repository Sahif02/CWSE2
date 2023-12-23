package com.example.reservation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private User userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.fragment_container);

        // Receive user details from the intent
        if (getIntent() != null && getIntent().hasExtra("userDetails")) {
            userDetails = (User) getIntent().getSerializableExtra("userDetails");
        }

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_chat) {
                    loadFragment(new Chat(), false);
                } else if (itemId == R.id.navigation_reels) {
                    loadFragment(new Reels(), false);
                } else if (itemId == R.id.navigation_home) {
                    homepage fragment = new homepage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userDetails", userDetails);
                    fragment.setArguments(bundle);
                    loadFragment(fragment, false);
                } else if (itemId == R.id.navigation_history) {
                    loadFragment(new History(), false);
                } else {
                    Profiles fragment = new Profiles();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userDetails", userDetails);
                    fragment.setArguments(bundle);
                    loadFragment(fragment, false);
                }

                return true;
            }
        });

        homepage fragment = new homepage();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userDetails", userDetails);
        fragment.setArguments(bundle);
        loadFragment(fragment, true);
    }

    void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isAppInitialized) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commit();
    }

}
