package com.example.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Profiles extends Fragment {
    private User userDetails;

    private TextView userNameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Receive user details from arguments
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");
        }

        // Display username in the TextView
        TextView usernameTextView = view.findViewById(R.id.user);
        if (userDetails != null) {
            usernameTextView.setText(userDetails.getUsername());
        }

        // Other UI components and logic can be added here
        // For example, handling a logout button
        Button logoutButton = view.findViewById(R.id.button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your logout logic here
            }
        });

        // Other UI components like Switch can be initialized and handled here
        Switch notificationSwitch = view.findViewById(R.id.switch1);
        // Add any necessary listeners or logic for the Switch

        // Set up other UI components as needed
    }
}
