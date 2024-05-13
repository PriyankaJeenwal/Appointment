package com.appointment.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.entity.Appointment;
import com.appointment.repository.AppointmentRepository;
import com.appointment.response.AppointmentResponse;
import com.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentControllerImpl implements AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	AppointmentRepository appointmentRepository;

	static final Logger log = LogManager.getLogger(AppointmentControllerImpl.class.getName());

	/*
	 * @Override public ResponseEntity<AppointmentResponse<List<Appointment>>>
	 * getAppointmentByUserId(Long userId) { // TODO Auto-generated method stub
	 * return null; }
	 */

	@Override
	public ResponseEntity<AppointmentResponse<Appointment>> addAppointment(Appointment appointment) {
		try {
			if (appointment.getAppintmentDate() == null || appointment.getDisease() == null
					|| appointment.getDoctorId() == null || appointment.getUserId() == null) {
				return new ResponseEntity<>(new AppointmentResponse<Appointment>(
						"Appointment not added. Required fields are missing.", false), HttpStatus.BAD_REQUEST);
			}
			log.info("adding new task : {}", appointment);

			AppointmentResponse<Appointment> appointmentResponse = appointmentService.addAppointment(appointment);

			if (appointmentResponse.isStatus()) {
				return new ResponseEntity<>(appointmentResponse, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(appointmentResponse, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Exception while adding task: {}", e.getMessage());
			return new ResponseEntity<>(
					new AppointmentResponse<>("Appointment not added. Internal Server Error", false),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<AppointmentResponse<List<Appointment>>> findAllAppointment(Integer pageSize, Integer page) {

		try {
			log.info("fetching all appointments");
			Pageable paging = PageRequest.of(page, pageSize);
			AppointmentResponse<List<Appointment>> appointmentResponse = appointmentService.getAllAppointment(paging);
			log.info("appointmentList :{} ", appointmentResponse.getData());
			if (appointmentResponse.isStatus()) {
				return new ResponseEntity<>(appointmentResponse, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(appointmentResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
			return new ResponseEntity<>(
					new AppointmentResponse<>("Appointment not added. Internal Server Error", false),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<AppointmentResponse<Appointment>> getAppointmentById(@PathVariable Long id) {
		try {
			if (id != 0 && id != null) {
				log.info("get Appointment By Id : {}", id);
				AppointmentResponse<Appointment> appointmentResponse = appointmentService.getAppointmentById(id);
				log.info("appointment :{}", appointmentResponse.getData());
				return new ResponseEntity<>(appointmentResponse, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
			return new ResponseEntity<>(
					new AppointmentResponse<>("Appointment not added. Internal Server Error", false),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<AppointmentResponse<Appointment>>(HttpStatus.NOT_FOUND);
	}

	
	  @Override
	  public ResponseEntity<AppointmentResponse<List<Appointment>>>getAppointmentByDisease(@PathVariable String disease) {
	
		  try {
				AppointmentResponse<List<Appointment>> appointmentResponse = appointmentService.getAppointmentByDisease(disease);
				if (appointmentResponse.isStatus()) {
					return new ResponseEntity<>(appointmentResponse, HttpStatus.FOUND);
				} else {
					return new ResponseEntity<>(appointmentResponse, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.info("exception : {}", e);
				return new ResponseEntity<>(new AppointmentResponse<>("Appointment not added. Internal Server Error", false),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
 
		  
		  
	  
	  }
	 

	@Override
	public ResponseEntity<String> deleteAppointmentById(Long id) {
		try {
			log.info("deleting appointment by id : {} ", id);
			if (appointmentService.deleteAppointmentById(id)) {
				return new ResponseEntity<String>("Appointment deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Appointment not with given id not exist ", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return new ResponseEntity<String>("Appointment  not exist  with given id", HttpStatus.NOT_FOUND);

	}

	
	  @Override
	  public ResponseEntity<AppointmentResponse<Appointment>>updateAppointment(Appointment appointment, Long id) { 
		  
		  try {

				log.info("updating the appointment :{}", appointment);
				AppointmentResponse<Appointment> appointmentResponse = appointmentService.getAppointmentById(id);
					return new ResponseEntity<>(appointmentResponse, HttpStatus.OK);
			} catch (Exception e) {
				log.info("exception : {}", e);
				return new ResponseEntity<>(new AppointmentResponse<>("Appointment not added. Internal Server Error", false),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
	  }
	 
}
