package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class reports extends AppCompatActivity {
    private static final String TAG = "ReportFlow";
    private final String[] crimeTypes = {"Fraud", "Extortion", "Harassment", "Hacking", "Defamation", "Impersonation"};
    private final String[] descriptions = {
            "Please choose this option if you were a victim of identity theft or financial scams online.",
            "Please choose this option if you were threatened with the release of private information, whether a ransom was demanded or not.",
            "Please choose this option if you are experiencing online bullying or threats.",
            "Please choose this option if there was unauthorized access to your accounts or devices.",
            "Please choose this option if unwanted information was published, whether it was damaging to your reputation or not.",
            "Please choose this option if someone pretended to be you or another person."
    };
    private final int[] icons = {R.drawable.fraud, R.drawable.extortion, R.drawable.harassment,
            R.drawable.hacking, R.drawable.defamation, R.drawable.impersonation};
    private final int[] optionIds = {R.id.linearLayout_fraud, R.id.linearLayout_extortion,
            R.id.linearLayout_harassment, R.id.linearLayout_hacking,
            R.id.linearLayout_defamation, R.id.linearLayout_impersonation};
    private View selectedOption;
    private String selectedCrimeType;
    private int selectedCrimeIcon = R.drawable.fraud;
    private TextView dropdownText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);
        setupSystemBars();
        findViewById(R.id.backIcon).setOnClickListener(v -> finish());
        findViewById(R.id.notificationIcon).setOnClickListener(v ->
                Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show());
        dropdownText = findViewById(R.id.cybercrimeDropdownText);
        for (int i = 0; i < optionIds.length; i++) bindOption(i);
        findViewById(R.id.cybercrimeDropdown).setOnClickListener(v -> showTypeSelector());
        findViewById(R.id.nextButton).setOnClickListener(v -> openDetails());
        if (savedInstanceState != null) {
            String restored = savedInstanceState.getString("selectedCrimeType");
            for (int i = 0; i < crimeTypes.length; i++) if (crimeTypes[i].equals(restored)) selectOption(i);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedCrimeType", selectedCrimeType);
    }

    private void setupSystemBars() {
        getWindow().setStatusBarColor(0xFFEEF0F2);
        getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void bindOption(int index) {
        View option = findViewById(optionIds[index]);
        ((ImageView) option.findViewById(R.id.crimeOptionIcon)).setImageResource(icons[index]);
        ((TextView) option.findViewById(R.id.crimeOptionTitle)).setText(crimeTypes[index]);
        ((TextView) option.findViewById(R.id.crimeOptionDescription)).setText(descriptions[index]);
        option.setOnClickListener(v -> selectOption(index));
    }

    private void selectOption(int index) {
        if (selectedOption != null) selectedOption.setBackgroundResource(R.drawable.report_card_background);
        selectedOption = findViewById(optionIds[index]);
        selectedOption.setBackgroundResource(R.drawable.report_card_selected_background);
        selectedCrimeType = crimeTypes[index];
        selectedCrimeIcon = icons[index];
        dropdownText.setText(selectedCrimeType);
        dropdownText.setTextColor(0xFF222222);
        Log.d(TAG, "Selected cybercrime type: " + selectedCrimeType);
    }

    private void showTypeSelector() {
        int checked = -1;
        for (int i = 0; i < crimeTypes.length; i++) if (crimeTypes[i].equals(selectedCrimeType)) checked = i;
        final int current = checked;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select Cybercrime type")
                .setSingleChoiceItems(crimeTypes, current, null)
                .setNegativeButton("Close", null)
                .setPositiveButton("Select", null)
                .create();
        dialog.setOnShowListener(v -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(button -> {
            int choice = dialog.getListView().getCheckedItemPosition();
            if (choice >= 0) {
                selectOption(choice);
                Log.d(TAG, "Dropdown selection: " + crimeTypes[choice]);
                dialog.dismiss();
            } else Toast.makeText(this, "Choose a cybercrime type", Toast.LENGTH_SHORT).show();
        }));
        dialog.show();
    }

    private void openDetails() {
        if (selectedCrimeType == null) {
            Toast.makeText(this, "Please select a cybercrime type first.", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "Step navigation: 1 -> 2, type=" + selectedCrimeType);
        Intent intent = new Intent(this, reports_two.class);
        intent.putExtra("cybercrimeType", selectedCrimeType);
        intent.putExtra("cybercrimeIcon", selectedCrimeIcon);
        startActivity(intent);
    }
}
