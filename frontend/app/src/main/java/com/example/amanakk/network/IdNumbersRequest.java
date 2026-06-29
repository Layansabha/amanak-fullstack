package com.example.amanakk.network;

public class IdNumbersRequest {
    private String idNumber;
    private String nationalId;
    private String birthdate;

    public IdNumbersRequest(String idNumber, String nationalId, String birthdate) {
        this.idNumber = idNumber;
        this.nationalId = nationalId;
        this.birthdate = birthdate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getBirthdate() {
        return birthdate;
    }
}
