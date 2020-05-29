package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.backend.entities.Categorie;
import com.backend.entities.Produit;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.CategorieService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategorieController {
	
	CategorieService service;
	
	@Autowired
	public CategorieController(CategorieService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping("/categories")
	@ResponseStatus(HttpStatus.OK)
	public List<Categorie> getCategories(@RequestParam(name="id", required=false) Long id)  throws NotFoundException
	{
		return service.getCategories(id);
		 
	}
	
	@GetMapping("/categorie/{id}/produits")
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduits(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		return service.getProduits(id);
		 
	}

	
	//POST
	
	@PostMapping("/categorie")
	@ResponseStatus(HttpStatus.CREATED)
	public void addCategorie(@RequestBody Categorie categorie)  throws ConflictException
	{
		service.addCategorie(categorie);
	}
	
	
	//PUT
	@PutMapping("/categorie/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateCategorie(@PathVariable(name="id") Long id,@RequestBody Categorie categorie)  throws ConflictException, NotFoundException
	{
		service.updateCategorie(id,categorie);
	}
	
	
	//DELETE
	
	@DeleteMapping("/categorie/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategorie(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		service.deleteCategorie(id);
	}
	
	
	

}
