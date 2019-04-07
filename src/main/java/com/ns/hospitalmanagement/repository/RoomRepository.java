package com.ns.hospitalmanagement.repository;

import com.ns.hospitalmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@Repository
@RepositoryRestResource(collectionResourceRel = "room", path = "room")
@CrossOrigin(origins = "http://localhost:4200")
public interface RoomRepository extends JpaRepository<Room,Long> {
}
