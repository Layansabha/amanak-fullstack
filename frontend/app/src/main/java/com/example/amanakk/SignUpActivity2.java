package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.example.amanakk.network.UserAccountsRequest;
import com.example.amanakk.network.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity2 extends AppCompatActivity {
    private EditText phoneNumberEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up2);

        // Bind UI elements
        phoneNumberEditText = findViewById(R.id.small_labell);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword2);
        signUpButton = findViewById(R.id.button);
        ImageView backButton = findViewById(R.id.imageView57);
        TextView termsText = findViewById(R.id.textView40);
        TextView privacyText = findViewById(R.id.textView4);

        // Initialize API service using the app client
        apiService = ApiClient.getAppClient().create(ApiService.class);

        // Set up focus change background effects for EditTexts
        setupFocusChangeBackground(phoneNumberEditText);
        setupFocusChangeBackground(passwordEditText);
        setupFocusChangeBackground(confirmPasswordEditText);

        // Set up sign-up button listener
        signUpButton.setOnClickListener(v -> registerUser());
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
        if (termsText != null) {
            termsText.setOnClickListener(v -> startActivity(new Intent(this, TermsActivity.class)));
        }
        if (privacyText != null) {
            privacyText.setOnClickListener(v -> startActivity(new Intent(this, PrivacyActivity.class)));
        }
    }

    private void setupFocusChangeBackground(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editText.setBackgroundResource(R.drawable.rounded_corner_view_focus);
            } else {
                editText.setBackgroundResource(R.drawable.rounded_corner_view);
            }
        });
    }

    private void registerUser() {
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Check if fields are empty
        if (phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the ID number, National ID, and birthdate from the previous activity
        Intent intent = getIntent();
        String idNumber = intent.getStringExtra("idNumber");
        String nationalId = intent.getStringExtra("nationalId");
        String birthdate = intent.getStringExtra("birthdate");

        // Create request object
        UserAccountsRequest request = new UserAccountsRequest(phoneNumber, password, idNumber, nationalId, birthdate);

        // Send sign-up request to the server
        Call<SignUpResponse> call = apiService.registerUser(request);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(SignUpActivity2.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity2.this, MainActivity.class);  // Redirect to MainActivity
                    startActivity(intent);
                    finish();
                } else {
                    // Provide a more detailed error message
                    String errorMessage = response.body() != null ? response.body().getMessage() : "Registration failed";
                    Toast.makeText(SignUpActivity2.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity2.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
