package com.rabbi.patientservices.controller;

import com.rabbi.patientservices.dto.PatientRequestDTO;
import com.rabbi.patientservices.dto.PatientResponseDTO;
import com.rabbi.patientservices.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")// http://localhost:4000/patients
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
}
//This PatientController class handles HTTP requests related to patient data.
// Mapped to /patients, it provides:
// - A GET endpoint to retrieve all patients via PatientService, returning a list of PatientResponseDTO objects.
// - A POST endpoint to create a new patient, validating the request body (PatientRequestDTO),
// saving it through PatientService, and returning the saved patient as a PatientResponseDTO.
