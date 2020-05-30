package com.backend.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.backend.entities.*;
import com.backend.services.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UtilisateurController {
	
	
	UtilisateurService service;
	
	@Autowired
	public UtilisateurController(UtilisateurService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/utilisateurs")
			@ResponseStatus(HttpStatus.OK)
			public List<Utilisateur> getUtilisateurs(@RequestParam(name="id", required=false) Long id)
			{
				return service.getUtilisateurs(id);
			}
			
			
			@GetMapping("/utilisateur/{username}")
			@ResponseStatus(HttpStatus.OK)
			public Utilisateur getUtilisateur(@PathVariable(name="username") String username)
			{
				return service.getByUsername(username);
			}
			

		
		//POST
			
			@PostMapping("/utilisateur")
			@ResponseStatus(HttpStatus.CREATED)
			public void addUtilisateur(@RequestBody Utilisateur utilisateur)
			{
				service.addUtilisateur(utilisateur);
			}
		
		
		
		//PUT
			
			@PutMapping("/utilisateur/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateUtilisateur(@PathVariable Long id , @RequestBody(required=false) Utilisateur utilisateur)
			{
				service.updateUtilisateur(id,utilisateur);
			}
	
		
			
		//DELETE
			
			@DeleteMapping("/utilisateur/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteUtilisateur(@PathVariable Long id)
			{
				service.removeUtilisateur(id);
			}
			
	

}

