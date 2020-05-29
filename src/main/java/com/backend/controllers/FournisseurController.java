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

import com.backend.entities.Fournisseur;
import com.backend.entities.Produit;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.FournisseurService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FournisseurController {
	
	
	FournisseurService service;
	
	@Autowired
	public FournisseurController(FournisseurService service){
		
		this.service=service;
	}
	
	//GET
	
	@GetMapping("/fournisseurs")
	@ResponseStatus(HttpStatus.OK)
	public List<Fournisseur> getFournisseurs(@RequestParam(name="id",required=false) Long id)  throws NotFoundException
	{
		return service.getFournisseurs(id);
		 
	}
	
	
	@GetMapping("/fournisseur/{id}/produits")
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduits(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		return service.getProduits(id);
		 
	}
	
	
	//POST
	
	@PostMapping("/fournisseur")
	@ResponseStatus(HttpStatus.CREATED)
	public void addFournisseur(@RequestBody Fournisseur fournisseur)  throws ConflictException
	{
		service.addFournisseur(fournisseur);
	}
	
	
	//PUT
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateFournisseur(@PathVariable(name="id") Long id,@RequestBody Fournisseur fournisseur)  
			throws ConflictException, NotFoundException
	{
		service.updateFournisseur(id,fournisseur);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteFournisseur(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		service.deleteFournisseur(id);
	}
	
	
	

}
