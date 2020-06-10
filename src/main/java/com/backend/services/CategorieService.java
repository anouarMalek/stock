package com.backend.services;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.entities.Categorie;
import com.backend.entities.Produit;
import com.backend.entities.Utilisateur;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.CategorieRepository;

import lombok.experimental.UtilityClass;

@Service
public class CategorieService {
	
	@Autowired
	CategorieRepository rep;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	Logger logger = LoggerFactory.getLogger(CategorieService.class.getName());

	//Liste des categories
	public List<Categorie> getCategories(Long id) throws NotFoundException
	{
		
		List<Categorie> categories = new ArrayList<Categorie>();
		if(id!=null) categories.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune catégorie trouvée avec l'id "+id)));
		else categories=rep.findAll();
			if(categories.isEmpty()) throw new NotFoundException("Aucune catégorie trouvée");
		
		return categories;
	}
	
	
	public Optional<Categorie> getCategorieByDesignation(String designation)
	{
		Optional<Categorie> categorie = rep.findByDesignation(designation);
		
		return categorie;
	}
	
	
	//Liste des produits
		public List<Produit> getProduits(Long id) throws NotFoundException
		{
			Categorie categorie = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune catégorie trouvée avec l'id "+id));		
			List<Produit> produits= categorie.getProduits();
			if(produits.isEmpty()) throw new NotFoundException("Aucun produit trouvé dans cette catégorie");
				
			List<Produit> unique = produits.stream()
	                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Produit::getNom))),ArrayList::new));
			
			return unique;
		}
	
	
	//ajouter une categorie
	public void addCategorie(Categorie categorie) throws ConflictException
	{
		if(rep.findByDesignation(categorie.getDesignation()).isPresent()) 
			throw new ConflictException("Une categorie avec la designation "+categorie.getDesignation()+" existe déjà.");
		
		rep.save(categorie);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a créé la catégorie "+categorie.getDesignation());
	}
	
	
	

	//modifier une categorie
	public void updateCategorie(Long id , Categorie categorie) throws ConflictException, NotFoundException
	{
		
		Categorie updated=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucune catégorie avec l'id "+id+" n'existe"));
		
		if(rep.findByDesignation(categorie.getDesignation()).isPresent() && !rep.findByDesignation(categorie.getDesignation()).get().equals(updated))
			throw new ConflictException("Une categorie avec la designation "+categorie.getDesignation()+" existe déjà.");
		
		if(categorie.getDesignation()!=null && !categorie.getDesignation().isEmpty()) updated.setDesignation(categorie.getDesignation());
		if(categorie.getDescription()!=null && !categorie.getDescription().isEmpty()) updated.setDescription(categorie.getDescription());
		
		rep.save(updated);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a modifié la catégorie "+updated.getDesignation());
		
	}

	
	
	//supprimer une categorie
	public void deleteCategorie(Long id) throws NotFoundException
	{
		
		Categorie categorie= rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucune catégorie avec l'id "+id+" n'existe"));
		List<Produit> produits = getProduits(id);
		Categorie inconnue = rep.findByDesignation("Non spécifiée").get();
		for (Produit produit : produits) {
			produit.setCategorie(inconnue);
		}
		categorie.setProduits(null);
		rep.save(categorie);
		rep.delete(categorie);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a supprimé la catégorie "+categorie.getDesignation());
	}

}
