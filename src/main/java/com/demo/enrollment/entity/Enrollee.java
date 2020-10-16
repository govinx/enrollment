package com.demo.enrollment.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Entity
@Table(name = "enrollee")
@Data
public class Enrollee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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


	@Column(nullable = false)
	private boolean activationStatus;

	@Column
	private String phoneNumber;
	
	@OneToMany(mappedBy = "enrollee", fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
	List<Dependent> dependents = new ArrayList<>();
	
	public Enrollee() {
		
	}
	
	public Enrollee(String firstName, String lastName, LocalDate dateOfBirth, boolean activationStatus, String phoneNumber) {
		this.firstName= firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.activationStatus = activationStatus;
		this.phoneNumber = phoneNumber;
	}

	public Dependent getDependentById (Long dependentId)
	{
		for (Dependent dependent : dependents)
		{
			if (dependent.getId().equals(dependentId))
			{
				return dependent;
			}
		}
		return null;
	}
	
	public void addDependent (Dependent dependent)
	{
		this.dependents.add(dependent);
	}
	
	public Dependent removeDependent (Long dependentId)
	{
		Iterator<Dependent> itr = dependents.iterator();
		while (itr.hasNext())
		{
			Dependent dependent = itr.next();
			if (dependent.getId().equals(dependentId))
			{
				itr.remove();
				return dependent;
			}
		}
		return null;
	}
	
	public void removeAllDependent ()
	{
		Iterator<Dependent> itr = dependents.iterator();
		while (itr.hasNext())
		{
			itr.next();
			itr.remove();
		}
	}

	@Override
	public String toString() {
		return "Enrollee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", activationStatus=" + activationStatus + ", phoneNumber=" + phoneNumber
				+ ", dependents=" + dependents 
				+ "]";
	}

}

