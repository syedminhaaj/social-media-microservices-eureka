package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.response.AddressResponse;
import com.example.demo.response.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.repo.EmployeeRepo;

@Service
public class EmployeeService {

	
	@Autowired
	private EmployeeRepo EmployeeRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private WebClient webClient;
	
	//@Autowired
	//private RestTemplate restTemplate; // null
	
	//@Value("${adressservice.base.url}")
	//private String addressBaseURL;
	
//	public EmployeeService(@Value("${adressservice.base.url}") String addressBaseURL,
//			RestTemplateBuilder builder) {
//		
//		this.restTemplate= builder
//				           .rootUri(addressBaseURL)
//				           .build();
//	}

	
	public EmployeeResponse getEmployeeById(int id) {
		
		
		//employee -> EmployeeResponse
		Employee employee = EmployeeRepo.findById(id).get(); // db call -> 10
		
		EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
		
		//10 sec
		AddressResponse addressResponse =  webClient
				                           .get()
				                           .uri("/address/"+id)
				                           .retrieve()
				                           .bodyToMono(AddressResponse.class)
				                           .block();
		
		employeeResponse.setAddressResponse(addressResponse);
				
		
		return employeeResponse;
		
	}

}
