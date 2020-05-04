package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.UniteDeMesure;
import com.backend.repositories.UniteDeMesureRepository;


@Service
public class UniteDeMesureService {
	
	@Autowired
	UniteDeMesureRepository rep;
	

	//Liste des unités de mesure
	public List<UniteDeMesure> getUniteDeMesures() throws NotFoundException
	{
		
		List<UniteDeMesure> uniteDeMesures = rep.findAll();
		if(uniteDeMesures.isEmpty()) throw new NotFoundException("Aucune unité de mesure trouvée");		
		
		return uniteDeMesures;
	}
	
	
	//ajouter une unité de mesure
	public void addUniteDeMesure(UniteDeMesure uniteDeMesure) throws AlreadyExistsException
	{
		if(rep.findByDesignation(uniteDeMesure.getDesignation()).isPresent()) 
			throw new AlreadyExistsException("Une unité de mesure avec la designation "+uniteDeMesure.getDesignation()+" existe déjà.");
		
		rep.save(uniteDeMesure);
	}
	
	
	

	//modifier une unités de mesure
	public void updateUniteDeMesure(String designation , UniteDeMesure uniteDeMesure) throws AlreadyExistsException, NotFoundException
	{
		
		UniteDeMesure updated=rep.findByDesignation(designation)
				.orElseThrow(() -> new NotFoundException("Aucune unité de mesure avec la designation "+designation+" n'existe"));
		
		if(rep.findByDesignation(uniteDeMesure.getDesignation()).isPresent() && !rep.findByDesignation(uniteDeMesure.getDesignation()).get().equals(updated))
			throw new AlreadyExistsException("Une unité de mesure avec la designation "+uniteDeMesure.getDesignation()+" existe déjà.");
		
		Long id=updated.getId();
		updated=uniteDeMesure;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer une unités de mesure
	public void deleteUniteDeMesure(String designation) throws NotFoundException
	{
		
		UniteDeMesure uniteDeMesure= rep.findByDesignation(designation)
				.orElseThrow(() -> new NotFoundException("Aucune unité de mesure avec la designation "+designation+" n'existe"));
		rep.delete(uniteDeMesure);
		
	}

}
