package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.UniteDeMesure;
import com.backend.services.UniteDeMesureService;


@RestController
@RequestMapping("/unitesDeMesure")
public class UniteDeMesureController {
	
	
	UniteDeMesureService service;
	
	@Autowired
	public UniteDeMesureController(UniteDeMesureService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UniteDeMesure> getUniteDeMesures()  throws NotFoundException
	{
		return service.getUniteDeMesures();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addUniteDeMesure(@RequestBody UniteDeMesure uniteDeMesure)  throws AlreadyExistsException
	{
		service.addUniteDeMesure(uniteDeMesure);
	}
	
	
	//PUT
	@PutMapping("/{designation}")
	@ResponseStatus(HttpStatus.OK)
	public void updateUniteDeMesure(@PathVariable String designation,@RequestBody UniteDeMesure uniteDeMesure)  throws AlreadyExistsException, NotFoundException
	{
		service.updateUniteDeMesure(designation,uniteDeMesure);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{designation}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteUniteDeMesure(@PathVariable String designation)  throws NotFoundException
	{
		service.deleteUniteDeMesure(designation);
	}
	
	
	

}