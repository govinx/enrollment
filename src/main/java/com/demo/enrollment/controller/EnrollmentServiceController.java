package com.demo.enrollment.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.enrollment.entity.Dependent;
import com.demo.enrollment.entity.Enrollee;
import com.demo.enrollment.service.EnrollmentService;



@RestController
@RequestMapping("/api/v1/enrollment")
public class EnrollmentServiceController {

	@Autowired 
	private EnrollmentService enrollmentService;

	@GetMapping(path = "/enrollee/{enrolleeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Enrollee> getEnrollee(@PathVariable("enrolleeId") Long enrolleeId) {
		try {
			
			Enrollee enrolle = enrollmentService.getEnrollee(enrolleeId).get();
			return ResponseEntity.ok(enrolle);
			
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(path = "/enrollee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Enrollee> addEnrollee (@Validated @RequestBody Enrollee enrollee) {
		
		enrollee = enrollmentService.addEnrollee(enrollee);
		return ResponseEntity.ok(enrollee);
	}

	@PutMapping(path = "/enrollee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Enrollee> updateEnrollee (@Validated @RequestBody Enrollee enrollee) {
		
		Enrollee enrolle = enrollmentService.updateEnrollee(enrollee);
		return ResponseEntity.ok(enrolle);
	}

	@DeleteMapping(path = "/enrollee/{enrolleeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Enrollee> deleteEnrollee (@PathVariable("enrolleeId") Long enrolleeId) {
		
		Enrollee enrolle = enrollmentService.deleteEnrollee(enrolleeId);
		return ResponseEntity.ok(enrolle);
	}
	
	///// Dependent
	
	@GetMapping(path = "/dependent/{enrolleeId}/{dependentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dependent> getDependent (@PathVariable("enrolleeId") Long enrolleeId, @PathVariable("dependentId") Long dependentId) {
		
		Dependent dependent = enrollmentService.getDependent(enrolleeId, dependentId);
		if (dependent != null) {
			return ResponseEntity.ok(dependent);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	

	@PostMapping(path = "/dependent/{enrolleeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Enrollee> addDependent (@PathVariable("enrolleeId") Long enrolleeId, @Validated @RequestBody Dependent dependent) {
		
		Enrollee enrolle = enrollmentService.addDependent(enrolleeId, dependent);
		return ResponseEntity.ok(enrolle);
	}

	@PutMapping(path = "/dependent/{enrolleeId}/{dependentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dependent> updateDependent (@PathVariable("enrolleeId") Long enrolleeId, @PathVariable("dependentId") Long dependentId, @Validated @RequestBody Dependent dependent) {
		
		dependent = enrollmentService.updateDependent(enrolleeId, dependentId, dependent);
		return ResponseEntity.ok(dependent);
	}


	@DeleteMapping(path = "/dependent/{enrolleeId}/{dependentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Enrollee> deleteDependent (@PathVariable("enrolleeId") Long enrolleeId, @PathVariable("dependentId") Long dependentId) {
		
		Enrollee enrollee = enrollmentService.deleteDependent(enrolleeId, dependentId);
		return ResponseEntity.ok(enrollee);
	}

}
