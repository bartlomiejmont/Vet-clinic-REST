package com.bartmont.VetClinicREST.repository;

import com.bartmont.VetClinicREST.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
