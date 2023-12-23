package com.example.reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class receipt extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(receipt.this, "My Notification");
        builder.setContentTitle("The Food Bridge");
        builder.setContentText("Hi there, Thank you for booking with us.");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(receipt.this);

        if (ActivityCompat.checkSelfPermission(receipt.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(1, builder.build());

        // Retrieve reservation data
        Intent intent = getIntent();
        if (intent != null) {
            Reservation reservation = (Reservation) intent.getSerializableExtra("reservation");
            User userDetails = (User) getIntent().getSerializableExtra("userDetails");

            TextView tv = findViewById(R.id.textView2);
            tv.setText("Customer Name: " + reservation.getCustomerName() + "/n"
                    + "Customer phone: " + userDetails.getPhone() + "/n"
                    + "Seating Area: " + reservation.getSeatingArea());

        }

    }
}
