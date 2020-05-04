package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Produit;
import com.backend.repositories.ProduitRepository;

@Service
public class ProduitService {
	
	@Autowired
	ProduitRepository rep;
	

	//Liste des produits
	public List<Produit> getProduits() throws NotFoundException
	{
		
		List<Produit> produits = rep.findAll();
		if(produits.isEmpty()) throw new NotFoundException("Aucun produit trouvé");		
		
		return produits;
	}
	
	
	//ajouter un produit
	public void addProduit(Produit produit) throws AlreadyExistsException
	{
		if(rep.findByNom(produit.getNom()).isPresent()) 
			throw new AlreadyExistsException("Un produit avec la nom "+produit.getNom()+" existe déjà.");
		
		rep.save(produit);
	}
	
	
	

	//modifier un produit
	public void updateProduit(String nom , Produit produit) throws AlreadyExistsException, NotFoundException
	{
		
		Produit updated=rep.findByNom(nom)
				.orElseThrow(() -> new NotFoundException("Aucun produit avec la nom "+nom+" n'existe"));
		
		if(rep.findByNom(produit.getNom()).isPresent() && !rep.findByNom(produit.getNom()).get().equals(updated))
			throw new AlreadyExistsException("Un produit avec la nom "+produit.getNom()+" existe déjà.");
		
		Long id=updated.getId();
		updated=produit;
		updated.setId(id);
		
		rep.save(updated);
		
	}

	
	
	//supprimer un produit
	public void deleteProduit(String nom) throws NotFoundException
	{
		
		Produit produit= rep.findByNom(nom)
				.orElseThrow(() -> new NotFoundException("Aucun produit avec la nom "+nom+" n'existe"));
		rep.delete(produit);
		
	}

}