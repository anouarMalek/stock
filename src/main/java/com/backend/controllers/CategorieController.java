package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Categorie;
import com.backend.services.CategorieService;


@RestController
@RequestMapping("/categories")
public class CategorieController {
	
	CategorieService service;
	
	@Autowired
	public CategorieController(CategorieService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Categorie> getCategories()  throws NotFoundException
	{
		return service.getCategories();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addCategorie(@RequestBody Categorie categorie)  throws AlreadyExistsException
	{
		service.addCategorie(categorie);
	}
	
	
	//PUT
	@PutMapping("/{designation}")
	@ResponseStatus(HttpStatus.OK)
	public void updateCategorie(@PathVariable String designation,@RequestBody Categorie categorie)  throws AlreadyExistsException, NotFoundException
	{
		service.updateCategorie(designation,categorie);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{designation}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategorie(@PathVariable String designation)  throws NotFoundException
	{
		service.deleteCategorie(designation);
	}
	
	
	

}
