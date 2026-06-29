package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class reportsss extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private final List<ReportItem> reportItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports); // Make sure 'reportsss' is the correct layout file name.

        setupReports();
        setupBottomNavigationView();
    }

    private void setupReports() {
        reportItems.clear();
        reportItems.add(createOldOpenReport());
        reportItems.add(createSubmittedReport());

        RecyclerView reportsRecyclerView = findViewById(R.id.reportsRecyclerView);
        ReportAdapter reportAdapter = new ReportAdapter(reportItems);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportsRecyclerView.setAdapter(reportAdapter);
        reportAdapter.setOnReportClickListener(this::openReportDetails);

        TextView reportCount = findViewById(R.id.reportCount);
        if (reportCount != null) {
            reportCount.setText(String.valueOf(reportItems.size()));
        }
    }

    private ReportItem createOldOpenReport() {
        return new ReportItem(
                "AMK-2026-0142",
                "Impersonation report opened",
                "Impersonation",
                "Under Investigation",
                "May 18, 2026",
                "Updated today, 10:23 AM",
                "High",
                "Sara - Cyber Security Specialist",
                "A fake account is using the victim's name and sending suspicious links to contacts. Screenshots and the account URL were received.",
                "Evidence is being reviewed and matched with the reported account activity. The case is active and assigned to a specialist.",
                "You will receive an inbox update if more evidence is needed. Do not contact the suspicious account directly.",
                R.drawable.notyet,
                R.drawable.impersonation
        );
    }

    private ReportItem createSubmittedReport() {
        return new ReportItem(
                "AMK-2026-0098",
                "Financial fraud report",
                "Fraud",
                "Case Solved",
                "April 03, 2026",
                "Closed on April 09, 2026",
                "Medium",
                "Amanak Case Review Team",
                "The user reported a payment scam through a social media marketplace. Evidence and transaction details were verified.",
                "The suspicious account and transaction trail were documented. The case was closed after guidance was sent to the user.",
                "No further action is required. Keep the case number for reference.",
                R.drawable.solved,
                R.drawable.fraud
        );
    }

    private void openReportDetails(ReportItem report) {
        Intent intent = new Intent(this, ReportDetailActivity.class);
        intent.putExtra(ReportDetailActivity.EXTRA_CASE_ID, report.getCaseId());
        intent.putExtra(ReportDetailActivity.EXTRA_TITLE, report.getTitle());
        intent.putExtra(ReportDetailActivity.EXTRA_TYPE, report.getType());
        intent.putExtra(ReportDetailActivity.EXTRA_STATUS, report.getStatus());
        intent.putExtra(ReportDetailActivity.EXTRA_SUBMITTED_DATE, report.getSubmittedDate());
        intent.putExtra(ReportDetailActivity.EXTRA_LAST_UPDATED, report.getLastUpdated());
        intent.putExtra(ReportDetailActivity.EXTRA_PRIORITY, report.getPriority());
        intent.putExtra(ReportDetailActivity.EXTRA_ASSIGNED_TO, report.getAssignedTo());
        intent.putExtra(ReportDetailActivity.EXTRA_SUMMARY, report.getSummary());
        intent.putExtra(ReportDetailActivity.EXTRA_CURRENT_ACTION, report.getCurrentAction());
        intent.putExtra(ReportDetailActivity.EXTRA_NEXT_STEP, report.getNextStep());
        intent.putExtra(ReportDetailActivity.EXTRA_STATUS_ICON, report.getStatusIcon());
        intent.putExtra(ReportDetailActivity.EXTRA_TYPE_ICON, report.getTypeIcon());
        startActivity(intent);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavUtils.setup(bottomNavigationView, this, R.id.reportss);
    }

    private void startActivityWithAnimation(Class<?> targetActivity) {
        BottomNavUtils.navigateTo(this, targetActivity);
    }
}
