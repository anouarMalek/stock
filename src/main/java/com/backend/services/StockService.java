package com.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entities.Inventaire;
import com.backend.entities.Mouvement;
import com.backend.entities.Produit;
import com.backend.entities.Stock;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.StockRepository;

@Service
public class StockService {
	
	@Autowired
	StockRepository rep;
	

	//Liste des stocks
	public List<Stock> getStocks(Long id) throws NotFoundException
	{
		List<Stock> stocks = new ArrayList<Stock>();
		if(id!=null) 
			stocks.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun stock avec l'id "+id+" n'existe")));
		
		else stocks = rep.findAll();
			if(stocks.isEmpty()) throw new NotFoundException("Aucun stock trouvé");		
		
		return stocks;
	}
	
	
	
	//Liste des produits
	public List<Produit> getProduits(Long id) throws NotFoundException
	{
		Stock stock = rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun stock avec l'id "+id+" n'existe"));
		
		List<Produit> produits = stock.getProduits();
		
		
		return produits;

	}
	
	
	
	//Trouver un produit par nom et prix achat
	public Produit produit(String nom, double prixAchat, Long id)
	{
		
		List<Produit> produits= getProduits(id);
		for (Produit produit : produits) {
			if(produit.getNom().equals(nom) && produit.getPrixAchat()==prixAchat)
			{
				return produit;
				
			}
		}
		
		return null;
	}
	
	
	
	//Liste des inventaires
		public List<Inventaire> getInventaires(Long id) throws NotFoundException
		{
			Stock stock = rep.findById(id)
					.orElseThrow(() -> new NotFoundException("Aucun stock avec l'id "+id+" n'existe"));
			
			List<Inventaire> inventaires = stock.getInventaires();
			
			if(inventaires.isEmpty()) throw new NotFoundException("Aucun inventaire effectué sur ce stock");
			
			
			return inventaires;

		}
		
		
		//Liste des mouvements
				public List<Mouvement> getMouvements(Long id) throws NotFoundException
				{
					Stock stock = rep.findById(id)
							.orElseThrow(() -> new NotFoundException("Aucun stock avec l'id "+id+" n'existe"));
					
					List<Mouvement> mouvements = stock.getMouvements();
					
					if(mouvements.isEmpty()) throw new NotFoundException("Aucun mouvement effectué.");
					
					
					return mouvements;

				}
		
	
	
	
	//ajouter un stock
	public void addStock(Stock stock) throws ConflictException
	{
		if(rep.findByEmplacement(stock.getEmplacement()).isPresent()) 
			throw new ConflictException("Un stock avec l'emplacement "+stock.getEmplacement().getDesignation()+" existe déjà.");
		
		rep.save(stock);
	}
	
	
	

	//modifier un stock
	public void updateStock(Long id , Stock stock) throws ConflictException, NotFoundException
	{
		
		Stock updated=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun stock avec l'id "+id+" n'existe"));
		
		if(rep.findByEmplacement(stock.getEmplacement()).isPresent() && !rep.findByEmplacement(stock.getEmplacement()).get().equals(updated))
			throw new ConflictException("Un stock avec l'emplacement "+stock.getEmplacement()+" existe déjà.");
		
		if(stock.getTelephone()!=null && !stock.getTelephone().isEmpty()) updated.setTelephone(stock.getTelephone());
		if(stock.getFax()!=null && !stock.getFax().isEmpty()) updated.setFax(stock.getFax());
		
		rep.save(updated);
		
	}

	
	
	//supprimer un stock
	public void deleteStock(Long id) throws NotFoundException
	{
		
		Stock stock= rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun stock avec l'id "+id+" n'existe"));
		rep.delete(stock);
		
	}


}
