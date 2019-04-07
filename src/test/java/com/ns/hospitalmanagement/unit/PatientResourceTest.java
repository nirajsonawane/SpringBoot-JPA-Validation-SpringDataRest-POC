package com.ns.hospitalmanagement.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.hospitalmanagement.entity.Gender;
import com.ns.hospitalmanagement.entity.Patient;
import com.ns.hospitalmanagement.repository.PatientRepository;
import com.ns.hospitalmanagement.resource.PatientResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientResource.class)
@ActiveProfiles("test")
public class PatientResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    public void givenPatient_whenGetPatient_thenReturnJsonArray()
            throws Exception {
        Patient patient = getPatient(1l,"Test");
        Patient patient1 = getPatient(2l,"Test2");
        List<Patient> list = Arrays.asList(patient,patient1);
        Mockito.when(patientRepository.findAll()).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders
           .get("/patient")
           .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));

    }
    @Test
    public void givenPatient_whenGetPatientWithId_thenReturnJsonPatient()
            throws Exception {

        Patient patient = getPatient(1l,"Test");
        Optional<Patient> OptionlaPatient = Optional.of(patient);


        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(OptionlaPatient);


        mvc.perform(MockMvcRequestBuilders
                .get("/patient/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sex").value("MALE"));

    }
    @Test
    public void givenPatient_whenDeletePatientWithId_thenDeletePatient() throws Exception {

    Mockito.doNothing().when(patientRepository).deleteById(Mockito.anyLong());

        Patient patient = getPatient(1l,"Test");
        mvc.perform(MockMvcRequestBuilders
                .delete("/patient/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


    }

    @Test
    public void givenNoPatient_whenGetPatientWithId_thenReturnResourceNotFound()
            throws Exception {

        Optional<Patient> OptionlaPatient = Optional.ofNullable(null);
        Mockito
                .when(patientRepository.findById(Mockito.anyLong())).thenReturn(OptionlaPatient);

        mvc.perform(MockMvcRequestBuilders
                .get("/patient/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }

    @Test
    public void whenPostPatient_thenShuldCreatResource()
            throws Exception {

        Patient patient = getPatient(1l,"Test");
        Mockito.when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
        String s = new ObjectMapper().writeValueAsString(patient);
        System.out.println(s);

        mvc.perform(MockMvcRequestBuilders
                .post("/patient")
                .content(s)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    private Patient getPatient(Long id,String name) {
        return Patient
                .builder()
                .id(id)
                .sex(Gender.MALE)
                .name(name)
                .build();
    }
}
