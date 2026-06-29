package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.example.amanakk.network.LoginResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView nameText;
    private TextView detailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sett);

        bindAccountTextViews();
        populateAccountInfo();
        fetchLatestAccountInfo();
        setupProfileActions();
        setupCustomBottomBar();
        setupBottomNavigationView();
    }

    private void bindAccountTextViews() {
        nameText = findViewById(R.id.textView28);
        detailsText = findViewById(R.id.textView29);
    }

    private void populateAccountInfo() {
        String fullName = SharedPreferencesUtils.getUserFullName(this);
        String nationalId = SharedPreferencesUtils.getProfileNationalId(this);
        String idNumber = SharedPreferencesUtils.getUserIdNumber(this);
        String phoneNumber = SharedPreferencesUtils.getUserPhoneNumber(this);

        if (fullName == null || fullName.trim().isEmpty()) {
            fullName = nationalId == null || nationalId.trim().isEmpty() ? "AMANAK user" : nationalId;
        }

        if (nameText != null) {
            nameText.setText(fullName);
        }

        if (detailsText != null) {
            detailsText.setText(buildAccountDetails(nationalId, idNumber, phoneNumber));
        }
    }

    private void fetchLatestAccountInfo() {
        String identifier = SharedPreferencesUtils.getProfileNationalId(this);
        if (identifier == null || identifier.trim().isEmpty()) {
            identifier = SharedPreferencesUtils.getLoginIdentifier(this);
        }

        if (identifier == null || identifier.trim().isEmpty()) {
            return;
        }

        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        apiService.getUserProfile(identifier.trim()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse profileResponse = response.body();
                if (!response.isSuccessful() || profileResponse == null || !profileResponse.isSuccess()) {
                    return;
                }

                SharedPreferencesUtils.saveUserProfile(
                        profile.this,
                        profileResponse.getFullName(),
                        profileResponse.getNationalId(),
                        profileResponse.getIdNumber(),
                        profileResponse.getPhoneNumber(),
                        SharedPreferencesUtils.getLoginIdentifier(profile.this)
                );
                populateAccountInfo();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Keep the locally saved profile visible if the backend is unavailable.
            }
        });
    }

    private String buildAccountDetails(String nationalId, String idNumber, String phoneNumber) {
        StringBuilder details = new StringBuilder();
        appendDetail(details, "National ID", nationalId);
        appendDetail(details, "ID No.", idNumber);
        appendDetail(details, "Phone", phoneNumber);
        return details.length() == 0 ? "Account information" : details.toString();
    }

    private void appendDetail(StringBuilder details, String label, String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        if (details.length() > 0) {
            details.append("  |  ");
        }
        details.append(label).append(": ").append(value.trim());
    }

    private void setupProfileActions() {
        bindClick(R.id.imageView33, v ->
                Toast.makeText(this, "Edit profile is not available yet.", Toast.LENGTH_SHORT).show());
        bindClick(R.id.imageView34, v -> startActivity(new Intent(this, SecuritySettingsActivity.class)));
        bindClick(R.id.imageView35, v -> startActivity(new Intent(this, SettingsActivity.class)));
        bindClick(R.id.imageView36, v -> startActivity(new Intent(this, AboutActivity.class)));
    }

    private void setupCustomBottomBar() {
        bindClick(R.id.imageView39, v -> BottomNavUtils.navigateToTopLevel(this, HomeActivity.class));
        bindClick(R.id.imageView40, v -> BottomNavUtils.navigateToTopLevel(this, InboxActivity.class));
        bindClick(R.id.imageView42, v -> BottomNavUtils.navigateToTopLevel(this, reports.class));
        bindClick(R.id.imageView43, v -> BottomNavUtils.navigateToTopLevel(this, reportsss.class));
        bindClick(R.id.imageView44, v -> { });
    }

    private void bindClick(int viewId, View.OnClickListener listener) {
        ImageView view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavUtils.setup(bottomNavigationView, this, R.id.profile);
    }

    private void startActivityWithAnimation(Class<?> targetActivity) {
        BottomNavUtils.navigateTo(this, targetActivity);
    }
}
