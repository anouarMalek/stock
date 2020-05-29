package com.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entities.Produit;
import com.backend.entities.UniteDeMesure;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.UniteDeMesureRepository;


@Service
public class UniteDeMesureService {
	
	@Autowired
	UniteDeMesureRepository rep;
	

	//Liste des unités de mesure
	public List<UniteDeMesure> getUniteDeMesures(Long id) throws NotFoundException
	{
		
		List<UniteDeMesure> uniteDeMesures = new ArrayList<UniteDeMesure>();
		if(id!=null) uniteDeMesures.add(rep.findById(id).orElseThrow(()-> new NotFoundException("Aucune unité de mesure avec l'id "+id+" n'existe")));
		else uniteDeMesures=rep.findAll();
			if(uniteDeMesures.isEmpty()) throw new NotFoundException("Aucune unité de mesure trouvée");		
		
		return uniteDeMesures;
	}
	
	
	//Liste des produits
	public List<Produit> getProduits(Long id) throws NotFoundException
	{
		
		UniteDeMesure uniteDeMesure= rep.findById(id)
				.orElseThrow(()-> new NotFoundException("Aucune unité de mesure avec l'id "+id+" n'existe"));
		
		List<Produit> produits=uniteDeMesure.getProduits();
		if(produits.isEmpty())
			throw new NotFoundException("Aucun produit ayant cette unité de mesure.");
		
		return produits;
		
	}
	
	
	//ajouter une unité de mesure
	public void addUniteDeMesure(UniteDeMesure uniteDeMesure) throws ConflictException
	{
		if(rep.findByDesignation(uniteDeMesure.getDesignation()).isPresent()) 
			throw new ConflictException("Une unité de mesure avec la designation "+uniteDeMesure.getDesignation()+" existe déjà.");
		
		rep.save(uniteDeMesure);
	}
	
	
	

	//modifier une unité de mesure
	public void updateUniteDeMesure(Long id , UniteDeMesure uniteDeMesure) throws ConflictException, NotFoundException
	{
		
		UniteDeMesure updated=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucune unité de mesure avec l'id "+id+" n'existe"));
		
		if(rep.findByDesignation(uniteDeMesure.getDesignation()).isPresent() && !rep.findByDesignation(uniteDeMesure.getDesignation()).get().equals(updated))
			throw new ConflictException("Une unité de mesure avec la designation "+uniteDeMesure.getDesignation()+" existe déjà.");
		
		updated=uniteDeMesure;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer une unité de mesure
	public void deleteUniteDeMesure(Long id) throws NotFoundException
	{
		
		UniteDeMesure uniteDeMesure= rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucune unité de mesure avec l'id "+id+" n'existe"));
		rep.delete(uniteDeMesure);
		
	}

}
