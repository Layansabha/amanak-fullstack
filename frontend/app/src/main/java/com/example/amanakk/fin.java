package com.example.amanakk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class fin extends AppCompatActivity {
    private static final String TAG = "ReportFlow";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fin);
        getWindow().setStatusBarColor(0xFFEEF0F2);
        getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        String reportId = getIntent().getStringExtra("caseId");
        if (reportId == null || reportId.trim().isEmpty()) reportId = "PENDING";
        ((TextView) findViewById(R.id.reportNumber)).setText(formatReportNumber(reportId));
        styleSuccessSubtitle();
        String finalReportId = reportId;
        findViewById(R.id.backIcon).setOnClickListener(v -> openHome());
        findViewById(R.id.notificationIcon).setOnClickListener(v -> Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show());
        findViewById(R.id.viewReportButton).setOnClickListener(v -> openTimeline(finalReportId));
        findViewById(R.id.reportInfoSection).setOnClickListener(v -> openTimeline(finalReportId));
        findViewById(R.id.homeButton).setOnClickListener(v -> openHome());
        findViewById(R.id.exploreReportsButton).setOnClickListener(v -> {
            startActivity(new Intent(this, reportsss.class));
            finish();
        });
    }

    private String formatReportNumber(String id) {
        return id.equals("PENDING") ? "#PENDING" : "#" + id;
    }

    private void styleSuccessSubtitle() {
        String value = "Has been placed Successfully";
        SpannableString text = new SpannableString(value);
        text.setSpan(new ForegroundColorSpan(Color.rgb(25, 151, 47)), value.indexOf("Successfully"), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(R.id.successSubtitle)).setText(text);
    }

    private void openTimeline(String reportId) {
        Intent intent = new Intent(this, ReportDetailActivity.class);
        intent.putExtra(ReportDetailActivity.EXTRA_CASE_ID, reportId);
        intent.putExtra(ReportDetailActivity.EXTRA_TITLE, getIntent().getStringExtra("cybercrimeType"));
        intent.putExtra(ReportDetailActivity.EXTRA_TYPE_ICON, getIntent().getIntExtra("cybercrimeIcon", R.drawable.report_icon));
        intent.putExtra(ReportDetailActivity.EXTRA_STATUS, "Report Submitted");
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
