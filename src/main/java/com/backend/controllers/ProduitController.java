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

import com.backend.entities.Produit;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.ProduitService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProduitController {
	
	ProduitService service;
	
	@Autowired
	public ProduitController(ProduitService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping("/produits")
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduits(@RequestParam(name="id",required=false) Long id)  throws NotFoundException
	{
		return service.getProduits(id);
		 
	}
	
	
	//Meme produit dans differents stocks
	@GetMapping("/produits/{nom}")
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduitsByNom(@PathVariable(name="nom") String nom)  throws NotFoundException
	{
		return service.getProduitsByNom(nom);
		 
	}
	
	
	//POST
	
	@PostMapping("/produit")
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduit(@RequestBody Produit produit)  throws ConflictException
	{
		service.addProduit(produit);
	}
	
	
	//PUT
	@PutMapping("/produit/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateProduit(@PathVariable(name="id") Long id,@RequestBody Produit produit)  throws ConflictException, NotFoundException
	{
		service.updateProduit(id,produit);
	}
	
	
	//DELETE
	
	@DeleteMapping("/produit/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteProduit(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		service.deleteProduit(id);
	}
	
	
	

}
