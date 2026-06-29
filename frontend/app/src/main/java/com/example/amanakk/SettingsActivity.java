package com.example.amanakk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not);

        preferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);

        bindBackButton();
        bindSwitch(R.id.switch1, "UPDATES_ENABLED");
        bindSwitch(R.id.switch4, "PUSH_NOTIFICATIONS_ENABLED");
        bindSwitch(R.id.switch5, "EMAIL_NOTIFICATIONS_ENABLED");
        bindSwitch(R.id.switch6, "CHAT_NOTIFICATIONS_ENABLED");
        bindAccountActions();
    }

    private void bindBackButton() {
        ImageView backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    private void bindSwitch(int switchId, String preferenceKey) {
        Switch settingSwitch = findViewById(switchId);
        if (settingSwitch == null) {
            return;
        }

        settingSwitch.setChecked(preferences.getBoolean(preferenceKey, true));
        settingSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                preferences.edit().putBoolean(preferenceKey, isChecked).apply());
    }

    private void bindAccountActions() {
        ImageView deleteButton = findViewById(R.id.imageView55);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(v ->
                    Toast.makeText(this, "Delete account is not available yet.", Toast.LENGTH_SHORT).show());
        }

        ImageView logoutButton = findViewById(R.id.imageView56);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> {
                getSharedPreferences("app_prefs", MODE_PRIVATE)
                        .edit()
                        .putBoolean("isLoggedIn", false)
                        .apply();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}
