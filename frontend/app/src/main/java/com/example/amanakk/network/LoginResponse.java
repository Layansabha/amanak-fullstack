package com.example.amanakk.network;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private boolean success;  // This will hold whether login was successful
    private String message;   // This will hold the message from the backend (e.g., "Login successful" or "Incorrect password")
    @SerializedName(value = "national_id", alternate = {"nationalId"})
    private String nationalId; // Added to hold the National ID of the user, if login is successful
    @SerializedName(value = "full_name", alternate = {"fullName"})
    private String fullName;
    @SerializedName(value = "id_number", alternate = {"idNumber"})
    private String idNumber;
    @SerializedName(value = "phone_number", alternate = {"phoneNumber"})
    private String phoneNumber;

    // Getter for success
    public boolean isSuccess() {
        return success;
    }

    // Setter for success
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for nationalId
    public String getNationalId() {
        return nationalId;
    }

    // Setter for nationalId
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
