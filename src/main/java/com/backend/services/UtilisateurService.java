package com.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.entities.*;
import com.backend.exceptions.*;
import com.backend.repositories.*;

@Service
public class UtilisateurService {
	
	@Autowired
	UtilisateurRepository rep;
	
	@Autowired
	EmailServiceImpl emailService;
	
	
	public Utilisateur getByUsername(String username)
	{
		Utilisateur utilisateur = rep.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Aucun utilisateur avec le username "+username+" trouvé"));
		return utilisateur;
	}
	
	
	public List<Utilisateur> getUtilisateurs(Long id)
	{
		
		List<Utilisateur> utilisateurs= new ArrayList<Utilisateur>();	
		
		if(id!=null)
			utilisateurs.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun utilisateur avec l'id "+id+" trouvé")));
		
		else
			utilisateurs=rep.findAll();
		
		if(utilisateurs.isEmpty())  throw new NotFoundException("Aucun utilisateur trouvé");
		return utilisateurs;
	}
	
	
	
	public void addUtilisateur(Utilisateur utilisateur)
	{
		if(rep.findByUsername(utilisateur.getUsername()).isPresent()) {
			throw new ConflictException("Un utilisateur avec le Username "+utilisateur.getUsername()+" existe déjà");
		}
		
		if(rep.findByCin(utilisateur.getCin()).isPresent()) {
			throw new ConflictException("Un utilisateur avec le CIN "+utilisateur.getCin()+" existe déjà");
		}
		
		String password= utilisateur.getPassword();
		utilisateur.setPassword(new BCryptPasswordEncoder().encode(utilisateur.getPassword()));				
		
		rep.save(utilisateur);
		
		if(utilisateur.getEmail()!=null && !utilisateur.getEmail().isEmpty())
		{
			utilisateur.setPassword(password);
			emailService.sendAuthenticationInfos(utilisateur);
		}
		
	}
	
	public void updateUtilisateur(Long id,Utilisateur utilisateur)
	{
		Utilisateur updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun utilisateur avec l'id "+id+" trouvé"));
		
		//verifier l'unicité du nouveau username
		if(rep.findByUsername(utilisateur.getUsername()).isPresent() && !(rep.findByUsername(utilisateur.getUsername()).get()==updated))
			throw new ConflictException("Un utilisateur avec le Username "+utilisateur.getUsername()+" existe déjà");
		//verifier l'unicité du nouveau CIN
		if(rep.findByCin(utilisateur.getCin()).isPresent() && !(rep.findByCin(utilisateur.getCin()).get()==updated))
			throw new ConflictException("Un utilisateur avec le CIN "+utilisateur.getCin()+" existe déjà");
		String password = null;
		
		if(utilisateur.getNom()!=null && !utilisateur.getNom().isEmpty()) updated.setNom(utilisateur.getNom());
		if(utilisateur.getPrenom()!=null && !utilisateur.getPrenom().isEmpty()) updated.setPrenom(utilisateur.getPrenom());
		if(utilisateur.getCin()!=null && !utilisateur.getCin().isEmpty()) updated.setCin(utilisateur.getCin());
		if(utilisateur.getTelephone()!=null && !utilisateur.getTelephone().isEmpty()) updated.setTelephone(utilisateur.getTelephone());
		if(utilisateur.getAdresse()!=null && !utilisateur.getAdresse().isEmpty()) updated.setAdresse(utilisateur.getAdresse());
		if(utilisateur.getUsername()!=null && !utilisateur.getUsername().isEmpty()) updated.setUsername(utilisateur.getUsername());
		if(utilisateur.getEmail()!=null && !utilisateur.getEmail().isEmpty()) updated.setEmail(utilisateur.getEmail());
		if(utilisateur.getPassword()!=null && !utilisateur.getPassword().isEmpty()) 
			{
			password = utilisateur.getPassword();
			updated.setPassword(new BCryptPasswordEncoder().encode(utilisateur.getPassword()));
			}
		if(utilisateur.getRole()!=null && !utilisateur.getRole().isEmpty()) updated.setRole(utilisateur.getRole());
		
		//Envoyer un email à l'utilisateur pour s'identifier		
		utilisateur.setEmail(updated.getEmail());
		if(!utilisateur.getEmail().isEmpty() && utilisateur.getEmail()!=null)
		{
			utilisateur.setUsername(updated.getUsername());
			if(password!=null && !password.isEmpty()) utilisateur.setPassword(password);
			emailService.sendAuthenticationInfos(utilisateur);
		}
		rep.save(updated);
		
	}

	public void removeUtilisateur(Long id)
	{
		
		//vérifier l'existence de l'utilisateur
		Utilisateur utilisateur=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun utilisateur avec l'id "+id+" n'est trouvé"));
		rep.delete(utilisateur);
	}


	
	
}
