package myproject.example.patient_manage.service;

import myproject.example.patient_manage.dto.PatientRequestDTO;
import myproject.example.patient_manage.dto.PatientResponeDTO;
import myproject.example.patient_manage.exception.EmailAlreadyExistsException;
import myproject.example.patient_manage.exception.PatientNotFoundException;
import myproject.example.patient_manage.mapper.PatientMapper;
import myproject.example.patient_manage.model.Patient;
import myproject.example.patient_manage.repository.PatientRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponeDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
    }

    public PatientResponeDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists" + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponeDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists" + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatePatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatePatient);
    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).
                orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        patientRepository.deleteById(id);
    }

}
