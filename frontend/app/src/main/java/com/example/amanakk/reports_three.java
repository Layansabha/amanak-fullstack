package com.example.amanakk;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reports_three extends AppCompatActivity {
    private static final String TAG = "ReportFlow";
    private static final int PICK_FILES_REQUEST_CODE = 2001;
    private final List<Uri> fileList = new ArrayList<>();
    private RecyclerView mediaRecyclerView;
    private FileAdapter fileAdapter;
    private View submitButton;
    private CheckBox termsCheckbox;
    private ApiService apiService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_three);
        getWindow().setStatusBarColor(0xFFEEF0F2);
        getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        apiService = ApiClient.getAppClient().create(ApiService.class);
        String type = getIntent().getStringExtra("cybercrimeType");
        int icon = getIntent().getIntExtra("cybercrimeIcon", R.drawable.fraud);
        ((ImageView) findViewById(R.id.cybercrimeIcon)).setImageResource(icon);
        ((TextView) findViewById(R.id.textView50)).setText((type == null ? "Selected" : type) + " Cybercrime");
        findViewById(R.id.backIcon).setOnClickListener(v -> finish());
        findViewById(R.id.editCrimeType).setOnClickListener(v -> {
            Intent intent = new Intent(this, reports.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        findViewById(R.id.notificationIcon).setOnClickListener(v -> Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show());
        mediaRecyclerView = findViewById(R.id.mediaRecyclerView);
        mediaRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fileAdapter = new FileAdapter(fileList, this::removeFile);
        mediaRecyclerView.setAdapter(fileAdapter);
        findViewById(R.id.uploadArea).setOnClickListener(v -> openFilePicker());
        termsCheckbox = findViewById(R.id.termsCheckbox);
        styleTermsText();
        submitButton = findViewById(R.id.submitButton);
        submitButton.setAlpha(.45f);
        termsCheckbox.setOnCheckedChangeListener((button, checked) -> {
            submitButton.setAlpha(checked ? 1f : .45f);
            Log.d(TAG, "Terms checkbox state: " + checked);
        });
        submitButton.setOnClickListener(v -> submitReport());
    }

    private void styleTermsText() {
        String text = "I accept Terms & Conditions and Privacy Policy";
        SpannableString styled = new SpannableString(text);
        int blue = Color.rgb(56, 93, 148);
        styled.setSpan(new ForegroundColorSpan(blue), text.indexOf("Terms"), text.indexOf(" and Privacy"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styled.setSpan(new ForegroundColorSpan(blue), text.indexOf("Privacy"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsCheckbox.setText(styled);
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*", "application/pdf"});
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select evidence files"), PICK_FILES_REQUEST_CODE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_FILES_REQUEST_CODE || resultCode != RESULT_OK || data == null) return;
        if (data.getClipData() != null) {
            for (int i = 0; i < data.getClipData().getItemCount(); i++) addFile(data.getClipData().getItemAt(i).getUri());
        } else if (data.getData() != null) addFile(data.getData());
        refreshFiles();
    }

    private void addFile(Uri uri) {
        if (!fileList.contains(uri)) {
            fileList.add(uri);
            try { getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); }
            catch (SecurityException ignored) { }
        }
    }

    private void refreshFiles() {
        mediaRecyclerView.setVisibility(fileList.isEmpty() ? View.GONE : View.VISIBLE);
        fileAdapter.notifyDataSetChanged();
        Log.d(TAG, "Number of uploaded files: " + fileList.size());
    }

    private void submitReport() {
        if (!termsCheckbox.isChecked()) {
            Toast.makeText(this, "Please accept the Terms & Conditions and Privacy Policy", Toast.LENGTH_SHORT).show();
            return;
        }
        String nationalId = SharedPreferencesUtils.getReceiverNationalId(this);
        if (nationalId == null || nationalId.isEmpty()) {
            Toast.makeText(this, "Unable to retrieve National ID. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }
        String relationship = getIntent().getStringExtra("relationship_to_victim");
        String dateValue = getIntent().getStringExtra("crime_date");
        String details = getIntent().getStringExtra("crime_details");
        String type = getIntent().getStringExtra("cybercrimeType");
        Date date;
        try { date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateValue); }
        catch (Exception e) { Toast.makeText(this, "Invalid crime date", Toast.LENGTH_LONG).show(); return; }
        Case newCase = new Case();
        newCase.setRelationshipToVictim(relationship);
        newCase.setDateOfCrime(date);
        newCase.setType(type);
        newCase.setDetails(details);
        newCase.setStatus(Case.CaseStatus.REPORT_SUBMITTED);
        newCase.setNationalId(nationalId);
        Log.d(TAG, "Submit request payload: type=" + type + ", date=" + dateValue + ", relationship=" + relationship
                + ", detailsLength=" + (details == null ? 0 : details.length()) + ", files=" + fileList.size());
        submitButton.setEnabled(false);
        submitButton.setAlpha(.55f);
        apiService.submitCase(newCase).enqueue(new Callback<Case>() {
            @Override public void onResponse(Call<Case> call, Response<Case> response) {
                submitButton.setEnabled(true); submitButton.setAlpha(1f);
                if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                    String reportId = response.body().getId().toString();
                    Log.d(TAG, "Submit response/report number: " + reportId);
                    Intent intent = new Intent(reports_three.this, fin.class);
                    intent.putExtra("caseId", reportId);
                    intent.putExtra("cybercrimeType", type);
                    intent.putExtra("cybercrimeIcon", getIntent().getIntExtra("cybercrimeIcon", R.drawable.report_icon));
                    Log.d(TAG, "Navigation to submitted screen");
                    startActivity(intent);
                    finish();
                } else Toast.makeText(reports_three.this, "Failed to submit case (" + response.code() + ")", Toast.LENGTH_LONG).show();
            }
            @Override public void onFailure(Call<Case> call, Throwable t) {
                submitButton.setEnabled(true); submitButton.setAlpha(1f);
                Log.e(TAG, "Submit failed", t);
                Toast.makeText(reports_three.this, "Could not submit the report. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeFile(int position) {
        if (position >= 0 && position < fileList.size()) fileList.remove(position);
        refreshFiles();
    }
}
