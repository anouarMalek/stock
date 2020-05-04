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
import com.backend.models.Emplacement;
import com.backend.services.EmplacementService;

@RestController
@RequestMapping("/emplacements")
public class EmplacementController {
	
	EmplacementService service;
	
	@Autowired
	public EmplacementController(EmplacementService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Emplacement> getEmplacements()  throws NotFoundException
	{
		return service.getEmplacements();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addEmplacement(@RequestBody Emplacement emplacement)  throws AlreadyExistsException
	{
		service.addEmplacement(emplacement);
	}
	
	
	//PUT
	@PutMapping("/{designation}")
	@ResponseStatus(HttpStatus.OK)
	public void updateEmplacement(@PathVariable String designation,@RequestBody Emplacement emplacement)  throws AlreadyExistsException, NotFoundException
	{
		service.updateEmplacement(designation,emplacement);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{designation}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteEmplacement(@PathVariable String designation)  throws NotFoundException
	{
		service.deleteEmplacement(designation);
	}
	
	
	

}
