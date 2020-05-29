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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entities.Produit;
import com.backend.entities.UniteDeMesure;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.UniteDeMesureService;


@RestController
public class UniteDeMesureController {
	
	
	UniteDeMesureService service;
	
	@Autowired
	public UniteDeMesureController(UniteDeMesureService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping("/unitesDeMesure")
	@ResponseStatus(HttpStatus.OK)
	public List<UniteDeMesure> getUniteDeMesures(@RequestParam(name="id", required=false) Long id)  throws NotFoundException
	{
		return service.getUniteDeMesures(id);
		 
	}
	
	
	
	@GetMapping("/uniteDeMesure/{id}/produits")
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduits(@PathVariable(name="id") Long id)
	{
		
		return service.getProduits(id);
	}
	
	
	//POST
	
	@PostMapping("/uniteDeMesure")
	@ResponseStatus(HttpStatus.CREATED)
	public void addUniteDeMesure(@RequestBody UniteDeMesure uniteDeMesure)  throws ConflictException
	{
		service.addUniteDeMesure(uniteDeMesure);
	}
	
	
	//PUT
	@PutMapping("/uniteDeMesure/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateUniteDeMesure(@PathVariable(name="id") Long id,@RequestBody UniteDeMesure uniteDeMesure)  
			throws ConflictException, NotFoundException
	{
		service.updateUniteDeMesure(id,uniteDeMesure);
	}
	
	
	//DELETE
	
	@DeleteMapping("/uniteDeMesure/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteUniteDeMesure(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		service.deleteUniteDeMesure(id);
	}
	
	
	

}