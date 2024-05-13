package com.appointment.service;

import java.awt.print.Pageable;
import java.util.List;

import com.appointment.entity.Appointment;
import com.appointment.response.AppointmentResponse;

public interface AppointmentService {


//	public AppointmentResponse<List<Appointment>> getByUserId(Long userId);

	public AppointmentResponse<Appointment> addAppointment(Appointment appointment);

	public AppointmentResponse<Appointment> getAppointmentById(Long id);

	public AppointmentResponse<List<Appointment>> getAllAppointment(org.springframework.data.domain.Pageable paging);

	 public boolean deleteAppointmentById(Long id);

	public AppointmentResponse<List<Appointment>> getAppointmentByDisease(String disease);




	public AppointmentResponse<Appointment> updateAppointment(Appointment appointment, Long id);

	

}
