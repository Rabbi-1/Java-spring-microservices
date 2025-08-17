package com.rabbi.patientservices.service;

import com.rabbi.patientservices.dto.PatientRequestDTO;
import com.rabbi.patientservices.dto.PatientResponseDTO;
import com.rabbi.patientservices.exception.EmailAlreadyExistsException;
import com.rabbi.patientservices.exception.PatientNotFoundException;
import com.rabbi.patientservices.grpc.BillingServiceGrpcClient;
import com.rabbi.patientservices.mapper.PatientMapper;
import com.rabbi.patientservices.model.Patient;
import com.rabbi.patientservices.repository.PatientRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists"
                            + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName(), newPatient.getEmail());

        return PatientMapper.toDto(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID:"+ id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists"
                            + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }



}
//This PatientService class centralizes patient business logic.
// It uses PatientRepository for persistence and PatientMapper to convert between entities and DTOs.
// - getPatients(): fetches all patients and returns them as PatientResponseDTO list.
// - createPatient(..): checks email uniqueness (existsByEmail), maps PatientRequestDTO → Patient, saves, and returns a PatientResponseDTO; throws EmailAlreadyExistsException if duplicate.
// - updatePatient(id, ..): loads patient by ID or throws PatientNotFoundException, enforces email uniqueness (existsByEmailAndIdNot), updates fields (parsing date strings to LocalDate), saves, and returns the updated DTO.
// By returning DTOs only, it keeps API responses clean and applies validation/rules in one place.