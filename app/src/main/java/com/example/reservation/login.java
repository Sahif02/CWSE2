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
    private EditText usernameEditText, passwordEditText;
    private TextView usernameError, passwordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Find views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        usernameError = findViewById(R.id.wrongUsername);
        passwordError = findViewById(R.id.wrongPassword);

        Button loginButton = findViewById(R.id.loginbtn);
        Button registerButton = findViewById(R.id.registerpagebtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            private User userDetails;

            @Override
            public void onClick(View view) {
                usernameError.setText("");
                passwordError.setText("");

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateInput(username, password)) {
                    if (authenticateUser(username, password)) {
                        // Successful login
                        Toast.makeText(login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        userDetails = getUserDetails(username);

                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.putExtra("userDetails", userDetails);
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

    private boolean validateInput(String username, String password) {
        boolean isValid = true;

        if (username.isEmpty()) {
            usernameError.setText("Cannot leave blank");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordError.setText("Cannot leave blank");
            isValid = false;
        }

        return isValid;
    }

    private boolean authenticateUser(String username, String password) {
        // Get a readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD};

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

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

    private User getUserDetails(String username) {
        User userDetails = new User();

        // Get a readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PHONE
        };

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

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

        // Move to the first row of the result
        if (cursor.moveToFirst()) {
            userDetails.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME)));
            userDetails.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
            userDetails.setPhone(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE)));
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return userDetails;
    }
}
