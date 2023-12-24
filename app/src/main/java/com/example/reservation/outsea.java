package com.example.reservation;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class outsea extends Fragment {
    private User userDetails;
    private int selectedPax;
    private String selectedDate;
    private String selectedTime;

    private FrameLayout bookingContainer;

    public outsea() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outsea, container, false);

        // Receive user details from arguments
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");

            selectedPax = getArguments().getInt("selectedPax", 0);
            selectedDate = getArguments().getString("selectedDate", "err");
            selectedTime = getArguments().getString("selectedTime", "err");
        }

        // Button to load different fragments
        Button outgarbtn = view.findViewById(R.id.outgar_btn);
        outgarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        // Button to load different fragments
        Button inseabtn = view.findViewById(R.id.insea_btn);
        inseabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDetails != null && selectedDate != null) {
                    insea fragment = new insea();
                    Bundle bundle = new Bundle();

                    // Assign values to selectedDate, selectedTime, and selectedPax
                    bundle.putString("selectedDate", selectedDate);
                    bundle.putString("selectedTime", selectedTime);
                    bundle.putInt("selectedPax", selectedPax);

                    bundle.putSerializable("userDetails", userDetails);
                    fragment.setArguments(bundle);
                    bookFragment(fragment, false);
                }
            }
        });

        Button so1Btn = view.findViewById(R.id.so1btn);
        so1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider("SO-1");
            }
        });

        Button so2Btn = view.findViewById(R.id.so2btn);
        so2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider("SO-2");
            }
        });

        Button so3Btn = view.findViewById(R.id.so3btn);
        so3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider("SO-3");
            }
        });

        Button so4Btn = view.findViewById(R.id.so4btn);
        so4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider("SO-4");
            }
        });

        Button so5Btn = view.findViewById(R.id.so5btn);
        so5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider("SO-5");
            }
        });

        Button so6Btn = view.findViewById(R.id.so6btn);
        so6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSlider("SO-6");
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

    private void showBottomSlider(String Button) {
        // Inflate the bottom slider layout
        View bottomSliderView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_slider, null);

        // Set user details in bottom slider view
        TextView usernameTextView = bottomSliderView.findViewById(R.id.uname);
        TextView emailTextView = bottomSliderView.findViewById(R.id.u_email);
        TextView phoneTextView = bottomSliderView.findViewById(R.id.pnum);

        TextView location = bottomSliderView.findViewById(R.id.location);
        TextView date = bottomSliderView.findViewById(R.id.date);
        TextView time = bottomSliderView.findViewById(R.id.time);
        TextView pax = bottomSliderView.findViewById(R.id.pax);

        if (userDetails != null) {
            usernameTextView.setText(userDetails.getUsername());
            emailTextView.setText(userDetails.getEmail());
            phoneTextView.setText(userDetails.getPhone());

            location.setText("Sea: Outside("+ Button +")");
            pax.setText(String.valueOf(selectedPax));
            date.setText(selectedDate);
            time.setText(selectedTime);
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

        Button confirmBtn = bottomSliderDialog.findViewById(R.id.confirmBtn);
        if (confirmBtn != null) {
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a Reservation object with the data
                    Reservation reservation = new Reservation();
                    reservation.setCustomerName(userDetails.getUsername());
                    reservation.setCustomerPhoneNumber(userDetails.getPhone());
                    reservation.setSeatingArea("Sea: Outside("+ Button +")");
                    reservation.setDate(selectedDate);
                    reservation.setMeal(selectedTime);
                    reservation.setTableSize(selectedPax);

                    // Make the API call
                    sendReservationToApi(reservation);

                    // Dismiss the bottom slider dialog
                    bottomSliderDialog.dismiss();
                }
            });
        }

        // Show the bottom slider
        bottomSliderDialog.show();
    }

    private void sendReservationToApi(Reservation reservation) {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a ReservationService instance
        ReservationService reservationService = retrofit.create(ReservationService.class);

        // Make the API call asynchronously
        Call<Void> call = reservationService.createReservation(reservation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(requireContext(), "Reservation successful", Toast.LENGTH_SHORT).show();
                    navigateToReceipt(reservation, userDetails);
                } else {
                    // Handle error response
                    Toast.makeText(requireContext(), "Failed to make reservation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(requireContext(), "Failed to connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToReceipt(Reservation reservation, User userDetails) {
        // Create Intent for ReceiptActivity and pass reservation data
        Intent intent = new Intent(requireContext(), receipt.class);
        intent.putExtra("reservation", reservation);
        intent.putExtra("userDetails", userDetails);

        // Start ReceiptActivity
        startActivity(intent);
    }

}
