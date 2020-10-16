package com.demo.enrollment.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;



@Entity
@Table(name = "dependent")
@Data
public class Dependent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotEmpty(message = "firstName is required field.")
	private String firstName;

	@Column(nullable = false)
	@NotEmpty(message = "lastName is required field.")
	private String lastName;	
	
	@Column(nullable = false)
	@NotNull(message = "dateOfBirth is required field.")
	private LocalDate dateOfBirth;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Enrollee enrollee;
	
	
	public Dependent() {
		
	}
	
	public Dependent(String firstName, String lastName, LocalDate dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Dependent [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + "]";
	}


	
	
}
