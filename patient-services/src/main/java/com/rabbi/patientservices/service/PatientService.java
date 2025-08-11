package com.rabbi.patientservices.service;

import com.rabbi.patientservices.dto.PatientRequestDTO;
import com.rabbi.patientservices.dto.PatientResponseDTO;
import com.rabbi.patientservices.mapper.PatientMapper;
import com.rabbi.patientservices.model.Patient;
import com.rabbi.patientservices.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDto).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDto(newPatient);
    }


}
//This PatientService class handles business logic for retrieving patient data.
// It uses PatientRepository to fetch all patient records from the database,
// then maps each Patient entity to a PatientResponseDTO using PatientMapper.
// The service returns a list of DTOs, ensuring the API only exposes necessary patient information.
// It also handles creating new patients by mapping a PatientRequestDTO
// to a Patient entity, saving it to the database, and returning the saved record as a DTO.