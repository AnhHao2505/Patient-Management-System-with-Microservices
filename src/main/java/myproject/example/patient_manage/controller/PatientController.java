package myproject.example.patient_manage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import myproject.example.patient_manage.dto.PatientRequestDTO;
import myproject.example.patient_manage.dto.PatientResponeDTO;
import myproject.example.patient_manage.dto.validator.CreatePatientValidationGroup;
import myproject.example.patient_manage.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponeDTO>> getPatients(){
        List<PatientResponeDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponeDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
                                                               @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponeDTO patientResponeDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a new patient")
    public ResponseEntity<PatientResponeDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class})
                                                           @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponeDTO patientResponeDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponeDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
