package com.rabbi.patientservices.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String address;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate registeredDate;

}

//This patients entity class in a Spring Boot application represents patient records stored in the database.
// It uses JPA annotations to define the patients table structure, with fields for patient ID, name, email, address, date of birth, and registration date.
// The id field is automatically generated as a UUID, and the email field is validated for proper format and uniqueness.
// This class ensures that all required patient information is stored with validation rules to maintain data integrity.