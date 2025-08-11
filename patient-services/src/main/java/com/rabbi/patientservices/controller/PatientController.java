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
//This PatientController class handles incoming HTTP requests for patient data.
// Mapped to /patients, it defines a GET endpoint that retrieves all patients via PatientService.
// The data is returned as a list of PatientResponseDTO objects wrapped in a ResponseEntity for proper HTTP response handling.