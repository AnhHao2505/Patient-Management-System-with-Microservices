package myproject.example.patient_manage.controller;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import myproject.example.patient_manage.dto.PatientRequestDTO;
import myproject.example.patient_manage.dto.PatientResponeDTO;
import myproject.example.patient_manage.dto.validator.CreatePatientValidationGroup;
import myproject.example.patient_manage.model.Patient;
import myproject.example.patient_manage.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponeDTO>> getPatients(){
        List<PatientResponeDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponeDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
                                                               @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponeDTO patientResponeDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponeDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class})
                                                           @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponeDTO patientResponeDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
