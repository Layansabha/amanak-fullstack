package com.example.amanakk_backend;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_accounts")
public class UserAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_number", nullable = false)
    private String idNumber;

    @Column(name = "national_id", nullable = false)
    private String nationalId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birthdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "email") // Nullable by default, no explicit setting needed
    private String email;



    @Column(name = "password", nullable = false)
    private String password;

    // Getters and setters

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
