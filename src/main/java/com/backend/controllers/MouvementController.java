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

import com.backend.entities.Mouvement;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.MouvementService;

@RestController
public class MouvementController {
	
	MouvementService service;
	
	@Autowired
	public MouvementController(MouvementService service) {
		this.service = service;
	}


	//GET
	
	@GetMapping("/mouvements")
	@ResponseStatus(HttpStatus.OK)
	public List<Mouvement> getMouvements(@RequestParam(name="id", required=false) Long id)  throws NotFoundException
	{
		return service.getMouvements(id);
		 
	}
	
	
	//POST
	
	@PostMapping("/mouvement")
	@ResponseStatus(HttpStatus.CREATED)
	public void addMouvement(@RequestBody Mouvement mouvement)  throws ConflictException
	{
		service.addMouvement(mouvement);
	}
	
	
}
