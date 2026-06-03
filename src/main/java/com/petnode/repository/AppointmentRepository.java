package com.petnode.repository;

import com.petnode.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByOwnerId(String ownerId);
}
