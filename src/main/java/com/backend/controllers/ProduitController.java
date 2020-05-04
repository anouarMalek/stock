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
import com.backend.models.Produit;
import com.backend.services.ProduitService;

@RestController
@RequestMapping("/produits")
public class ProduitController {
	
	ProduitService service;
	
	@Autowired
	public ProduitController(ProduitService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduits()  throws NotFoundException
	{
		return service.getProduits();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduit(@RequestBody Produit produit)  throws AlreadyExistsException
	{
		service.addProduit(produit);
	}
	
	
	//PUT
	@PutMapping("/{nom}")
	@ResponseStatus(HttpStatus.OK)
	public void updateProduit(@PathVariable String nom,@RequestBody Produit produit)  throws AlreadyExistsException, NotFoundException
	{
		service.updateProduit(nom,produit);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{nom}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteProduit(@PathVariable String nom)  throws NotFoundException
	{
		service.deleteProduit(nom);
	}
	
	
	

}
