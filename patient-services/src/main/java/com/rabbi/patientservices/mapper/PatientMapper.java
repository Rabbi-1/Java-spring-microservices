package com.rabbi.patientservices.mapper;

import com.rabbi.patientservices.dto.PatientRequestDTO;
import com.rabbi.patientservices.dto.PatientResponseDTO;
import com.rabbi.patientservices.model.Patient;

import java.time.LocalDate;

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

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }
}
//This `PatientMapper` class converts between `Patient` entities and DTOs.
// - `toDto` maps a `Patient` entity to a `PatientResponseDTO` for API responses, formatting values as strings.
// - `toModel` maps a `PatientRequestDTO` to a `Patient` entity for database storage
