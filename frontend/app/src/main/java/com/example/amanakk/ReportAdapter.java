package com.example.amanakk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private final List<ReportItem> reports;
    private OnReportClickListener onReportClickListener;

    public ReportAdapter(List<ReportItem> reports) {
        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportItem report = reports.get(position);
        holder.reportIcon.setImageResource(report.getTypeIcon());
        holder.reportStatusIcon.setImageResource(report.getStatusIcon());
        holder.reportTitle.setText(report.getTitle());
        holder.reportId.setText(report.getCaseId());
        holder.reportDate.setText(report.getLastUpdated());
        holder.reportStatusText.setText(report.getStatus());
        holder.reportType.setText(report.getType());
        holder.itemView.setOnClickListener(v -> {
            if (onReportClickListener != null) {
                onReportClickListener.onReportClick(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setOnReportClickListener(OnReportClickListener listener) {
        this.onReportClickListener = listener;
    }

    public interface OnReportClickListener {
        void onReportClick(ReportItem report);
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        ImageView reportIcon, reportStatusIcon;
        TextView reportTitle, reportId, reportDate, reportStatusText, reportType;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reportIcon = itemView.findViewById(R.id.report_icon);
            reportStatusIcon = itemView.findViewById(R.id.report_status_icon);
            reportTitle = itemView.findViewById(R.id.report_title);
            reportId = itemView.findViewById(R.id.report_id);
            reportDate = itemView.findViewById(R.id.report_date);
            reportStatusText = itemView.findViewById(R.id.report_status_text);
            reportType = itemView.findViewById(R.id.report_type);
        }
    }
}
