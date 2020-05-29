package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entities.Emplacement;
import com.backend.entities.Stock;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.EmplacementService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmplacementController {
	
	EmplacementService service;
	
	@Autowired
	public EmplacementController(EmplacementService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping("/emplacements")
	@ResponseStatus(HttpStatus.OK)
	public List<Emplacement> getEmplacements(@RequestParam(name="id", required=false) Long id)  throws NotFoundException
	{
		return service.getEmplacements(id);
		 
	}
	
	
	@GetMapping("/emplacement/{id}/stock")
	@ResponseStatus(HttpStatus.OK)
	public Stock getStock(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		return service.getStock(id);
		 
	}
	
	
	
	//POST
	
	@PostMapping("/emplacement")
	@ResponseStatus(HttpStatus.CREATED)
	public void addEmplacement(@RequestBody Emplacement emplacement)  throws ConflictException
	{
		service.addEmplacement(emplacement);
	}
	
	
	//PUT
	@PutMapping("/emplacement/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateEmplacement(@PathVariable(name="id") Long id,@RequestBody Emplacement emplacement)  throws ConflictException, NotFoundException
	{
		service.updateEmplacement(id,emplacement);
	}
	
	
	//DELETE
	
	@DeleteMapping("/emplacement/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteEmplacement(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		service.deleteEmplacement(id);
	}
	
	
	

}
