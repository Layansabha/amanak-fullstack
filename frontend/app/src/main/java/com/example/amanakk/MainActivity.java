package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.example.amanakk.network.LoginRequest;
import com.example.amanakk.network.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText idOrPhoneEditText, passwordEditText;
    private TextView loginButton, signUpTextView, forgotPasswordTextView;
    private ImageView showPasswordBtn;
    private ProgressBar progressBar;
    private ApiService apiService;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize API service
        apiService = ApiClient.getAppClient().create(ApiService.class);

        // Bind UI elements
        idOrPhoneEditText = findViewById(R.id.small_label);
        passwordEditText = findViewById(R.id.pass);
        loginButton = findViewById(R.id.button);
        signUpTextView = findViewById(R.id.textView4);
        forgotPasswordTextView = findViewById(R.id.forgot);
        showPasswordBtn = findViewById(R.id.eye_icon);
        progressBar = findViewById(R.id.progressBar);

        // Setup loading dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.loading_dialog);
        builder.setCancelable(false);
        loadingDialog = builder.create();

        loginButton.setOnClickListener(v -> {
            loginButton.setEnabled(false); // Disable the button to prevent multiple clicks
            idOrPhoneEditText.setEnabled(false); // Disable input fields
            passwordEditText.setEnabled(false); // Disable input fields
            loadingDialog.show(); // Show loading dialog
            loginUser();
        });
        signUpTextView.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        forgotPasswordTextView.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
        showPasswordBtn.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void togglePasswordVisibility() {
        if (passwordEditText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPasswordBtn.setImageResource(R.drawable.ic_eye_open);
        } else {
            passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPasswordBtn.setImageResource(R.drawable.ic_eye);
        }
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    private void loginUser() {
        String idOrPhone = idOrPhoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (idOrPhone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true); // Re-enable the button
            idOrPhoneEditText.setEnabled(true); // Re-enable input fields
            passwordEditText.setEnabled(true); // Re-enable input fields
            loadingDialog.dismiss(); // Hide loading dialog
            return;
        }

        LoginRequest loginRequest = new LoginRequest(idOrPhone, password);
        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loginButton.setEnabled(true); // Re-enable the button after response
                idOrPhoneEditText.setEnabled(true); // Re-enable input fields
                passwordEditText.setEnabled(true); // Re-enable input fields
                loadingDialog.dismiss(); // Hide loading dialog
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    Log.d(TAG, "Login response: success=" + loginResponse.isSuccess()
                            + ", message=" + loginResponse.getMessage()
                            + ", nationalId=" + loginResponse.getNationalId()
                            + ", fullName=" + loginResponse.getFullName()
                            + ", idNumber=" + loginResponse.getIdNumber()
                            + ", phoneNumber=" + loginResponse.getPhoneNumber());
                } else {
                    Log.d(TAG, "Login response: empty body, code=" + response.code());
                }

                boolean hasNationalId = loginResponse != null
                        && loginResponse.getNationalId() != null
                        && !loginResponse.getNationalId().trim().isEmpty();
                boolean loginSucceeded = response.isSuccessful()
                        && loginResponse != null
                        && (loginResponse.isSuccess() || hasNationalId);

                if (loginSucceeded) {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    String nationalId = loginResponse.getNationalId();

                    if (nationalId != null && !nationalId.trim().isEmpty()) {
                        nationalId = nationalId.trim();
                        SharedPreferencesUtils.saveReceiverNationalId(MainActivity.this, nationalId);
                        SharedPreferencesUtils.saveSenderId(MainActivity.this, nationalId);
                        SharedPreferencesUtils.saveUserProfile(
                                MainActivity.this,
                                loginResponse.getFullName(),
                                nationalId,
                                loginResponse.getIdNumber(),
                                loginResponse.getPhoneNumber(),
                                idOrPhone
                        );
                        getSharedPreferences("app_prefs", MODE_PRIVATE)
                                .edit()
                                .putBoolean("isLoggedIn", true)
                                .apply();
                    } else {
                        Toast.makeText(MainActivity.this, "Error: Unable to retrieve National ID. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Proceed to the next activity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage;
                    if (loginResponse != null && loginResponse.getMessage() != null) {
                        errorMessage = loginResponse.getMessage();
                    } else if (response.code() == 401) {
                        errorMessage = "Incorrect credentials. Please try again.";
                    } else if (response.code() == 500) {
                        errorMessage = "Server error. Please try again later.";
                    } else {
                        errorMessage = "Login failed. Please check your credentials and try again.";
                    }
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginButton.setEnabled(true); // Re-enable the button after failure
                idOrPhoneEditText.setEnabled(true); // Re-enable input fields
                passwordEditText.setEnabled(true); // Re-enable input fields
                loadingDialog.dismiss(); // Hide loading dialog
                Toast.makeText(MainActivity.this, "Network error. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
