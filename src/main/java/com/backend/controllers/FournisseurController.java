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
import com.backend.models.Fournisseur;
import com.backend.services.FournisseurService;


@RequestMapping("/fournisseurs")
@RestController
public class FournisseurController {
	
	
	FournisseurService service;
	
	@Autowired
	public FournisseurController(FournisseurService service){
		
		this.service=service;
	}
	
	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Fournisseur> getFournisseurs()  throws NotFoundException
	{
		return service.getFournisseurs();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addFournisseur(@RequestBody Fournisseur fournisseur)  throws AlreadyExistsException
	{
		service.addFournisseur(fournisseur);
	}
	
	
	//PUT
	@PutMapping("/{nom}")
	@ResponseStatus(HttpStatus.OK)
	public void updateFournisseur(@PathVariable String nom,@RequestBody Fournisseur fournisseur)  
			throws AlreadyExistsException, NotFoundException
	{
		service.updateFournisseur(nom,fournisseur);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{nom}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteFournisseur(@PathVariable String nom)  throws NotFoundException
	{
		service.deleteFournisseur(nom);
	}
	
	
	

}
