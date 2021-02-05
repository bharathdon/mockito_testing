package com.abc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abc.model.Employee;
import com.abc.model.Response;
import com.abc.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PostConstruct
	public void postconstruct() {
		Employee employee = new Employee(UUID.randomUUID().toString(), "abcd", "defg");
		Employee employee2 = new Employee(UUID.randomUUID().toString(), "ghij", "klmn");

		ArrayList<Employee> list = new ArrayList<Employee>();
		list.add(employee);
		list.add(employee2);

		employeeRepository.saveAll(list);

	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,path = "/insert")
	public Response addEmp(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return new Response("data inserted eno is : "+employee.getId(), true);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Employee>> getAllEmp(){
		List<Employee> findAll = employeeRepository.findAll();
		return new ResponseEntity<List<Employee>>(findAll, HttpStatus.OK);
	}
	
	
	
}
