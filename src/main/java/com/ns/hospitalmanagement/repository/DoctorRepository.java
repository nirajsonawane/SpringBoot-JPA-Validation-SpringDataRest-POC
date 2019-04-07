package com.ns.hospitalmanagement.repository;

import com.ns.hospitalmanagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@RepositoryRestResource(collectionResourceRel = "doctor", path = "doctor")
@CrossOrigin(origins = "http://localhost:4200")
public interface DoctorRepository extends JpaRepository<Doctor,Long> {


}
