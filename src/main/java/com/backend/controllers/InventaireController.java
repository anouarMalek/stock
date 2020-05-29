package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entities.Inventaire;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.InventaireService;

@RestController
@RequestMapping("/inventaires")
public class InventaireController {
	
	InventaireService service;
	
	@Autowired
	public InventaireController(InventaireService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Inventaire> getInventaires(@RequestParam(name="id", required=false) Long id)  throws NotFoundException
	{
		return service.getInventaires();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addInventaire(@RequestBody Inventaire inventaire)  throws ConflictException
	{
		service.addInventaire(inventaire);
	}
	

	

}
