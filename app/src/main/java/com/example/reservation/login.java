package com.example.reservation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText usrname, pass;
    private TextView wrongname, wrongpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Find views
        usrname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        wrongname = findViewById(R.id.wrongUsername);
        wrongpass = findViewById(R.id.wrongPassword);

        Button loginButton = findViewById(R.id.loginbtn);
        Button registerButton = findViewById(R.id.registerpagebtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongname.setText("");
                wrongpass.setText("");

                String uname = usrname.getText().toString();
                String upass = pass.getText().toString();

                if (validateInput(uname, upass)) {
                    if (authenticateUser(uname, upass)) {
                        // Successful login
                        Toast.makeText(login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to the desired activity (e.g., HomeActivity)
                        // Replace HomeActivity.class with the actual activity you want to navigate to
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Invalid username or password
                        Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInput(String uname, String upass) {
        boolean isValid = true;

        if (uname.isEmpty()) {
            wrongname.setText("Cannot leave blank");
            isValid = false;
        }

        if (uname.isEmpty()) {
            wrongpass.setText("Cannot leave blank");
            isValid = false;
        }

        if (!authenticateUser(uname, upass)) {
            wrongname.setText("Wrong Username!");
            wrongpass.setText("Wrong Password!");
            isValid = false;
        }

        return isValid;
    }

    private boolean authenticateUser(String uname, String upass) {
        // Get a readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_PASSWORD
        };

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {uname, upass};

        // Query the database
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if the cursor has any rows, indicating a successful login
        boolean loginSuccessful = cursor.getCount() > 0;

        // Close the cursor and database
        cursor.close();
        db.close();

        return loginSuccessful;
    }
}
