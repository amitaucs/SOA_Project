package com.example;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Manufacturer;

@RestController
public class ManufacturerFeignMSController {

	@Autowired
	private ManufacturerFeignClient feignClient;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Resources<Manufacturer> getData() {
		return feignClient.findAll();
	}
	
	@RequestMapping(value="/findByDate", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Resources<Manufacturer> getDataByDate(@RequestParam("startDate") @DateTimeFormat(iso=ISO.DATE) Date date) {
		return feignClient.findByStartDateBefore(date);
	}
	
	

}
