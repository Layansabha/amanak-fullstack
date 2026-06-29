package com.example.amanakk;

public class ReportItem {
    private final String caseId;
    private final String title;
    private final String type;
    private final String status;
    private final String submittedDate;
    private final String lastUpdated;
    private final String priority;
    private final String assignedTo;
    private final String summary;
    private final String currentAction;
    private final String nextStep;
    private final int statusIcon;
    private final int typeIcon;

    public ReportItem(
            String caseId,
            String title,
            String type,
            String status,
            String submittedDate,
            String lastUpdated,
            String priority,
            String assignedTo,
            String summary,
            String currentAction,
            String nextStep,
            int statusIcon,
            int typeIcon
    ) {
        this.caseId = caseId;
        this.title = title;
        this.type = type;
        this.status = status;
        this.submittedDate = submittedDate;
        this.lastUpdated = lastUpdated;
        this.priority = priority;
        this.assignedTo = assignedTo;
        this.summary = summary;
        this.currentAction = currentAction;
        this.nextStep = nextStep;
        this.statusIcon = statusIcon;
        this.typeIcon = typeIcon;
    }

    public String getCaseId() { return caseId; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getSubmittedDate() { return submittedDate; }
    public String getLastUpdated() { return lastUpdated; }
    public String getPriority() { return priority; }
    public String getAssignedTo() { return assignedTo; }
    public String getSummary() { return summary; }
    public String getCurrentAction() { return currentAction; }
    public String getNextStep() { return nextStep; }
    public int getStatusIcon() { return statusIcon; }
    public int getTypeIcon() { return typeIcon; }
}
