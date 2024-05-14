package com.appointment.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment_table")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id")
	private Long id;

	@Column(name = "disease")
	private String disease;

	@Column(name = "doctorName")
	private String doctorName;

	@Column(name = "appointmentDate")
	private LocalDateTime appointmentDate;

	@Column(name = "userId")
	private Long userId;

	@Column(name = "doctorId")
	private Long doctorId;

	@Column(name = "description")
	private String description;

	@Column(name = "previousMedicalDocument")
	private String previousMedicalDocument;

}
