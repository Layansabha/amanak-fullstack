package com.example.amanakk_backend;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "id_numbers")
public class IdNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birthdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "national_id", nullable = false, unique = true)
    private String nationalId;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
