package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Mouvement;
import com.backend.services.MouvementService;

@RestController
@RequestMapping("/mouvements")
public class MouvementController {
	
	MouvementService service;
	
	@Autowired
	public MouvementController(MouvementService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Mouvement> getMouvements()  throws NotFoundException
	{
		return service.getMouvements();
		 
	}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addMouvement(@RequestBody Mouvement mouvement)  throws AlreadyExistsException
	{
		service.addMouvement(mouvement);
	}
	
	
}
