package com.example.amanakk;

import java.util.Date;

public class Case {
    private Long id;
    private String relationshipToVictim;
    private Date dateOfCrime;
    private String type;
    private String details;
    private CaseStatus status;  // Use enum type here
    private Date reportedAt;
    private String nationalId;

    public enum CaseStatus {
        REPORT_SUBMITTED("Report Submitted"),
        UNDER_INVESTIGATION("Under Investigation"),
        CASE_SOLVED("Case Solved"),
        CASE_NOT_SOLVED("Case Not Solved"),
        CASE_CLOSED("Case Closed"),
        INVESTIGATION_NOT_STARTED("Investigation Not Started");

        private final String description;

        CaseStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // Default constructor
    public Case() {}

    // Parameterized constructor
    public Case(Long id, String relationshipToVictim, Date dateOfCrime, String type, String details, CaseStatus status, Date reportedAt, String nationalId) {
        this.id = id;
        this.relationshipToVictim = relationshipToVictim;
        this.dateOfCrime = dateOfCrime;
        this.type = type;
        this.details = details;
        this.status = status;
        this.reportedAt = reportedAt;
        this.nationalId = nationalId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRelationshipToVictim() { return relationshipToVictim; }
    public void setRelationshipToVictim(String relationshipToVictim) { this.relationshipToVictim = relationshipToVictim; }
    public Date getDateOfCrime() { return dateOfCrime; }
    public void setDateOfCrime(Date dateOfCrime) { this.dateOfCrime = dateOfCrime; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }  // Ensure type is CaseStatus
    public Date getReportedAt() { return reportedAt; }
    public void setReportedAt(Date reportedAt) { this.reportedAt = reportedAt; }
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
}
