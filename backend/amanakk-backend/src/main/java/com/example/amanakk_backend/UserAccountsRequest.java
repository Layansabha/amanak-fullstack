package com.example.amanakk_backend;

import java.util.Date;

public class UserAccountsRequest {

    private String phoneNumber;
    private String password;
    private String idNumber;
    private String nationalId;
    private Date birthdate;

    // Default constructor for JSON serialization/deserialization
    public UserAccountsRequest() {
    }

    // All-args constructor
    public UserAccountsRequest(String phoneNumber, String password, String idNumber, String nationalId, Date birthdate) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.idNumber = idNumber;
        this.nationalId = nationalId;
        this.birthdate = birthdate;
    }

    // Getters and setters
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
}
