package com.rabbi.patientservices.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDTO {
    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
}

//This PatientResponseDTO class is a Data Transfer
// Object used to send patient details (ID, name, email, address, date of birth) in API responses.