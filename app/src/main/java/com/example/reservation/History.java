package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends Fragment implements HistoryAdapter.OnItemClickListener {
    private User userDetails;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Receive user details from arguments
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable("userDetails");
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Make API call and populate the RecyclerView
        fetchHistoryData();

        return view;
    }

    private void fetchHistoryData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReservationService apiService = retrofit.create(ReservationService.class);
        Call<List<Reservation>> call = apiService.getReservation();

        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> historyItems = response.body();
                    // Populate the RecyclerView with history items
                    historyAdapter = new HistoryAdapter(historyItems, History.this);
                    recyclerView.setAdapter(historyAdapter);
                } else {
                    // Handle error response
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public void onItemClick(Reservation historyItem) {
        // Handle item click, for example, open bottom sheet with reservation details
        showBottomSheet(historyItem);
    }

    private void showBottomSheet(Reservation historyItem) {
        // Create a BottomSheetDialog and set the layout
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_receipt, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Get TextViews and Buttons from the layout
        TextView customerNameTextView = bottomSheetView.findViewById(R.id.customerNameTextView);
        TextView seatingAreaTextView = bottomSheetView.findViewById(R.id.seatingAreaTextView);
        TextView dateTextView = bottomSheetView.findViewById(R.id.dateTextView);
        TextView mealTimeTextView = bottomSheetView.findViewById(R.id.mealTimeTextView);
        TextView tableSizeTextView = bottomSheetView.findViewById(R.id.tableSizeTextView);
        Button updateButton = bottomSheetView.findViewById(R.id.updateButton);
        Button cancelButton = bottomSheetView.findViewById(R.id.cancelButton);

        // Set text for TextViews
        customerNameTextView.setText("Name: " + historyItem.getCustomerName());
        seatingAreaTextView.setText("Seating Area: " + historyItem.getSeatingArea());
        dateTextView.setText("Date: " + historyItem.getDate());
        mealTimeTextView.setText("Meal Time: " + historyItem.getMeal());
        tableSizeTextView.setText("Table Size: " + historyItem.getTableSize());

        // Set click listener for updateButton
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click, navigate to UpdateReservationActivity
                navigateToUpdateActivity(historyItem);
                bottomSheetDialog.dismiss(); // Dismiss the bottom sheet
            }
        });

        // Set click listener for cancelButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the bottom sheet
                bottomSheetDialog.dismiss();
            }
        });

        // Show the bottom sheet
        bottomSheetDialog.show();
    }

    private void navigateToUpdateActivity(Reservation historyItem) {
        Intent intent = new Intent(requireContext(), UpdateReservationActivity.class);
        intent.putExtra("reservation", historyItem);
        startActivity(intent);
    }
}
