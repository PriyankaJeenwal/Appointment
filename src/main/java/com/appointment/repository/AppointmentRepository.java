package com.appointment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	Optional<Appointment> findAppointmentByTitleAndUserId(String lowerCase, Long userId);

	public List<Appointment> findByuserId(Long userId);

	public List<Appointment> findAppointmentByAppointmentDate(LocalDate appointmentDate);

}
