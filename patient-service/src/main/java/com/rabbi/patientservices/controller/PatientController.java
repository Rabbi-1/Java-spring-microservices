package com.rabbi.patientservices.controller;

import com.rabbi.patientservices.dto.PatientRequestDTO;
import com.rabbi.patientservices.dto.PatientResponseDTO;
import com.rabbi.patientservices.dto.validators.CreatePatientValidationGroup;
import com.rabbi.patientservices.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")// http://localhost:4000/patients
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }
    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a new Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id
            ,@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
    @Operation(summary = "Delete a Patient")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
//This PatientController class handles HTTP requests related to patient data.
// Mapped to /patients, it provides:
// - A GET endpoint to retrieve all patients via PatientService, returning a list of PatientResponseDTO objects.
// - A POST endpoint to create a new patient, validating the request body (PatientRequestDTO),
// saving it through PatientService, and returning the saved patient as a PatientResponseDTO.
