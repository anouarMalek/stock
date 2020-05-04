package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Categorie;
import com.backend.repositories.CategorieRepository;

@Service
public class CategorieService {
	
	@Autowired
	CategorieRepository rep;
	

	//Liste des categories
	public List<Categorie> getCategories() throws NotFoundException
	{
		
		List<Categorie> categories = rep.findAll();
		if(categories.isEmpty()) throw new NotFoundException("Aucune catégorie trouvée");		
		
		return categories;
	}
	
	
	//ajouter une categorie
	public void addCategorie(Categorie categorie) throws AlreadyExistsException
	{
		if(rep.findByDesignation(categorie.getDesignation()).isPresent()) 
			throw new AlreadyExistsException("Une categorie avec la designation "+categorie.getDesignation()+" existe déjà.");
		
		rep.save(categorie);
	}
	
	
	

	//modifier une categorie
	public void updateCategorie(String designation , Categorie categorie) throws AlreadyExistsException, NotFoundException
	{
		
		Categorie updated=rep.findByDesignation(designation)
				.orElseThrow(() -> new NotFoundException("Aucune catégorie avec la designation "+designation+" n'existe"));
		
		if(rep.findByDesignation(categorie.getDesignation()).isPresent() && !rep.findByDesignation(categorie.getDesignation()).get().equals(updated))
			throw new AlreadyExistsException("Une categorie avec la designation "+categorie.getDesignation()+" existe déjà.");
		
		Long id=updated.getId();
		updated=categorie;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer une categorie
	public void deleteCategorie(String designation) throws NotFoundException
	{
		
		Categorie categorie= rep.findByDesignation(designation)
				.orElseThrow(() -> new NotFoundException("Aucune catégorie avec la designation "+designation+" n'existe"));
		rep.delete(categorie);
		
	}

}
