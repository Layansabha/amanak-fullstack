package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.amanakk.network.ApiService;
import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.UserCheckRequest;
import com.example.amanakk.network.UserCheckResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView backIcon;
    private EditText idOrPhoneField;
    private Button resetButton;
    private ApiService apiService;
    private boolean isActivityActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);

        apiService = ApiClient.getAppClient().create(ApiService.class);

        backIcon = findViewById(R.id.imageView);
        idOrPhoneField = findViewById(R.id.small_label);
        resetButton = findViewById(R.id.button);

        setupListeners();
    }

    private void setupListeners() {
        backIcon.setOnClickListener(v -> finish());

        idOrPhoneField.setOnFocusChangeListener((v, hasFocus) -> {
            int drawableId = hasFocus ? R.drawable.rounded_corner_view_focus : R.drawable.rounded_corner_view;
            idOrPhoneField.setBackground(ContextCompat.getDrawable(getApplicationContext(), drawableId));
        });

        resetButton.setOnClickListener(v -> verifyUser());
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityActive = false;
    }

    private void verifyUser() {
        String identifier = idOrPhoneField.getText().toString().trim();

        if (identifier.isEmpty()) {
            showToast("Please enter your National ID or Phone Number");
            return;
        }

        UserCheckRequest request = new UserCheckRequest(identifier);
        Call<UserCheckResponse> call = apiService.checkUser(request);
        call.enqueue(new Callback<UserCheckResponse>() {
            @Override
            public void onResponse(Call<UserCheckResponse> call, Response<UserCheckResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isFound()) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                    intent.putExtra("phoneNumber", response.body().getPhoneNumber());
                    startActivity(intent);
                } else {
                    showToast("User not found");
                }
            }

            @Override
            public void onFailure(Call<UserCheckResponse> call, Throwable t) {
                showToast("Network error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        if (isActivityActive) {
            runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
        }
    }
}
