package com.example.reservation;

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

        // Create notification channel
        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(receipt.this, "My Notification");
        builder.setContentTitle("The Food Bridge");
        builder.setContentText("Hi there, Thank you for booking with us.");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show notification
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(receipt.this);
        if (ActivityCompat.checkSelfPermission(receipt.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Handle the case where the permission is not granted
            return;
        }
        managerCompat.notify(1, builder.build());

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
                    Intent mainMenuIntent = new Intent(receipt.this, MainActivity.class);
                    startActivity(mainMenuIntent);
                    finish();
                }
            });

            backToMenuButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainMenuIntent = new Intent(receipt.this, MainActivity.class);
                    startActivity(mainMenuIntent);
                    finish();
                }
            });

            Button history = findViewById(R.id.history);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainMenuIntent = new Intent(receipt.this, MainActivity.class);
                    startActivity(mainMenuIntent);
                    finish();
                }
            });

        }

    }
}
