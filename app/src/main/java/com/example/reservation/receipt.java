package com.example.reservation;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class receipt extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Retrieve reservation data
        Intent intent = getIntent();
        if (intent != null) {
            Reservation reservation = (Reservation) intent.getSerializableExtra("reservation");
            User userDetails = (User) getIntent().getSerializableExtra("userDetails");

            // Display details in TextViews
            TextView nameTextView = findViewById(R.id.name);
            nameTextView.setText("Name: " + userDetails.getUsername());

            TextView emailTextView = findViewById(R.id.email);
            emailTextView.setText("Email: " + userDetails.getEmail());

            TextView phoneTextView = findViewById(R.id.phone);
            phoneTextView.setText("Phone: " + userDetails.getPhone());

            TextView seatTextView = findViewById(R.id.seat);
            seatTextView.setText("Seat: " + reservation.getSeatingArea());

            TextView dateTextView = findViewById(R.id.date);
            dateTextView.setText("Date: " + reservation.getDate());

            TextView timeTextView = findViewById(R.id.time);
            timeTextView.setText("Meal Time: " + reservation.getMeal());

            TextView paxTextView = findViewById(R.id.pax);
            paxTextView.setText("Pax: " + reservation.getTableSize());

            // Back to Menu Button
            Button backToMenuButton = findViewById(R.id.home1);
            Button backToMenuButton1 = findViewById(R.id.home2);

            backToMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(receipt.this, MainActivity.class);
                    intent.putExtra("userDetails", userDetails);

                    // Start ReceiptActivity
                    startActivity(intent);
                    finish();
                }
            });

            backToMenuButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(receipt.this, MainActivity.class);
                    intent.putExtra("userDetails", userDetails);

                    // Start ReceiptActivity
                    startActivity(intent);
                    finish();
                }
            });

            Button history = findViewById(R.id.history);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }
}
