package com.demo.enrollment;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.enrollment.entity.Dependent;
import com.demo.enrollment.entity.Enrollee;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class EnrollmentServiceControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int localServerPort;

	private boolean isDebug = log.isDebugEnabled();	
	
	private String getBaseUrl() {
		return "http://localhost:" + localServerPort + "/api/v1/enrollment";
	}
	
    private String getEnrolleeUrl() {
        return getBaseUrl()+"/enrollee";
    }
    
    private String getDependentUrl() {
        return getBaseUrl()+"/dependent";
    }	

	private Enrollee createEnrollee () {
		String firstName = "John";
		String lastName = "Doe-" + System.currentTimeMillis();
		int age = 66;
		LocalDate dob = LocalDate.now().minusYears(age);
		Enrollee enrollee = new Enrollee (firstName, lastName, dob, true, "555-888-1234");
		return enrollee;
	}

	private Dependent createDependent () {
		String firstName = "Jane";
		String lastName = "Doe-" + System.currentTimeMillis();
		int age = 56;
		LocalDate dob = LocalDate.now().minusYears(age);
		Dependent dependent = new Dependent (firstName, lastName, dob);
		return dependent;
	}

	
	private ResponseEntity<Enrollee> addEnrollee () {

		String url = getEnrolleeUrl();
		Enrollee enrollee = createEnrollee();

		HttpEntity<Enrollee> entity = new HttpEntity<Enrollee>(enrollee);
		ResponseEntity<Enrollee> respEntity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(respEntity.getBody()).isNotNull();
		assertThat(respEntity.getBody().getId()).isNotNull();
		
		return respEntity;
	}

	private ResponseEntity<Enrollee> addEnrolleeWithDependent () {

		Enrollee enrollee =  addEnrollee ().getBody();
		
		String url = getDependentUrl() + "/" + enrollee.getId();
		
		Dependent dependent = createDependent();

		HttpEntity<Dependent> entity = new HttpEntity<Dependent>(dependent);
		ResponseEntity<Enrollee> respEntity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(respEntity.getBody()).isNotNull();
		assertThat(respEntity.getBody().getId()).isNotNull();
		
		Enrollee enrolleeWithDependent = respEntity.getBody();
		
		assertThat(enrolleeWithDependent.getDependents()).isNotNull();
		assertThat(enrolleeWithDependent.getDependents().size()).isGreaterThan(0);
		return respEntity;
	}	
	
	
	@Test
	public void testGetEnrollee() {
		
		Enrollee enrollee = addEnrollee().getBody();
		
		String url = getEnrolleeUrl() + "/" + enrollee.getId();
		
		ResponseEntity<Enrollee> entity = testRestTemplate.getForEntity(url, Enrollee.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isNotNull();
		assertThat(entity.getBody().getId()).isNotNull();
		assertThat(entity.getBody().getId()).isEqualTo(enrollee.getId());
	}

	@Test
	public void testAddEnrollee() {
		
		ResponseEntity<Enrollee> entity = addEnrollee ();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().getId()).isNotNull();
		if(isDebug) log.debug("StatusCode: "+entity.getStatusCode());
	}

	@Test
	public void testUpdateEnrollee() {
		String url = getEnrolleeUrl();

		Enrollee enrollee =  addEnrollee ().getBody();
		String originalFirstName = enrollee.getFirstName();
		String updatedFirstName = originalFirstName + "Upd";
		enrollee.setFirstName(updatedFirstName);

		HttpEntity<Enrollee> entity = new HttpEntity<Enrollee>(enrollee);
		ResponseEntity<Enrollee> respEntity = testRestTemplate.exchange(url, HttpMethod.PUT, entity, Enrollee.class);
		
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(respEntity.getBody()).isNotNull();
		assertThat(respEntity.getBody().getId()).isEqualTo(entity.getBody().getId());
		assertThat(respEntity.getBody().getFirstName()).isNotEqualTo(originalFirstName);
	}
	
	@Test
	public void testDeleteEnrollee() {

		Enrollee enrollee =  addEnrollee ().getBody();

		String url = getEnrolleeUrl() + "/" + enrollee.getId();
		testRestTemplate.delete(url);

		ResponseEntity<Enrollee> entity = testRestTemplate.getForEntity(url, Enrollee.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testAddDependent() {

		addEnrolleeWithDependent ();
	}


	@Test
	public void testUpdateDependent () {

		Enrollee enrollee = addEnrolleeWithDependent ().getBody();
		
		Dependent dependent = enrollee.getDependents().get(0);
		
		String url = getDependentUrl() + "/" + enrollee.getId() + "/" + dependent.getId();

		String originalFirstName = dependent.getFirstName();
		String updatedFirstName = originalFirstName + "Upd";
		dependent.setFirstName(updatedFirstName);

		HttpEntity<Dependent> entity = new HttpEntity<Dependent>(dependent);
		ResponseEntity<Dependent> respEntity = testRestTemplate.exchange(url, HttpMethod.PUT, entity, Dependent.class);
		
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(respEntity.getBody()).isNotNull();
		assertThat(respEntity.getBody().getId()).isEqualTo(entity.getBody().getId());
		assertThat(respEntity.getBody().getFirstName()).isNotEqualTo(originalFirstName);
	}

	@Test
	public void testDeleteDependent () {

		Enrollee enrollee = addEnrolleeWithDependent ().getBody();
		
		Dependent dependent = enrollee.getDependents().get(0);
		
		String url = getDependentUrl() + "/" + enrollee.getId() + "/" + dependent.getId();

		testRestTemplate.delete(url);


		ResponseEntity<Dependent> respEntity = testRestTemplate.getForEntity(url, Dependent.class);
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(respEntity.getBody()).isNull();
	}

	@Test
	public void testGetEnrolleeNegative () {
		
		// Not exists
		String url = getEnrolleeUrl() + "/" + "99999999"; // Random Id
		
		ResponseEntity<Enrollee> entity = testRestTemplate.getForEntity(url, Enrollee.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isNull();
	}
	
	
	@Test
	public void testAddEnrolleeNegative () {
		
		String url = getEnrolleeUrl();
		
		Enrollee enrollee = createEnrollee();
		
		// No First Name
		enrollee.setFirstName(null);
		HttpEntity<Enrollee> entity = new HttpEntity<Enrollee>(enrollee);
		ResponseEntity<Enrollee> respentity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		assertThat(respentity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(respentity.getBody().getId()).isNull();
		
		// No Last Name
		enrollee = createEnrollee();
		enrollee.setLastName(null);
		entity = new HttpEntity<Enrollee>(enrollee);
		respentity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		assertThat(respentity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(respentity.getBody().getId()).isNull();
		
		// No DOB
		enrollee = createEnrollee();
		enrollee.setDateOfBirth(null);
		entity = new HttpEntity<Enrollee>(enrollee);
		respentity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		assertThat(respentity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(respentity.getBody().getId()).isNull();
		
	}
	

	@Test
	public void testAddDependentNegative () {

		
		Enrollee enrollee =  addEnrollee ().getBody();
		
		String url = getDependentUrl() + "/" + enrollee.getId();

		// No First Name
		Dependent dependent = createDependent();
		dependent.setFirstName(null);
		HttpEntity<Dependent> entity = new HttpEntity<Dependent>(dependent);
		ResponseEntity<Enrollee> respEntity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(respEntity.getBody().getDependents().size()).isEqualTo(0);
		
		// No Last Name
		dependent = createDependent();
		dependent.setLastName(null);
		entity = new HttpEntity<Dependent>(dependent);
		respEntity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(respEntity.getBody().getDependents().size()).isEqualTo(0);
		
		// No DOB
		dependent = createDependent();
		dependent.setDateOfBirth(null);
		entity = new HttpEntity<Dependent>(dependent);
		respEntity = testRestTemplate.exchange(url, HttpMethod.POST, entity, Enrollee.class);
		assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(respEntity.getBody().getDependents().size()).isEqualTo(0);
	}
	
}
