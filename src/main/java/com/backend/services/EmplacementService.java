package com.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entities.Emplacement;
import com.backend.entities.Stock;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.EmplacementRepository;

@Service
public class EmplacementService {
	
	@Autowired
	EmplacementRepository rep;
	

	//Liste des emplacements
	public List<Emplacement> getEmplacements(Long id) throws NotFoundException
	{
		
		List<Emplacement> emplacements = new ArrayList<Emplacement>();
		if(id!=null) 
			emplacements.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe")));
								
		else emplacements = rep.findAll();
			if(emplacements.isEmpty()) throw new NotFoundException("Aucun emplacement trouvé");		
		
		return emplacements;
	}
	
	
	
	//Stock
	public Stock getStock(Long id) throws NotFoundException
	{
		Emplacement emplacement = rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe"));
			
		if(emplacement.getStock()== null) throw new NotFoundException("Stock pas encore créé pour cette emplacement.");
		return emplacement.getStock();
	}
	
	
	//ajouter une emplacement
	public void addEmplacement(Emplacement emplacement) throws ConflictException
	{
		if(rep.findByDesignation(emplacement.getDesignation()).isPresent()) 
			throw new ConflictException("Un emplacement avec la designation "+emplacement.getDesignation()+" existe déjà.");
		
		rep.save(emplacement);
	}
	
	
	

	//modifier une emplacement
	public void updateEmplacement(Long id , Emplacement emplacement) throws ConflictException, NotFoundException
	{
		
		Emplacement updated=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe"));
		
		if(rep.findByDesignation(emplacement.getDesignation()).isPresent() && !rep.findByDesignation(emplacement.getDesignation()).get().equals(updated))
			throw new ConflictException("Un emplacement avec la designation "+emplacement.getDesignation()+" existe déjà.");
		
		if(emplacement.getDesignation()!=null && !emplacement.getDesignation().isEmpty()) updated.setDesignation(emplacement.getDesignation());
		if(emplacement.getAdresse()!=null && !emplacement.getAdresse().isEmpty()) updated.setAdresse(emplacement.getAdresse());
		
		rep.save(updated);
		
	}

	
	
	//supprimer une emplacement
	public void deleteEmplacement(Long id) throws NotFoundException
	{
		
		Emplacement emplacement= rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe"));
		rep.delete(emplacement);
		
	}

}
