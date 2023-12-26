package com.example.reservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
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

        return inflater.inflate(R.layout.fragment_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");
        }

        TextView usernameTextView = view.findViewById(R.id.user);
        if (userDetails != null) {
            usernameTextView.setText(userDetails.getUsername());
        }

        Button logoutButton = view.findViewById(R.id.button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginScreen();
            }
        });

        Switch notificationSwitch = view.findViewById(R.id.switch1);
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(requireContext(), login.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
