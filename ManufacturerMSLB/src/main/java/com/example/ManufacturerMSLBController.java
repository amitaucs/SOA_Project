package com.example;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.model.Manufacturer;

@RestController
public class ManufacturerMSLBController {

	@Autowired
	private LoadBalancerClient loadBalanceClient;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public String getData() {
		ServiceInstance serviceInstance = 
				loadBalanceClient.choose("manufacturer-microservice");
		
		System.out.println(serviceInstance.getPort());
		
		RestTemplate restTemplate = new RestTemplate();
		String response = null;
		//ResponseEntity<String> responseEntity = new ResponseEntity<String>(String.valueOf(serviceInstance.getPort()),S);
		ResponseEntity<ResponseEntity> resources = null;
		try {
			response = restTemplate.getForObject(
					new URI(serviceInstance.getUri().toString().concat("/datarest")), 
					String.class);
		} catch (RestClientException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
