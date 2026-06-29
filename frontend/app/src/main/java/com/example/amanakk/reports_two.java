package com.example.amanakk;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class reports_two extends AppCompatActivity {
    private static final String TAG = "ReportFlow";
    private final Calendar calendar = Calendar.getInstance();
    private TextView dateText, relationshipText, cybercrimeText;
    private EditText detailsInput;
    private int selectedIcon;
    private String selectedType, selectedDate, selectedRelationship;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_two);
        getWindow().setStatusBarColor(0xFFEEF0F2);
        getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        selectedType = getIntent().getStringExtra("cybercrimeType");
        if (selectedType == null) selectedType = "Fraud";
        selectedIcon = getIntent().getIntExtra("cybercrimeIcon", R.drawable.fraud);
        dateText = findViewById(R.id.cybercrimeDateText);
        relationshipText = findViewById(R.id.relationshipToVictimInput);
        detailsInput = findViewById(R.id.detailsInput);
        cybercrimeText = findViewById(R.id.textView50);
        ((ImageView) findViewById(R.id.cybercrimeIcon)).setImageResource(selectedIcon);
        cybercrimeText.setText(selectedType + " Cybercrime");
        findViewById(R.id.backIcon).setOnClickListener(v -> finish());
        findViewById(R.id.editCrimeType).setOnClickListener(v -> finish());
        findViewById(R.id.notificationIcon).setOnClickListener(v -> Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show());
        findViewById(R.id.dateOfCybercrimeSection).setOnClickListener(v -> showDatePicker());
        findViewById(R.id.relationshipSection).setOnClickListener(v -> showRelationshipSelector());
        findViewById(R.id.dateInfo).setOnClickListener(v -> showHelp("Date of cybercrime", "Select the date when the cybercrime happened."));
        findViewById(R.id.relationshipInfo).setOnClickListener(v -> showRelationshipHelp());
        findViewById(R.id.detailsInfo).setOnClickListener(v -> showDetailsHelp());
        detailsInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) { }
            public void onTextChanged(CharSequence s, int st, int b, int c) { Log.d(TAG, "Description length: " + s.length()); }
            public void afterTextChanged(Editable e) { }
        });
        findViewById(R.id.nextButton).setOnClickListener(v -> navigateToEvidence());
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, day) -> {
            calendar.set(year, month, day);
            selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
            dateText.setText(selectedDate);
            dateText.setTextColor(Color.rgb(34, 34, 34));
            Log.d(TAG, "Date selected: " + selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showRelationshipSelector() {
        String[] options = {"Myself", "Family member", "Friend", "Colleague", "Other"};
        new AlertDialog.Builder(this).setTitle("Relationship to the victim")
                .setItems(options, (dialog, which) -> {
                    selectedRelationship = options[which];
                    relationshipText.setText(selectedRelationship);
                    relationshipText.setTextColor(Color.rgb(34, 34, 34));
                    Log.d(TAG, "Relationship selected: " + selectedRelationship);
                }).setNegativeButton("Close", null).show();
    }

    private void showRelationshipHelp() {
        boolean ar = Locale.getDefault().getLanguage().equals("ar");
        showHelp(ar ? "العلاقة" : "Relationship to the victim",
                ar ? "علاقتك بالضحية المقدم البلاغ لها." : "Your relationship to the victim you are reporting for.");
    }

    private void showDetailsHelp() {
        showHelp("Cybercrime Details", "Write all details related to the cybercrime you experienced. Include any known criminal information, a clear description of what happened, and the evidence you can provide. Please be accurate and clear so your report can be processed promptly.");
    }

    private void showHelp(String title, String message) {
        Dialog dialog = new Dialog(this);
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(12), dp(18), dp(18));
        card.setBackgroundResource(R.drawable.report_modal_background);
        TextView close = new TextView(this);
        close.setText("×"); close.setTextSize(30); close.setTextColor(Color.rgb(34, 54, 75));
        close.setGravity(Gravity.START); close.setOnClickListener(v -> dialog.dismiss());
        TextView heading = new TextView(this);
        heading.setText(title); heading.setGravity(Gravity.CENTER); heading.setTextSize(17); heading.setTextColor(Color.rgb(45, 68, 91)); heading.setTypeface(null, android.graphics.Typeface.BOLD);
        TextView body = new TextView(this);
        body.setText(message); body.setTextColor(Color.WHITE); body.setTextSize(13); body.setLineSpacing(dp(3), 1f); body.setPadding(0, dp(12), 0, 0);
        card.addView(close, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(35)));
        card.addView(heading); card.addView(body);
        dialog.setContentView(card);
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnShowListener(v -> dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * .88f), ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();
    }

    private void navigateToEvidence() {
        String details = detailsInput.getText().toString().trim();
        if (selectedDate == null) { Toast.makeText(this, "Please select the date", Toast.LENGTH_SHORT).show(); return; }
        if (selectedRelationship == null) { Toast.makeText(this, "Please select your relationship to the victim", Toast.LENGTH_SHORT).show(); return; }
        if (details.isEmpty()) { detailsInput.setError("Please describe what happened"); detailsInput.requestFocus(); return; }
        Log.d(TAG, "Step navigation: 2 -> 3; date=" + selectedDate + ", relationship=" + selectedRelationship + ", descriptionLength=" + details.length());
        Intent intent = new Intent(this, reports_three.class);
        intent.putExtra("crime_date", selectedDate);
        intent.putExtra("relationship_to_victim", selectedRelationship);
        intent.putExtra("crime_details", details);
        intent.putExtra("cybercrimeType", selectedType);
        intent.putExtra("cybercrimeIcon", selectedIcon);
        startActivity(intent);
    }

    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
}
