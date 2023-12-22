package com.example.reservation;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class booking extends Fragment {

    private User userDetails;

    private FrameLayout frameLayout;

    public booking() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        // Retrieve user details from arguments
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");
        }

        // Top Toolbar Setup
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        // Back Button
        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check the current fragment and navigate accordingly
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                int fragmentCount = fragmentManager.getBackStackEntryCount();

                if (fragmentCount > 0) {
                    // If there are fragments in the back stack, pop back
                    fragmentManager.popBackStack();
                } else {
                    // If no fragments in the back stack, go back to the home fragment
                    homepage fragment = new homepage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userDetails", userDetails);
                    fragment.setArguments(bundle);
                    loadFragment(fragment, false);
                }
            }
        });

        // Title
        TextView titleTextView = view.findViewById(R.id.toolbarTitle);
        titleTextView.setText("Booking");

        if (userDetails != null) {
            outgarden fragment = new outgarden();
            Bundle bundle = new Bundle();
            bundle.putSerializable("userDetails", userDetails);
            fragment.setArguments(bundle);
            bookFragment(fragment, false);
        }

        return view;
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
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
