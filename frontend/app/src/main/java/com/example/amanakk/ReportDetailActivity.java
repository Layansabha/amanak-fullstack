package com.example.amanakk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CASE_ID = "caseId", EXTRA_TITLE = "title", EXTRA_TYPE = "type", EXTRA_STATUS = "status",
            EXTRA_SUBMITTED_DATE = "submittedDate", EXTRA_LAST_UPDATED = "lastUpdated", EXTRA_PRIORITY = "priority",
            EXTRA_ASSIGNED_TO = "assignedTo", EXTRA_SUMMARY = "summary", EXTRA_CURRENT_ACTION = "currentAction",
            EXTRA_NEXT_STEP = "nextStep", EXTRA_STATUS_ICON = "statusIcon", EXTRA_TYPE_ICON = "typeIcon";
    private static final String TAG = "ReportFlow";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        getWindow().setStatusBarColor(0xFFEEF0F2);
        getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        String caseId = clean(getIntent().getStringExtra(EXTRA_CASE_ID), "1561-15623");
        String type = clean(getIntent().getStringExtra(EXTRA_TITLE), clean(getIntent().getStringExtra(EXTRA_TYPE), "Extortion"));
        if (type.toLowerCase().contains("report") || type.toLowerCase().contains("submitted")) type = "Cybercrime";
        ((TextView) findViewById(R.id.pageTitle)).setText("#" + caseId);
        ((TextView) findViewById(R.id.detailTitle)).setText(type + (type.endsWith("Cybercrime") ? "" : " Cybercrime"));
        ((TextView) findViewById(R.id.detailCaseId)).setText("#" + caseId);
        ((ImageView) findViewById(R.id.detailTypeIcon)).setImageResource(getIntent().getIntExtra(EXTRA_TYPE_ICON, R.drawable.report_icon));
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.detailStatusIcon).setOnClickListener(v -> Toast.makeText(this, "Report settings", Toast.LENGTH_SHORT).show());
        buildTimeline(clean(getIntent().getStringExtra(EXTRA_STATUS), "Report Submitted"));
        findViewById(R.id.chatExpertCard).setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(ChatActivity.EXTRA_DEMO_CHAT, true);
            intent.putExtra("receiverId", ChatActivity.DEMO_SPECIALIST_ID);
            startActivity(intent);
        });
        Log.d(TAG, "Timeline screen opened: report=" + caseId + ", type=" + type);
    }

    private void buildTimeline(String status) {
        String[] titles = {"Submit your report", "CS.Mohammed\nreceived the report", "Take final decision\non your report.", "The case closed."};
        int completedSteps = getCompletedSteps(status);
        LinearLayout container = findViewById(R.id.timelineContainer);
        for (int i = 0; i < titles.length; i++) {
            boolean complete = i < completedSteps;
            boolean lineComplete = i + 1 < completedSteps;
            container.addView(createTimelineRow(titles[i], complete, lineComplete, i == titles.length - 1));
        }
    }

    private int getCompletedSteps(String status) {
        String normalized = status == null ? "" : status.trim().toLowerCase();
        if (normalized.contains("closed")) return 4;
        if (normalized.contains("solved") || normalized.contains("final decision")) return 3;
        if (normalized.contains("under investigation") || normalized.contains("received")) return 2;
        return 1;
    }

    private View createTimelineRow(String title, boolean complete, boolean lineComplete, boolean last) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.TOP);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(112));
        row.setLayoutParams(rowParams);
        LinearLayout rail = new LinearLayout(this);
        rail.setOrientation(LinearLayout.VERTICAL); rail.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView marker = new TextView(this);
        marker.setText(complete ? "\u2713" : ""); marker.setGravity(Gravity.CENTER); marker.setTextColor(Color.WHITE); marker.setTextSize(12);
        marker.setBackgroundColor(complete ? Color.rgb(56, 93, 148) : Color.rgb(239, 240, 242));
        rail.addView(marker, new LinearLayout.LayoutParams(dp(19), dp(19)));
        if (!last) {
            View line = new View(this); line.setBackgroundColor(lineComplete ? Color.rgb(56, 93, 148) : Color.rgb(218, 220, 223));
            rail.addView(line, new LinearLayout.LayoutParams(dp(3), dp(93)));
        }
        row.addView(rail, new LinearLayout.LayoutParams(dp(34), LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout card = new LinearLayout(this);
        card.setGravity(Gravity.CENTER_VERTICAL); card.setOrientation(LinearLayout.HORIZONTAL); card.setPadding(dp(14), dp(10), dp(12), dp(10));
        card.setBackgroundResource(R.drawable.report_timeline_card);
        ImageView icon = new ImageView(this); icon.setImageResource(R.drawable.report_icon); icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        card.addView(icon, new LinearLayout.LayoutParams(dp(43), dp(43)));
        LinearLayout copy = new LinearLayout(this); copy.setOrientation(LinearLayout.VERTICAL); copy.setPadding(dp(10), 0, dp(5), 0);
        TextView heading = new TextView(this); heading.setText(title); heading.setTextColor(Color.rgb(56, 93, 148)); heading.setTextSize(14); heading.setTypeface(null, android.graphics.Typeface.BOLD);
        TextView date = new TextView(this); date.setText("09:10 AM, 9 May 2022"); date.setTextColor(Color.rgb(55, 55, 55)); date.setTextSize(12);
        copy.addView(heading); copy.addView(date);
        card.addView(copy, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        ImageView info = new ImageView(this); info.setImageResource(R.drawable.ic_report_info); info.setOnClickListener(v -> Toast.makeText(this, title, Toast.LENGTH_SHORT).show());
        card.addView(info, new LinearLayout.LayoutParams(dp(22), dp(22)));
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(0, dp(96), 1f); cardParams.bottomMargin = dp(16);
        row.addView(card, cardParams);
        return row;
    }

    private String clean(String value, String fallback) { return value == null || value.trim().isEmpty() ? fallback : value.trim(); }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
}
