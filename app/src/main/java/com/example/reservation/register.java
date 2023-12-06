package com.example.reservation;

import android.content.ContentValues;
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

public class register extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText username, email, password, repass;
    private TextView usernameError, emailError, passwordError, repassError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Find views
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repass = findViewById(R.id.repassword);
        usernameError = findViewById(R.id.usernameError);
        emailError = findViewById(R.id.emailError);
        passwordError = findViewById(R.id.passwordError);
        repassError = findViewById(R.id.repassError);

        // Register button click listener
        Button registerButton = findViewById(R.id.registerbtn);
        Button loginButton = findViewById(R.id.loginpagebtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset error messages
                usernameError.setText("");
                emailError.setText("");
                passwordError.setText("");
                repassError.setText("");

                String uname = username.getText().toString();
                String uemail = email.getText().toString();
                String upass = password.getText().toString();
                String confirmpass = repass.getText().toString();

                if (validateInput(uname, uemail, upass, confirmpass)) {
                    // Store user data into the local database
                    if (storeUserData(uname, uemail, upass)) {
                        // Show a toast message
                        Toast.makeText(register.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        // Redirect to the login page
                        redirectToLoginPage();
                    } else {
                        // Show an error message if data already exists
                        Toast.makeText(register.this, "Username or email already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(register.this, login.class);

                // Start the LoginActivity
                startActivity(intent);
            }
        });
    }

    private boolean validateInput(String uname, String uemail, String upass, String confirmpass) {
        boolean isValid = true;

        if (uname.isEmpty()) {
            usernameError.setText("Cannot leave blank");
            isValid = false;
        }

        if (uemail.isEmpty()) {
            emailError.setText("Cannot leave blank");
            isValid = false;
        }

        if (upass.isEmpty()) {
            passwordError.setText("Cannot leave blank");
            isValid = false;
        }

        if (!upass.equals(confirmpass)) {
            repassError.setText("Passwords do not match");
            isValid = false;
        }

        // Check if email or username already exists in the database
        if (isUserExists(uname)) {
            usernameError.setText("Username already exists!");
            isValid = false;
        }

        if (isEmailExists(uemail)) {
            emailError.setText("email already exists!");
            isValid = false;
        }

        return isValid;
    }

    private boolean isUserExists(String username) {
        // Get a readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define a projection that specifies which columns to query
        String[] projection = {DatabaseHelper.COLUMN_USERNAME};

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? ";
        String[] selectionArgs = {username};

        // Perform the query
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER,   // The table to query
                projection,                  // The array of columns to return (null to return all)
                selection,                   // The columns for the WHERE clause
                selectionArgs,               // The values for the WHERE clause
                null,                        // don't group the rows
                null,                        // don't filter by row groups
                null                         // don't sort order
        );

        // Check if the cursor has any rows
        boolean userExists = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        return userExists;
    }

    private boolean isEmailExists(String email) {
        // Get a readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define a projection that specifies which columns to query
        String[] projection = {DatabaseHelper.COLUMN_EMAIL};

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? ";
        String[] selectionArgs = {email};

        // Perform the query
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER,   // The table to query
                projection,                  // The array of columns to return (null to return all)
                selection,                   // The columns for the WHERE clause
                selectionArgs,               // The values for the WHERE clause
                null,                        // don't group the rows
                null,                        // don't filter by row groups
                null                         // don't sort order
        );

        // Check if the cursor has any rows
        boolean emailExists = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        return emailExists;
    }

    private boolean storeUserData(String uname, String uemail, String upass) {
        // Get a writable database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Create a ContentValues object to store data
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, uname);
        values.put(DatabaseHelper.COLUMN_EMAIL, uemail);
        values.put(DatabaseHelper.COLUMN_PASSWORD, upass);

        // Insert data into the "user" table
        long newRowId = db.insert(DatabaseHelper.TABLE_USER, null, values);

        // Close the database
        db.close();

        // Check if the insertion was successful
        return newRowId != -1;
    }

    private void redirectToLoginPage() {
        // Create an Intent to navigate to the LoginActivity
        Intent intent = new Intent(register.this, login.class);

        // Start the LoginActivity
        startActivity(intent);

        // Finish the current activity to prevent going back to the registration page
        finish();
    }
}
