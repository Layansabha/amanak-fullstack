package com.example.amanakk_backend;

public class IdNumbersRequest {
    private String idNumber;
    private String nationalId;
    private String birthdate;

    // Default constructor for JSON parsing
    public IdNumbersRequest() {}

    public IdNumbersRequest(String idNumber, String nationalId, String birthdate) {
        this.idNumber = idNumber;
        this.nationalId = nationalId;
        this.birthdate = birthdate;
    }

    // Getters
    public String getIdNumber() {
        return idNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getBirthdate() {
        return birthdate;
    }

    // Setters
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
