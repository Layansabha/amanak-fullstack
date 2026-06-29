package com.example.amanakk_backend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private boolean success;
    private String message;
    @JsonProperty("national_id")
    private String nationalId;
    @JsonProperty("id_number")
    private String idNumber;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String message, String nationalId, String idNumber, String fullName) {
        this.success = success;
        this.message = message;
        this.nationalId = nationalId;
        this.idNumber = idNumber;
        this.fullName = fullName;
    }

    public LoginResponse(boolean success, String message, String nationalId, String idNumber, String fullName, String phoneNumber, String email) {
        this.success = success;
        this.message = message;
        this.nationalId = nationalId;
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
