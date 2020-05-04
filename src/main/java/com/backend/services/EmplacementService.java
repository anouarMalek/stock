package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Emplacement;
import com.backend.repositories.EmplacementRepository;

@Service
public class EmplacementService {
	
	@Autowired
	EmplacementRepository rep;
	

	//Liste des emplacements
	public List<Emplacement> getEmplacements() throws NotFoundException
	{
		
		List<Emplacement> emplacements = rep.findAll();
		if(emplacements.isEmpty()) throw new NotFoundException("Aucun emplacement trouvé");		
		
		return emplacements;
	}
	
	
	//ajouter une emplacement
	public void addEmplacement(Emplacement emplacement) throws AlreadyExistsException
	{
		if(rep.findByDesignation(emplacement.getDesignation()).isPresent()) 
			throw new AlreadyExistsException("Un emplacement avec la designation "+emplacement.getDesignation()+" existe déjà.");
		
		rep.save(emplacement);
	}
	
	
	

	//modifier une emplacement
	public void updateEmplacement(String designation , Emplacement emplacement) throws AlreadyExistsException, NotFoundException
	{
		
		Emplacement updated=rep.findByDesignation(designation)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec la designation "+designation+" n'existe"));
		
		if(rep.findByDesignation(emplacement.getDesignation()).isPresent() && !rep.findByDesignation(emplacement.getDesignation()).get().equals(updated))
			throw new AlreadyExistsException("Un emplacement avec la designation "+emplacement.getDesignation()+" existe déjà.");
		
		Long id=updated.getId();
		updated=emplacement;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer une emplacement
	public void deleteEmplacement(String designation) throws NotFoundException
	{
		
		Emplacement emplacement= rep.findByDesignation(designation)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec la designation "+designation+" n'existe"));
		rep.delete(emplacement);
		
	}

}
