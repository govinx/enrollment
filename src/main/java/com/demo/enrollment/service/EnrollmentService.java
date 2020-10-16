package com.demo.enrollment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.enrollment.entity.Dependent;
import com.demo.enrollment.entity.Enrollee;
import com.demo.enrollment.repository.DependentRepository;
import com.demo.enrollment.repository.EnrolleeRepository;


@Service
public class EnrollmentService {
	
	@Autowired
	private EnrolleeRepository enrolleeRepository;
	
	@Autowired
	private DependentRepository dependentRepository;
	

	public Optional<Enrollee> getEnrollee (Long enrolleeId)
	{
		return enrolleeRepository.findById(enrolleeId);
	}
	
	public Enrollee addEnrollee (Enrollee enrollee)
	{
		return enrolleeRepository.save(enrollee);
	}
	
	public Enrollee updateEnrollee (Enrollee enrollee)
	{
		return enrolleeRepository.save(enrollee);
	}

	public Enrollee deleteEnrollee (Long enrolleeId)
	{
		Enrollee enrollee = getEnrollee(enrolleeId).get();
		for (Dependent dependent : enrollee.getDependents())
		{
			Long dependentId = dependent.getId();
			dependentRepository.deleteById(dependentId);
		}
		enrolleeRepository.deleteById(enrolleeId);
		return enrollee;
	}

	public Dependent getDependent (Long enrolleeId, Long dependentId)
	{
		Enrollee enrollee = getEnrollee (enrolleeId).get();
		Dependent dependent = enrollee.getDependentById(dependentId);
		return dependent;
	}
	
	public Enrollee addDependent (Long enrolleeId, Dependent dependent)
	{
		Optional<Enrollee> enrollee = getEnrollee (enrolleeId);
		dependent.setEnrollee(enrollee.get());
		enrollee.get().addDependent(dependent);
		return enrolleeRepository.save(enrollee.get());
	}
	
	public Dependent updateDependent (Long enrolleeId, Long dependentId, Dependent dependent)
	{
		Enrollee enrollee = getEnrollee (enrolleeId).get();
		Dependent updatedDependent = enrollee.getDependentById(dependentId);
		updatedDependent.setFirstName(dependent.getFirstName());
		updatedDependent.setLastName(dependent.getLastName());
		updatedDependent.setDateOfBirth(dependent.getDateOfBirth());
		
		enrolleeRepository.save(enrollee);
		return updatedDependent;
	}
	
	public Enrollee deleteDependent (Long enrolleeId, Long dependentId)
	{
		Enrollee enrollee = getEnrollee (enrolleeId).get();
		enrollee.removeDependent(dependentId);
		enrollee = enrolleeRepository.save(enrollee);
		dependentRepository.deleteById(dependentId);
		return enrollee;
	}
}
