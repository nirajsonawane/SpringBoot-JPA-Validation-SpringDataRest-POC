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
public class PatientResource_RequestValidationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    public void whenPostPatient_validateName()
            throws Exception {

        Patient patient = getPatient(1l,"");
        Mockito.when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
        String s = new ObjectMapper().writeValueAsString(patient);
        System.out.println(s);

        mvc.perform(MockMvcRequestBuilders
                .post("/patient")
                .content(s)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


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
