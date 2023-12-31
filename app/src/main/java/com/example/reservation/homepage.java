package com.example.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class homepage extends Fragment {

    public homepage() {
        // Required empty public constructor
    }

    private User userDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        // Retrieve user details from arguments
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");
        }

        Button bookNowButton = view.findViewById(R.id.bookingbtn);
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the code to navigate to the booking fragment
                booking fragment = new booking();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userDetails", userDetails);
                fragment.setArguments(bundle);
                ((MainActivity) requireActivity()).loadFragment(fragment, false);
            }
        });

        return view;
    }
}
