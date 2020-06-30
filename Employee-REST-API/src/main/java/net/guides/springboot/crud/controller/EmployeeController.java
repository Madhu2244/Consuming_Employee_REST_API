package net.guides.springboot.crud.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;
import net.guides.springboot.crud.exception.ResourceNotFoundException;
import net.guides.springboot.crud.model.Employee;
import net.guides.springboot.crud.repository.EmployeeRepository;
import net.guides.springboot.crud.service.SequenceGeneratorService;




@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/employees")
	@ApiOperation(value = "Retrieves all employees in the database")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/employees/count")
	@ApiOperation(value = "Retrieves the number of employees in the database")
	public long employeeCount() {
		return employeeRepository.count();
	}
	
	/*@GetMapping("/getEmployee/{id}")
	@ApiOperation(value = "Finds an employee given an ID",
	  notes = "Provide an ID Number to look up a specific employee from the database")
	public ResponseEntity<Employee> retrieveEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}*/
	
	@PostMapping("/employee/create")
	@ApiOperation(value = "Adds an employee into the database given First Name, Last Name, Email, and Avaliability")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
		return employeeRepository.save(employee);
	}
	
	@PostMapping("/test")
	public Employee create(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String emailId) {
		Employee p = new Employee(firstName,  lastName, emailId);
		p.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
		return employeeRepository.save(p);
	}
	

	@PostMapping("/test2")
	public Employee update(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String emailId) {
		Employee p = new Employee(firstName,  lastName, emailId);
		return employeeRepository.save(p);
	}
	
	@PutMapping("/employees/{id}")
	@ApiOperation(value = "Finds an employee given an ID",
				  notes = "Provide an ID Number to look up a specific employee from the database",
				  response = Contact.class)
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}/delete")
	@ApiOperation(value = "Deletes an employee given an ID")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}