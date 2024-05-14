package com.appointment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.appointment.entity.Appointment;
import com.appointment.response.AppointmentResponse;

public interface AppointmentController {

	@GetMapping("/user/{userId}")
	public ResponseEntity<AppointmentResponse<List<Appointment>>>getByUserId( Long userId);
	@PostMapping("/addAppointment")
	public ResponseEntity<AppointmentResponse<Appointment>> addAppointment(@RequestBody Appointment appointment);

	@GetMapping("/getAll")
	public ResponseEntity<AppointmentResponse<List<Appointment>>> findAllAppointment(
			@RequestParam(defaultValue = "5", required = false) Integer pageSize,

			@RequestParam(defaultValue = "0", required = false) Integer page);

	@GetMapping("getAppointmentById/{id}")
	public ResponseEntity<AppointmentResponse<Appointment>> getAppointmentById(@PathVariable Long id);

	@GetMapping("/getByDisease/{disease}")
	public ResponseEntity<AppointmentResponse<List<Appointment>>> getAppointmentByDisease(@PathVariable String disease);

	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id);

//	@PostMapping("/update/{id}")
//	public ResponseEntity<AppointmentResponse<Appointment>> updateAppointment(@RequestBody Appointment appointment,@PathVariable Long id);

}
