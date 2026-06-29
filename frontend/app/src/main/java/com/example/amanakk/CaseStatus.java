package com.example.amanakk;

public enum CaseStatus {
    REPORT_SUBMITTED("Report Submitted"),
    UNDER_INVESTIGATION("Under Investigation"),
    CASE_SOLVED("Case Solved"),
    CASE_NOT_SOLVED("Case Not Solved"),
    CASE_CLOSED("Case Closed"),
    INVESTIGATION_NOT_STARTED("Investigation Not Started");

    private String description;

    CaseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Optional: Method to get enum from string if you need to convert from strings to enum values
    public static CaseStatus fromString(String text) {
        for (CaseStatus b : CaseStatus.values()) {
            if (b.description.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
