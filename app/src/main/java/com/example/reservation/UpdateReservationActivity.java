package com.example.reservation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateReservationActivity extends AppCompatActivity {
    private User userDetails;
    private EditText customerNameEditText;
    private EditText customerPhoneEditText;
    private EditText seatingAreaEditText;
    private EditText dateEditText;
    private EditText mealTimeEditText;
    private EditText tableSizeEditText;
    private Button updateButton;

    private ReservationService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        // Initialize views
        customerNameEditText = findViewById(R.id.customerNameEditText);
        customerPhoneEditText = findViewById(R.id.customerPhoneEditText);
        seatingAreaEditText = findViewById(R.id.seatingAreaEditText);
        dateEditText = findViewById(R.id.dateEditText);
        mealTimeEditText = findViewById(R.id.mealTimeEditText);
        tableSizeEditText = findViewById(R.id.tableSizeEditText);
        updateButton = findViewById(R.id.updateButton);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ReservationService.class);

        // Retrieve reservation details from the intent or bundle
        if (getIntent().hasExtra("reservation")) {
            Reservation reservation = (Reservation) getIntent().getSerializableExtra("reservation");
            populateFields(reservation);

            // Set click listener for the update button
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform the update operation
                    updateReservation(reservation.getId());
                }
            });
        }


    }

    private void populateFields(Reservation reservation) {
        // Populate the EditText fields with existing reservation details
        customerNameEditText.setText(reservation.getCustomerName());
        customerPhoneEditText.setText(reservation.getCustomerPhoneNumber());
        seatingAreaEditText.setText(reservation.getSeatingArea());
        dateEditText.setText(reservation.getDate());
        mealTimeEditText.setText(reservation.getMeal());
        tableSizeEditText.setText(String.valueOf(reservation.getTableSize()));
    }

    private void updateReservation(int reservationId) {

        // Receive user details from the intent
        if (getIntent() != null && getIntent().hasExtra("userDetails")) {
            userDetails = (User) getIntent().getSerializableExtra("userDetails");
        }

        // Retrieve updated values from EditText fields
        String updatedCustomerName = customerNameEditText.getText().toString().trim();
        String updatedCustomerPhone = customerPhoneEditText.getText().toString().trim();
        String updatedSeatingArea = seatingAreaEditText.getText().toString().trim();
        String updatedDate = dateEditText.getText().toString().trim();
        String updatedMealTime = mealTimeEditText.getText().toString().trim();
        int updatedTableSize = Integer.parseInt(tableSizeEditText.getText().toString().trim());

        // Create a new Reservation object with updated values
        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(reservationId);
        updatedReservation.setCustomerPhoneNumber(updatedCustomerPhone);
        updatedReservation.setCustomerName(updatedCustomerName);
        updatedReservation.setSeatingArea(updatedSeatingArea);
        updatedReservation.setDate(updatedDate);
        updatedReservation.setMeal(updatedMealTime);
        updatedReservation.setTableSize(updatedTableSize);

        // Make the PUT API call
        Call<Void> call = apiService.updateHistoryItem(reservationId, updatedReservation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful update, for example, show a success message
                    Toast.makeText(UpdateReservationActivity.this, "Reservation updated successfully", Toast.LENGTH_SHORT).show();

                    finish(); // Close the update activity
                } else {
                    // Log the error details
                    Log.e("UpdateReservation", "Failed to update reservation. Response code: " + response.code() + ", Message: " + response.message());

                    // Handle error response
                    Toast.makeText(UpdateReservationActivity.this, "Failed to update reservation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Log the failure details
                Log.e("UpdateReservation", "Network error", t);

                // Handle failure
                Toast.makeText(UpdateReservationActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}