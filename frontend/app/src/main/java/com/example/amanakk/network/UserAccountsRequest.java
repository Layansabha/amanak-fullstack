package com.example.amanakk.network;

public class UserAccountsRequest {
    private String phoneNumber;
    private String password;
    private String idNumber;
    private String nationalId;
    private String birthdate;


    public UserAccountsRequest() {
    }

    public UserAccountsRequest(String phoneNumber, String password, String idNumber, String nationalId, String birthdate) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.idNumber = idNumber;
        this.nationalId = nationalId;
        this.birthdate = birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "UserAccountsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", password='[PROTECTED]'" +
                ", idNumber='" + idNumber + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
