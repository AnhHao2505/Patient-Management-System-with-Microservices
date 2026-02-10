package myproject.example.patient_manage.mapper;

import myproject.example.patient_manage.dto.PatientRequestDTO;
import myproject.example.patient_manage.dto.PatientResponeDTO;
import myproject.example.patient_manage.model.Patient;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponeDTO toDTO(Patient patient){
        PatientResponeDTO patientDTO = new PatientResponeDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return patient;
    }
}
