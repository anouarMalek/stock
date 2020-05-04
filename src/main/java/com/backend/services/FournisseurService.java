package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.exceptions.*;
import com.backend.models.Fournisseur;
import com.backend.repositories.FournisseurRepository;

@Service
public class FournisseurService {
	
	@Autowired
	FournisseurRepository rep;
	

	//Liste des fournisseurs
	public List<Fournisseur> getFournisseurs() throws NotFoundException
	{
		
		List<Fournisseur> fournisseurs = rep.findAll();
		if(fournisseurs.isEmpty()) throw new NotFoundException("Aucun fournisseur trouvé");		
		
		return fournisseurs;
	}
	
	
	//ajouter un fournisseur
	public void addFournisseur(Fournisseur fournisseur) throws AlreadyExistsException
	{
		if(rep.findByNom(fournisseur.getNom()).isPresent()) 
			throw new AlreadyExistsException("Un fournisseur avec la nom "+fournisseur.getNom()+" existe déjà.");
		
		rep.save(fournisseur);
	}
	
	
	

	//modifier une fournisseur
	public void updateFournisseur(String nom , Fournisseur fournisseur) throws AlreadyExistsException, NotFoundException
	{
		
		Fournisseur updated=rep.findByNom(nom)
				.orElseThrow(() -> new NotFoundException("Aucun fournisseur avec la nom "+nom+" n'existe"));
		
		if(rep.findByNom(fournisseur.getNom()).isPresent() && !rep.findByNom(fournisseur.getNom()).get().equals(updated))
			throw new AlreadyExistsException("Un fournisseur avec la nom "+fournisseur.getNom()+" existe déjà.");
		
		Long id=updated.getId();
		updated=fournisseur;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer une fournisseur
	public void deleteFournisseur(String nom) throws NotFoundException
	{
		
		Fournisseur fournisseur= rep.findByNom(nom)
				.orElseThrow(() -> new NotFoundException("Aucun fournisseur avec la nom "+nom+" n'existe"));
		rep.delete(fournisseur);
		
	}

}
