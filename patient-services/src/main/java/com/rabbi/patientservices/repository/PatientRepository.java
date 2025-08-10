package com.rabbi.patientservices.repository;

import com.rabbi.patientservices.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

}
//This PatientRepository interface in a Spring Boot application provides data access operations for the Patient entity.
// It extends JpaRepository, enabling built-in CRUD (Create, Read, Update, Delete) functionality and database query methods without manual SQL.
// The repository works with UUID as the primary key type for patients.