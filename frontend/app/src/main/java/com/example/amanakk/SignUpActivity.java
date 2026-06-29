package com.example.amanakk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.example.amanakk.network.IdNumbersRequest;
import com.example.amanakk.network.SignUpResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "AMANAKK_VERIFY_ID";
    private EditText idNumberEditText, nationalIdEditText, birthdateEditText;
    private Button continueButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Binding UI elements
        idNumberEditText = findViewById(R.id.small_label);
        nationalIdEditText = findViewById(R.id.viewww);
        birthdateEditText = findViewById(R.id.editTextDate);
        continueButton = findViewById(R.id.button);

        // Initializing Retrofit API client for the app backend
        apiService = ApiClient.getAppClient().create(ApiService.class);

        // Set up date picker dialog
        birthdateEditText.setOnClickListener(v -> showDatePickerDialog());

        // Set up button listener for verification
        continueButton.setOnClickListener(v -> verifyUserDetails());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, day) -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            calendar.set(year, month, day);
            birthdateEditText.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void verifyUserDetails() {
        String idNumber = idNumberEditText.getText().toString().trim().toUpperCase();
        String nationalId = nationalIdEditText.getText().toString().trim();
        String birthdate = birthdateEditText.getText().toString().trim();

        if (idNumber.isEmpty() || nationalId.isEmpty() || birthdate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        IdNumbersRequest request = new IdNumbersRequest(idNumber, nationalId, birthdate);

        // API call to verify user details
        Call<SignUpResponse> call = apiService.verifyIdNumber(request);
        Log.d(TAG, "Final request URL: " + call.request().url());
        Log.d(TAG, "Request body: idNumber=" + idNumber + ", nationalId=" + nationalId + ", birthdate=" + birthdate);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d(TAG, "HTTP status code: " + response.code());
                if (response.isSuccessful()) {
                    SignUpResponse signUpResponse = response.body();
                    if (signUpResponse != null) {
                        Log.d(TAG, "Backend response body: success=" + signUpResponse.isSuccess()
                                + ", message=" + signUpResponse.getMessage());
                    } else {
                        Log.d(TAG, "Backend response body: null");
                    }
                    if (signUpResponse != null && signUpResponse.isSuccess()) {
                        Toast.makeText(SignUpActivity.this, "ID verification successful", Toast.LENGTH_SHORT).show();

                        // Move to the next screen for additional sign-up details
                        Intent intent = new Intent(SignUpActivity.this, SignUpActivity2.class);
                        intent.putExtra("idNumber", idNumber);
                        intent.putExtra("nationalId", nationalId);
                        intent.putExtra("birthdate", birthdate);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e(TAG, "Backend error body: " + errorBody);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to read backend error body", e);
                    }
                    Toast.makeText(SignUpActivity.this, "Verification failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e(TAG, "Call failed for URL: " + call.request().url(), t);
                Toast.makeText(SignUpActivity.this, "Network error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
