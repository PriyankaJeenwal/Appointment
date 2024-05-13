package com.appointment.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.appointment.entity.Appointment;
import com.appointment.repository.AppointmentDao;
import com.appointment.repository.AppointmentRepository;
import com.appointment.response.AppointmentResponse;
import com.appointment.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	AppointmentDao appointmentDao;

	private static final String ERROR_MESSAGE = "Something went wrong";
	private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	/*
	 * @Override public AppointmentResponse<List<Appointment>> getByUserId(Long
	 * userId) { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public AppointmentResponse<Appointment> addAppointment(Appointment appointment) {
		AppointmentResponse<Appointment> appointmentResponse = new AppointmentResponse<Appointment>();
		log.info("appointment :{}", appointment);
		try {
			if (appointment.getDisease() != null && appointment.getUserId() != null
					&& appointment.getAppintmentDate() != null && appointment.getDoctorId() != null) {
				Optional<Appointment> existingAppointment = appointmentRepository.findAppointmentByDiseaseAndUserId(
						appointment.getDisease().toLowerCase(), appointment.getUserId());

				log.info("existingAppointment :{}", existingAppointment);
				if (existingAppointment.isEmpty()) {
					log.info("existingAppointment is empty we can add new appointment");
					Appointment newAppointment = new Appointment();

					newAppointment.setDisease(appointment.getDisease().toLowerCase());
					newAppointment.setUserId(appointment.getUserId());
					newAppointment.setDoctorId(appointment.getDoctorId());
					newAppointment.setDescription(appointment.getDescription());
					newAppointment.setAppintmentDate(appointment.getAppintmentDate());
					newAppointment.setPreviousMedicalDocument(appointment.getPreviousMedicalDocument());

					appointmentRepository.save(newAppointment);
					List<Appointment> appointmentList = new ArrayList<Appointment>();
					appointmentList.add(newAppointment);
					appointmentResponse.setData(appointmentList);
					appointmentResponse.setMessage("appointment.getDisease().toLowerCase()success");
					appointmentResponse.setStatus(true);
					return appointmentResponse;
				}
			}

		} catch (Exception e) {
			log.error("exception in addAppointment : {}", e);
		}
		log.info("appointment already exist");
		appointmentResponse.setMessage("appointment not saved");
		appointmentResponse.setStatus(false);
		return appointmentResponse;
	}

	@Override
	public AppointmentResponse<List<Appointment>> getAllAppointment(Pageable paging) {

		AppointmentResponse<List<Appointment>> appointmentResponse = new AppointmentResponse<>();
		try {
			Page<Appointment> appointmentList = appointmentRepository.findAll(paging);
			List<Appointment> list = appointmentList.getContent();
			log.info("list of appointments:{} ", appointmentList);
			if (!list.isEmpty()) {
				log.info("appointmentList is not empty");
				appointmentResponse.setData(appointmentList);
				appointmentResponse.setMessage("success");
				appointmentResponse.setStatus(true);
				return appointmentResponse;
			}

		} catch (Exception e) {
			log.error("exception in getAllAppointment :{} ", e);
		}

		appointmentResponse.setMessage("appointment not saved");
		appointmentResponse.setStatus(false);
		return appointmentResponse;

	}

	@Override
	public AppointmentResponse<Appointment> getAppointmentById(Long id) {
		log.info("Id : {}", id);
		AppointmentResponse<Appointment> appointmentResponse = new AppointmentResponse<Appointment>();
		try {
			Optional<Appointment> appointment = appointmentRepository.findById(id);
			if (!appointment.isEmpty()) {
				Appointment appointmentObj = appointment.get();
				log.info("appointment is not empty");
				List<Appointment> appointmentList = new ArrayList<Appointment>();
				appointmentList.add(appointmentObj);
				appointmentResponse.setData(appointmentList);
				appointmentResponse.setMessage("appointment found");
				appointmentResponse.setStatus(true);
				return appointmentResponse;
			}
		} catch (Exception e) {
			log.error("exception in getAppointmentById : {}", e);
		}

		appointmentResponse.setMessage("appointment not found");
		appointmentResponse.setStatus(false);
		return appointmentResponse;

	}

	@Override
	public AppointmentResponse<List<Appointment>> getAppointmentByDisease(String disease) {

		AppointmentResponse<List<Appointment>> appointmentResponse = new AppointmentResponse<>();
		try {
			List<Appointment> list = appointmentDao.findTaskByTitle(disease);
			if (!list.isEmpty()) {
				appointmentResponse.setData(list);
				appointmentResponse.setMessage("appointment found");
				appointmentResponse.setStatus(true);
				return appointmentResponse;
			}

		} catch (Exception e) {
			log.error("exception {}", e);
		}
		appointmentResponse.setMessage("appointment not found");
		appointmentResponse.setStatus(false);
		return appointmentResponse;

	}

	@Override

	public boolean deleteAppointmentById(Long id) {

		try {
			log.info("id : {}", id);
			Optional<Appointment> appointment = appointmentRepository.findById(id);
			if (appointment.isPresent()) {
				log.info("appointment is not empty ; {}", appointment);
				appointmentRepository.deleteById(id);
				return true;
			}
		} catch (Exception e) {
			log.error("exception :{} ", e);
		}
		log.info("appointment not found with id : {}", id);
		return false;

	}

	@Override
	public AppointmentResponse<Appointment> updateAppointment(Appointment newAppointment, Long id) {
		AppointmentResponse<Appointment> appointmentResponse = new AppointmentResponse<Appointment>();
		try {
			if (id != null && newAppointment != null) {
				Optional<Appointment> appointment = appointmentRepository.findById(id);
				log.info("update appointment :{}", appointment);

				if (appointment.isPresent()) {
					log.info(" appointment is not null :{}", appointment);
					
					appointment.get().setDisease(newAppointment.getDisease());
					appointment.get().setDescription(newAppointment.getDescription());
					appointment.get().setDoctorId(newAppointment.getDoctorId());
					appointment.get().setAppintmentDate(newAppointment.getAppintmentDate());
	
					
					List<Appointment> appointmentList = new ArrayList<Appointment>();
					
						
					appointmentList.add(newAppointment);
					
appointmentRepository.saveAll(appointmentList);
					appointmentResponse.setData(appointmentList);
					appointmentResponse.setMessage("appointment updated");
					appointmentResponse.setStatus(true);
					return appointmentResponse;

				}
			}

		} catch (Exception e) {
			log.error("exception in update appointment :{} ", e);
		}
		log.info("appointment not found with id : {}", id);
		appointmentResponse.setMessage("appointment not updated");
		appointmentResponse.setStatus(false);
		return appointmentResponse;

	}

}
