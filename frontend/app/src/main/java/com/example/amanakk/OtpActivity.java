package com.example.amanakk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.example.amanakk.network.OtpRequest;
import com.example.amanakk.network.OtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    private EditText otp1, otp2, otp3, otp4;
    private Button verifyButton;
    private TextView resendCode, timerText;
    private CountDownTimer countDownTimer;
    private ApiService apiService;
    private String phoneNumber; // This will be passed from the ForgotPasswordActivity

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp); // Ensure this matches the correct layout

        // Initialize API client
        apiService = ApiClient.getAppClient().create(ApiService.class);

        // Get the phone number from the previous activity
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        // Initialize UI elements
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        verifyButton = findViewById(R.id.button);
        resendCode = findViewById(R.id.textView7);
        timerText = findViewById(R.id.timerText);

        setFocusChangeListener(otp1);
        setFocusChangeListener(otp2);
        setFocusChangeListener(otp3);
        setFocusChangeListener(otp4);

        // Set onClick listener for verify button
        verifyButton.setOnClickListener(view -> {
            String otp = otp1.getText().toString().trim() + otp2.getText().toString().trim() + otp3.getText().toString().trim() + otp4.getText().toString().trim();
            if (otp.length() == 4) {
                verifyOtp(otp);
            } else {
                Toast.makeText(OtpActivity.this, "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
            }
        });

        startTimer();

        resendCode.setOnClickListener(view -> {
            if (resendCode.isEnabled()) {
                resendOtp();
                startTimer();
            }
        });
    }

    private void setFocusChangeListener(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            int background = hasFocus ? R.drawable.rounded_corner_view_focus : R.drawable.rounded_corner_view;
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), background, null));
        });
    }

    private void startTimer() {
        resendCode.setEnabled(false);
        resendCode.setTextColor(ContextCompat.getColor(this, R.color.gray));
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("0:" + String.format("%02d", millisUntilFinished / 1000));
            }

            public void onFinish() {
                resendCode.setEnabled(true);
                resendCode.setTextColor(ContextCompat.getColor(OtpActivity.this, R.color.blue));
                timerText.setText("0:00");
            }
        }.start();
    }

    private void verifyOtp(String otp) {
        OtpRequest otpRequest = new OtpRequest(phoneNumber, otp);
        apiService.verifyOtp(otpRequest).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isVerified()) {
                    Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                    // Proceed to the next step, such as password reset
                    Intent intent = new Intent(OtpActivity.this, reset.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOtp() {
        apiService.resendOtp(phoneNumber).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(OtpActivity.this, "OTP Resent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtpActivity.this, "Error resending OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
