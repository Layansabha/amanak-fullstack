package com.example.amanakk_backend;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LoginRequest {
    @JsonAlias({"phoneNumber", "nationalId", "id_or_phone", "idOrPhone"})
    private String idOrPhone;  // This holds either the phone number or national ID
    private String password;

    public LoginRequest() {}

    public LoginRequest(String idOrPhone, String password) {
        this.idOrPhone = idOrPhone;
        this.password = password;
    }

    // Getter for idOrPhone
    public String getIdOrPhone() {
        return idOrPhone;
    }

    // Setter for idOrPhone (if needed)
    public void setIdOrPhone(String idOrPhone) {
        this.idOrPhone = idOrPhone;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password (if needed)
    public void setPassword(String password) {
        this.password = password;
    }
}
