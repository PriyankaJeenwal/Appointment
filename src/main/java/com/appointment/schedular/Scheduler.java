package com.appointment.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.appointment.entity.Appointment;
import com.appointment.repository.AppointmentRepository;


@Component
public class Scheduler {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Scheduled(fixedRate = 60000)
	public void scheduleNotification() {
		System.out.println("scheduler");
		LocalDateTime currentTime = LocalDateTime.now();

		List<Appointment>appointmentList = appointmentRepository.findAll();
		for (Appointment appointment : appointmentList) {

			System.out.println(currentTime);
			LocalDateTime appointmentDate = appointment.getAppointmentDate();
			System.out.println(appointmentDate);
			System.out.println(appointment.getAppointmentDate());

			if (currentTime.withSecond(0).withNano(0).equals(appointmentDate.minusHours(1).withSecond(0).withNano(0))) {
				System.out.println("your appointment" + appointment.getDisease() + " is near complition date");
			}
		}
	}

}
