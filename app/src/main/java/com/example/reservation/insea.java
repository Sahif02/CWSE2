package com.example.reservation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class insea extends Fragment {

    private FrameLayout bookingContainer;

    public insea() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insea, container, false);

        // Button to load different fragments
        Button ingarbtn = view.findViewById(R.id.ingar_btn);
        ingarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load a different fragment when the button is clicked
                bookFragment(new ingarden(), true);
            }
        });

        // Button to load different fragments
        Button outseabtn = view.findViewById(R.id.outsea_btn);
        outseabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load a different fragment when the button is clicked
                bookFragment(new outsea(), true);
            }
        });

        return view;
    }


    private void bookFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.booking_container, fragment);
        fragmentTransaction.commit();
    }

}
