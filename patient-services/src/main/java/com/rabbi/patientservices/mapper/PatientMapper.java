package com.rabbi.patientservices.mapper;

import com.rabbi.patientservices.dto.PatientResponseDTO;
import com.rabbi.patientservices.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDto(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }
}
//This PatientMapper class converts a Patient entity into a PatientResponseDTO.
// It extracts patient details and formats them as strings for API responses.