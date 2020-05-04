package com.backend.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Emplacement;
import com.backend.models.Produit;
import com.backend.models.Stock;
import com.backend.repositories.StockRepository;

@Service
public class StockService {
	
	@Autowired
	StockRepository rep;
	

	//Liste des stocks
	public List<Stock> getStocks() throws NotFoundException
	{
		
		List<Stock> stocks = rep.findAll();
		if(stocks.isEmpty()) throw new NotFoundException("Aucun stock trouvé");		
		
		return stocks;
	}
	
	
	//ajouter un stock
	public void addStock(Stock stock) throws AlreadyExistsException
	{
		if(rep.findByEmplacement(stock.getEmplacement()).isPresent()) 
			throw new AlreadyExistsException("Un stock avec l'emplacement "+stock.getEmplacement().getDesignation()+" existe déjà.");
		
		rep.save(stock);
	}
	
	
	

	//modifier un stock
	public void updateStock(Emplacement emplacement , Stock stock) throws AlreadyExistsException, NotFoundException
	{
		
		Stock updated=rep.findByEmplacement(emplacement)
				.orElseThrow(() -> new NotFoundException("Aucun stock avec l'emplacement "+emplacement.getDesignation()+" n'existe"));
		
		if(rep.findByEmplacement(stock.getEmplacement()).isPresent() && !rep.findByEmplacement(stock.getEmplacement()).get().equals(updated))
			throw new AlreadyExistsException("Un stock avec l'emplacement "+stock.getEmplacement()+" existe déjà.");
		
		Long id=updated.getId();
		updated=stock;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer un stock
	public void deleteStock(Emplacement emplacement) throws NotFoundException
	{
		
		Stock stock= rep.findByEmplacement(emplacement)
				.orElseThrow(() -> new NotFoundException("Aucun stock avec l'emplacement "+emplacement.getDesignation()+" n'existe"));
		rep.delete(stock);
		
	}


	//Liste des produits dans le stock
	public List<Produit> getProducts(Emplacement emplacement) {
		
		
		Stock stock= rep.findByEmplacement(emplacement)
				.orElseThrow(()-> new NotFoundException("Aucun stock avec l'emplacement "+emplacement.getDesignation()+" n'existe"));
		List<Produit> produits = Arrays.asList(stock.getProduits());
		if(produits.isEmpty()) throw new NotFoundException("Aucun produit trouvé dans le stock");
		return produits;
	}

}
