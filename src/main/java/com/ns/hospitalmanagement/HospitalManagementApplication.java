package com.ns.hospitalmanagement;

import com.ns.hospitalmanagement.entity.*;
import com.ns.hospitalmanagement.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }


    @Bean
    @Profile("!test")
    ApplicationRunner init(PatientRepository repository, DoctorRepository doctorRepository, RoomRepository roomRepository, PatientProcedureRepository studyRepository) {
        return (ApplicationArguments args) ->  dataSetup(repository, doctorRepository, roomRepository, studyRepository);
    }



    public void dataSetup(PatientRepository repository, DoctorRepository doctorRepository, RoomRepository roomRepository, PatientProcedureRepository studyRepository){

        repository.saveAll(getDummyPatients());
        List<Patient> allPatients = repository.findAll();
        doctorRepository.saveAll(getDoctors());
        roomRepository.saveAll(getRooms(5));

        List<Doctor> doctorList = doctorRepository
                .findAll();
        List<Room> allRooms = roomRepository.findAll();

        PatientProcedure studyPojo = PatientProcedure
                .builder()
                .description("Regular Consultation")
                .status(ProcedureStatus.PLANNED)
                .patient(allPatients.get(0))
                .startTime(LocalDateTime.now())
                .doctor(doctorList.get(0))
                .room(allRooms.get(0))
                .procedureType(ProcedureType.CONSULTATION)
                .build();

        studyRepository.save(studyPojo);

        PatientProcedure studyPojo1 = PatientProcedure
                .builder()
                .description("Knee Surgery")
                .status(ProcedureStatus.FINISHED)
                .patient(allPatients.get(1))
                .startTime(LocalDateTime.now())
                .doctor(doctorList.get(1))
                .room(allRooms.get(1))
                .procedureType(ProcedureType.OPERATION)
                .build();
        studyRepository.save(studyPojo1);
    }

    private List<Doctor> getDoctors() {
        List<String> list = Arrays.asList("Ultron","Red Skull","Loki");
        List<Doctor> doctorList = new ArrayList<>();
        IntStream
                .range(0, list.size())
                .forEach(it ->
                    doctorList.add(Doctor
                            .builder()
                            .name(list.get(it))
                            .build())
                );
        return doctorList;
    }

    private List<Room> getRooms(int nuumbers) {
        List<Room> list = new ArrayList<>();
        IntStream
                .range(1, nuumbers)
                .forEach(it ->
                    list.add(Room
                            .builder()
                            .roomName("Room Number " + it)
                            .build())
                );
        return list;
    }
    private List<Patient> getDummyPatients(){
        List<Patient> list = new ArrayList<>();

        Patient p = Patient.builder()
                .name("Iron Man")
                .sex(Gender.MALE)
                .dateOfBirth(LocalDate.now().minusYears(32).minusDays(25))
                .build();
        Patient p1 = Patient
                .builder()
                .name("Captain America")
                .sex(Gender.MALE)
                .dateOfBirth(LocalDate.now().minusYears(37).minusDays(45))
                .build();
        Patient p2 = Patient
                .builder()
                .name("Black Widow")
                .sex(Gender.FEMALE)
                .dateOfBirth(LocalDate.now().minusYears(37).minusDays(45))
                .build();
        Patient p3 = Patient
                .builder()
                .name("Hulk")
                .sex(Gender.MALE)
                .dateOfBirth(LocalDate.now().minusYears(32).minusDays(4))
                .build();
        list.add(p);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        return  list;


    }

}
