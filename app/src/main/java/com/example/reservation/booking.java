package com.example.reservation;

import static android.app.ProgressDialog.show;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class booking extends Fragment {

    private User userDetails;

    private FrameLayout frameLayout;

    private Button chooseDateBtn;
    private String selectedDate;
    private int selectedPax;
    private String selectedTime;

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

        chooseDateBtn = view.findViewById(R.id.chooseDateBtn);

        chooseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionPopup();
            }
        });

        // Title
        TextView titleTextView = view.findViewById(R.id.toolbarTitle);
        titleTextView.setText("Booking");

        if (userDetails != null && selectedDate != null) {
            outgarden fragment = new outgarden();
            Bundle bundle = new Bundle();

            // Assign values to selectedDate, selectedTime, and selectedPax
            bundle.putString("selectedDate", selectedDate);
            bundle.putString("selectedTime", selectedTime);
            bundle.putInt("selectedPax", selectedPax);

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

    private void openSelectionPopup() {
        // Create an instance of the selection popup fragment
        View selectionPopupFragment = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_selection_popup, null);

        // Initialize the bottom slider dialog
        final BottomSheetDialog selectionPopup = new BottomSheetDialog(requireContext());
        selectionPopup.setContentView(selectionPopupFragment);

        // Set up the "Back" button click listener
        Button cancelBtn = selectionPopup.findViewById(R.id.cancelButton);
        if (cancelBtn != null) {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectionPopup.dismiss();
                }
            });
        }

        TextView paxTextView = selectionPopup.findViewById(R.id.paxEditText);
        Spinner timeSpinner = selectionPopup.findViewById(R.id.timeSpinner);

        Button nextBtn = selectionPopup.findViewById(R.id.nextButton);
        if (nextBtn != null) {
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve user input data
                    String paxInput = paxTextView.getText().toString();
                    String timeInput = timeSpinner.getSelectedItem().toString();

                    // Validate and process user input as needed
                    if (!paxInput.isEmpty()) {
                        selectedPax = Integer.parseInt(paxInput);
                        selectedTime = timeInput;

                        // Dismiss the current popup
                        selectionPopup.dismiss();

                        // Proceed to show date picker or any other action
                        showDatePicker();
                    } else {
                        // Handle empty input, show an error, or take appropriate action
                        Toast.makeText(requireContext(), "Please enter valid input", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Show the bottom slider
        selectionPopup.show();
    }

    private void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the selected date
                        selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;

                        // Now, you can use these values to create the outgarden fragment
                        if (userDetails != null) {
                            outgarden fragment = new outgarden();
                            Bundle bundle = new Bundle();

                            // Pass the selected date, time, and pax to the outgarden fragment
                            bundle.putString("selectedDate", selectedDate);
                            bundle.putString("selectedTime", selectedTime);
                            bundle.putInt("selectedPax", selectedPax);

                            bundle.putSerializable("userDetails", userDetails);
                            fragment.setArguments(bundle);
                            bookFragment(fragment, false);
                        }
                    }
                },
                year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }



}
