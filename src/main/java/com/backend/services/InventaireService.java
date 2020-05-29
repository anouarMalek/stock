package com.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entities.Inventaire;
import com.backend.entities.Stock;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.InventaireRepository;


@Service
public class InventaireService {
	
	@Autowired
	InventaireRepository rep;
	

	//Liste des inventaires
	public List<Inventaire> getInventaires() throws NotFoundException
	{
		
		List<Inventaire> inventaires = rep.findAll();
		if(inventaires.isEmpty()) throw new NotFoundException("Aucun inventaire trouvé");		
		
		return inventaires;
	}
	
	
	//ajouter une inventaire
	public void addInventaire(Inventaire inventaire) throws ConflictException
	{
		if(rep.findByStockAndStock(inventaire.getStock(), inventaire.getDate()).isPresent()) 
			throw new ConflictException("Un inventaire est déjà effectué sur ce stock à cette date.");
		
		rep.save(inventaire);
	}



	
	//supprimer une inventaire
	public void deleteInventaire(Stock stock, LocalDateTime date) throws NotFoundException
	{
		
		Inventaire inventaire= rep.findByStockAndStock(stock, date)
				.orElseThrow(() -> new NotFoundException("Aucun inventaire de ce stock à cette date n'est trouvé."));
		rep.delete(inventaire);
		
	}

}
