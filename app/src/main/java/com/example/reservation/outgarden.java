package com.example.reservation;

import android.annotation.SuppressLint;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class outgarden extends Fragment {
    private User userDetails;

    private FrameLayout bookingContainer;

    public outgarden() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outgarden, container, false);

        // Receive user details from arguments
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");
        }

        // Button to load different fragments
        Button seabtn = view.findViewById(R.id.sea_btn);
        seabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load a different fragment when the button is clicked
                outsea fragment = new outsea();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userDetails", userDetails);
                fragment.setArguments(bundle);
                bookFragment(fragment, false);
            }
        });

        // Button to load different fragments
        Button ingarbtn = view.findViewById(R.id.ingar_btn);
        ingarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load a different fragment when the button is clicked
                ingarden fragment = new ingarden();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userDetails", userDetails);
                fragment.setArguments(bundle);
                bookFragment(fragment, false);
            }
        });

        Button go1Btn = view.findViewById(R.id.go1btn);
        go1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider();
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

    private void showBottomSlider() {
        // Inflate the bottom slider layout
        View bottomSliderView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_slider, null);

        // Set user details in bottom slider view
        TextView usernameTextView = bottomSliderView.findViewById(R.id.uname);
        TextView emailTextView = bottomSliderView.findViewById(R.id.u_email);
        TextView phoneTextView = bottomSliderView.findViewById(R.id.pnum);

        if (userDetails != null) {
            usernameTextView.setText(userDetails.getUsername());
            emailTextView.setText(userDetails.getEmail());
            phoneTextView.setText(userDetails.getPhone());
        }

        // Initialize the bottom slider dialog
        final BottomSheetDialog bottomSliderDialog = new BottomSheetDialog(requireContext());
        bottomSliderDialog.setContentView(bottomSliderView);

        // Set up the "Back" button click listener
        Button cancelBtn = bottomSliderDialog.findViewById(R.id.cancelBtn);
        if (cancelBtn != null) {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSliderDialog.dismiss();
                }
            });
        }

        // Show the bottom slider
        bottomSliderDialog.show();
    }


}
