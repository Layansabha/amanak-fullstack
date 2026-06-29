package com.example.amanakk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecuritySettingsActivity extends AppCompatActivity {

    private EditText oldPassword, newPassword, confirmPassword;
    private Switch biometricSwitch, otpSwitch;
    private ImageView changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security);

        oldPassword = findViewById(R.id.oldpass);
        newPassword = findViewById(R.id.newpass);
        confirmPassword = findViewById(R.id.newpass2);
        biometricSwitch = findViewById(R.id.switch2);
        otpSwitch = findViewById(R.id.switch3);
        changePasswordButton = findViewById(R.id.imageView48);
        ImageView backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        setupSwitchListeners();
        setupChangePasswordButton();
    }

    private void setupSwitchListeners() {
        biometricSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the state in SharedPreferences or adjust the backend settings
            SharedPreferences preferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
            preferences.edit().putBoolean("BIOMETRIC_LOGIN_ENABLED", isChecked).apply();
            Toast.makeText(this, "Biometric login " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });

        otpSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the state in SharedPreferences or adjust the backend settings
            SharedPreferences preferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
            preferences.edit().putBoolean("OTP_LOGIN_ENABLED", isChecked).apply();
            Toast.makeText(this, "OTP login " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });
    }

    private void setupChangePasswordButton() {
        changePasswordButton.setOnClickListener(v -> {
            String oldPass = oldPassword.getText().toString();
            String newPass = newPassword.getText().toString();
            String confirmPass = confirmPassword.getText().toString();

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "New passwords do not match!", Toast.LENGTH_LONG).show();
                return;
            }

            // Send password change request to the server
            changeUserPassword(oldPass, newPass);
        });
    }

    private void changeUserPassword(String oldPass, String newPass) {
        // Here, you would make a network request to change the password
        // For demonstration, using a simple toast message
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_LONG).show();
    }
}
