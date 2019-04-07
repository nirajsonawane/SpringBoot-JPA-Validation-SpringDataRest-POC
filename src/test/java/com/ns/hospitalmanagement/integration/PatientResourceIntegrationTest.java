package com.ns.hospitalmanagement.integration;

import com.ns.hospitalmanagement.HospitalManagementApplication;
import com.ns.hospitalmanagement.entity.*;
import com.ns.hospitalmanagement.repository.DoctorRepository;
import com.ns.hospitalmanagement.repository.PatientProcedureRepository;
import com.ns.hospitalmanagement.repository.PatientRepository;
import com.ns.hospitalmanagement.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-ittest.properties")
public class PatientResourceIntegrationTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PatientProcedureRepository patientProcedureRepository;



    @Autowired
    private MockMvc mvc;

    @Before
    public void setup(){
        patientRepository.deleteAll();

    }

    @Test
    public  void OnPostRequest_shouldAddPatientToDatabase(){

    String post = "{\"name\":\"Niraj\",\"dateOfBirth\":\"2019-04-08T18:30:00.000Z\",\"sex\":\"MALE\"}";
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .post("/patient")
                            .content(post)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
            List<Patient> all = patientRepository.findAll();
            Assert.assertEquals(1,all.size());
            Assert.assertEquals("Niraj",all.get(0).getName());
            Assert.assertEquals(Gender.MALE,all.get(0).getSex());
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public  void OnDeleteRequest_shouldDeletePatientaAndProceduresForPatient(){

        Patient p = patientRepository.save(Patient.builder().name("Niraj").sex(Gender.MALE).dateOfBirth(LocalDate.now()).build());
        Room room = roomRepository.save(Room.builder().roomName("Test Room").build());
        Doctor doctor = doctorRepository.save(Doctor.builder().name("Test Doctor").build());
        PatientProcedure patientProcedure = patientProcedureRepository.save(PatientProcedure.builder().patient(p).description("Test").procedureType(ProcedureType.FOLLOW_UP).status(ProcedureStatus.PLANNED).startTime(LocalDateTime.now()).build());



        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .delete("/patient/"+p.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
            List<Patient> all = patientRepository.findAll();
            Assert.assertEquals(0,all.size());
           Assert.assertEquals(0,patientProcedureRepository.findAll().size());

        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public  void shouldReturnAllProceduresForPatient_OneToManyMappingTest(){

        Patient p = patientRepository.save(Patient.builder().name("Niraj").sex(Gender.MALE).dateOfBirth(LocalDate.now()).build());
        Room room = roomRepository.save(Room.builder().roomName("Test Room").build());
        Doctor doctor = doctorRepository.save(Doctor.builder().name("Test Doctor").build());
        PatientProcedure patientProcedure = patientProcedureRepository.save(PatientProcedure.builder().patient(p).description("Test").procedureType(ProcedureType.FOLLOW_UP).status(ProcedureStatus.PLANNED).startTime(LocalDateTime.now()).build());
        PatientProcedure patientProcedure1 = patientProcedureRepository.save(PatientProcedure.builder().patient(p).description("Test1").procedureType(ProcedureType.FOLLOW_UP).status(ProcedureStatus.PLANNED).startTime(LocalDateTime.now()).build());

        Optional<Patient> fromDb = patientRepository.findById(p.getId());
        Assert.assertTrue(fromDb.isPresent());
        Assert.assertEquals(fromDb.get().getPatientProcedure().size(),2);

    }
}
