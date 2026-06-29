package com.example.amanakk_backend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cases")
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relationship_to_victim", nullable = false)
    private String relationshipToVictim;

    @Column(name = "date_of_crime")
    @Temporal(TemporalType.DATE)
    @JsonDeserialize(using = FlexibleDateDeserializer.class)
    private Date dateOfCrime;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private String status;

    @Column(name = "reported_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = FlexibleDateDeserializer.class)
    private Date reportedAt;

    @Column(name = "national_id", nullable = false)
    private String nationalId;

    @Column(name = "report_number", nullable = false, updatable = false, length = 36)
    private String reportNumber;

    @Column(columnDefinition = "TEXT")
    private String files;  // This will store file URLs or identifiers

    @PrePersist
    public void prepareForInsert() {
        if (reportNumber == null || reportNumber.trim().isEmpty()) {
            reportNumber = UUID.randomUUID().toString();
        }
    }

    // Getters and Setters
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getReportedAt() { return reportedAt; }
    public void setReportedAt(Date reportedAt) { this.reportedAt = reportedAt; }
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
    public String getReportNumber() { return reportNumber; }
    public void setReportNumber(String reportNumber) { this.reportNumber = reportNumber; }
    public String getFiles() { return files; }
    public void setFiles(String files) { this.files = files; }
}
