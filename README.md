# Enrollment Service

## Indroduction
Enrollment Service is a spring boot based microservice part Health Plan Member System. Enrollee is a Health Plan subscriber with dependents. Enrollment Service tracks demographics and status of the members.

## Requirements: 
- Enrollees must have an id, name, and activation status (true or false), and a birth date
- Enrollees may have a phone number (although they do not have to supply this)
- Enrollees may have zero or more dependents
- Each of an enrollee's dependents must have an id, name, and birth date

The Enrollee Microservice should  be able to do handle below operations: 
- Add a new enrollee
- Modify an existing enrollee
- Remove an enrollee entirely
- Add dependents to an enrollee
- Remove dependents from an enrollee
- Modify existing dependents

## Technology Components
- Spring Boot with JPA for Micro Service
- MySQL for database
- Maven for build automation
- Java (OpenJdk 8 or higher)

## Entity Class Diagram
![Entity Class Diagram](./doc/ERDiagram.png)

## Swagger UI Rest End Points
http://localhost:8080/swagger-ui/

![Enrollment Service](./doc/SwaggerEnrollmentService.png)

## Swagger UI Enrollment Service Test Cases

### Add Enrollee Request
![Enrollment Service](./doc/addEnrolleeRequest.png)

### Add Enrollee Response
![Enrollment Service](./doc/addEnrolleeResponse.png)

### Add Enrollee Database Record
![Enrollment Service](./doc/addEnrolleeDB.png)

### Update Enrollee Request
![Enrollment Service](./doc/updateEnrolleeRequest.png)

### Update Enrollee Response
![Enrollment Service](./doc/updateEnrolleeResponse.png)

### Update Enrollee Database Record
![Enrollment Service](./doc/updateEnrolleeDB.png)

### Get Enrollee Request
![Enrollment Service](./doc/getEnrolleeRequest.png)

### Get Enrollee Response
![Enrollment Service](./doc/getEnrolleeResponse.png)

### Delete Enrollee Request
![Enrollment Service](./doc/deleteEnrolleeRequest.png)

### Delete Enrollee Response
![Enrollment Service](./doc/deleteEnrolleeResponse.png)

### Delete Enrollee Database Record
![Enrollment Service](./doc/deleteEnrolleeDB.png)

---
### Add Dependent Request
![Enrollment Service](./doc/addDependentRequest.png)

### Add Dependent Response
![Enrollment Service](./doc/addDependentResponse.png)

### Add Dependent Database Record
![Enrollment Service](./doc/addDependentDB.png)

### Update Dependent Request
![Enrollment Service](./doc/updateDependentRequest.png)

### Update Dependent Response
![Enrollment Service](./doc/updateDependentResponse.png)

### Update Dependent Database Record
![Enrollment Service](./doc/updateDependentDB.png)

### Get Dependent Request
![Enrollment Service](./doc/getDependentRequest.png)

### Get Dependent Response
![Enrollment Service](./doc/getDependentResponse.png)

### Delete Dependent Request
![Enrollment Service](./doc/deleteDependentRequest.png)

### Delete Dependent Response
![Enrollment Service](./doc/deleteDependentResponse.png)

### Delete Dependent Database Record
![Enrollment Service](./doc/deleteDependentDB.png)

## Maven Test Result
![Enrollment Service](./doc/MavenTestResult.png)

 
 
# Docker Container

1. Run the  command `./mvnw clean install` from the enrollment home folder
2. Should create the application jar file in target folder: `./target/enrollment-0.0.1-SNAPSHOT.jar`
3. Build the enrollment service docker image by running the command `./docker-run.sh`
![Enrollment Service](./doc/docker-container-build.png)
4. Above command will start the Enrollment application in the container with host port 9090
5. Start swagger at port 9090 `http://localhost:9090/swagger-ui/` 
6. Add enrollee as shown below:
![Enrollment Service](./doc/docker-add-enrollee-request.png)
7. Should get a successful response as below:
![Enrollment Service](./doc/docker-add-enrollee-response.png)