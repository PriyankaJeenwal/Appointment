package com.appointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	Optional<Appointment> findAppointmentByDiseaseAndUserId(String lowerCase, Long userId); 

	

}
