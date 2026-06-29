package com.example.amanakk.network;

/**
 * Represents the login request with the user's ID or phone number and password.
 */
public class LoginRequest {
    private String idOrPhone;
    private String password;

    /**
     * Constructs a new login request.
     *
     * @param idOrPhone the ID or phone number of the user
     * @param password the password of the user
     */
    public LoginRequest(String idOrPhone, String password) {
        this.idOrPhone = idOrPhone;
        this.password = password;
    }

    // Getter for the ID or phone number
    public String getIdOrPhone() {
        return idOrPhone;
    }

    // Setter for the ID or phone number
    public void setIdOrPhone(String idOrPhone) {
        this.idOrPhone = idOrPhone;
    }

    // Getter for the password
    public String getPassword() {
        return password;
    }

    // Setter for the password
    public void setPassword(String password) {
        this.password = password;
    }
}