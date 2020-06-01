package com.backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entities.Fournisseur;
import com.backend.entities.Mouvement;
import com.backend.entities.Produit;
import com.backend.entities.Stock;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.MouvementRepository;

@Service
@Transactional
public class MouvementService {
	
	@Autowired
	MouvementRepository rep;
	
	@Autowired
	ProduitService produitService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	FournisseurService fournisseurService;
	

	//Liste des mouvements
	public List<Mouvement> getMouvements(Long id) throws NotFoundException
	{
		
		List<Mouvement> mouvements = new ArrayList<Mouvement>();
		if(id!=null)
			mouvements.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun mouvement avec l'id "+id+" trouvé.")));
			
			else mouvements = rep.findAll();
		if(mouvements.isEmpty()) throw new NotFoundException("Aucun mouvement trouvé");		
		
		return mouvements;
	}
	
	
	//ajouter une mouvement
	@SuppressWarnings("unused")
	public void addMouvement(Mouvement mouvement) throws NotFoundException
	{
		Produit produit = produitService.getProduits(mouvement.getProduit().getId()).get(0);
		
		Stock stock = stockService.getStocks(mouvement.getStock().getId()).get(0);
		
		Fournisseur fournisseur = fournisseurService.getFournisseurs(mouvement.getProduit().getFournisseur().getId()).get(0);
		
		Produit produit2=stockService.produit(produit.getNom(),fournisseur,mouvement.getProduit().getPrixAchat(), stock.getId());
		
		
		if(produit2!=null)
		{
			
			int quantiteExistante=produit2.getQuantiteEnStock();
			int quantiteMouvement=mouvement.getQuantite();
			if(mouvement.getType().equals("Entree"))
				produit2.setQuantiteEnStock(quantiteExistante + quantiteMouvement);
			else if(mouvement.getType().equals("Sortie"))
			{
				if(quantiteExistante >= quantiteMouvement)
					produit2.setQuantiteEnStock(quantiteExistante - quantiteMouvement);
				else throw new ConflictException("Quantité en stock insuffisante.");
			}
			else throw new NotFoundException("Mouvement non reconnu.");
			
			produitService.rep.save(produit2);
			produitService.setQuantiteTotale(produit2.getNom());
		}
		
		else
		{
			produit2= new Produit();
			if(mouvement.getType().equals("Entree")) 
			{
				produit2.setCategorie(produit.getCategorie());
				produit2.setFournisseur(fournisseur);
				produit2.setUniteDeMesure(produit.getUniteDeMesure());
				produit2.setDescription(produit.getDescription());
				produit2.setNom(produit.getNom());
				produit2.setType(produit.getType());
				produit2.setPrixAchat(mouvement.getProduit().getPrixAchat());
				produit2.setStock(stock);
				produit2.setQuantiteEnStock(mouvement.getQuantite());
				produit2.setQuantiteMin(produit.getQuantiteMin());
				produitService.rep.save(produit2);
				produitService.setQuantiteTotale(produit2.getNom());
			}
			else throw new NotFoundException("Mouvement non reconnu ou impossible.");
		}
		
		mouvement.setDate(LocalDateTime.now());
		mouvement.setProduit(produit2);
		rep.save(mouvement);
	}
	

}

