package com.backend.services;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.entities.Fournisseur;
import com.backend.entities.Produit;
import com.backend.entities.Utilisateur;
import com.backend.exceptions.*;
import com.backend.repositories.FournisseurRepository;

@Service
public class FournisseurService {
	
	@Autowired
	FournisseurRepository rep;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	Logger logger = LoggerFactory.getLogger(FournisseurService.class.getName());

	//Liste des fournisseurs
	public List<Fournisseur> getFournisseurs(Long id) throws NotFoundException
	{
		
		List<Fournisseur> fournisseurs= new ArrayList<Fournisseur>();
		if(id!=null) 
			fournisseurs.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun fournisseur avec l'id "+id+" n'existe")));
		
		else fournisseurs = rep.findAll();
			if(fournisseurs.isEmpty()) throw new NotFoundException("Aucun fournisseur trouvé");		
		
		return fournisseurs;
	}
	
	
	
	//Liste des produits
	public List<Produit> getProduits(Long id) throws NotFoundException
	{
		Fournisseur fournisseur=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun fournisseur avec l'id "+id+" n'existe"));
		
		List<Produit> produits = fournisseur.getProduits();
		
		if(produits.isEmpty()) throw new NotFoundException("Aucun produit de cette fournisseur n'existe");
		
		List<Produit> unique = produits.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Produit::getNom))),ArrayList::new));
		
		return unique;

	}
	
	
	
	//ajouter un fournisseur
	public void addFournisseur(Fournisseur fournisseur) throws ConflictException
	{
		if(rep.findByNom(fournisseur.getNom()).isPresent()) 
			throw new ConflictException("Un fournisseur avec la nom "+fournisseur.getNom()+" existe déjà.");
		
		rep.save(fournisseur);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a créé le fournisseur "+fournisseur.getNom());
	}
	
	
	

	//modifier une fournisseur
	public void updateFournisseur(Long id , Fournisseur fournisseur) throws ConflictException, NotFoundException
	{
		
		Fournisseur updated=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun fournisseur avec l'id "+id+" n'existe"));
		
		if(rep.findByNom(fournisseur.getNom()).isPresent() && !rep.findByNom(fournisseur.getNom()).get().equals(updated))
			throw new ConflictException("Un fournisseur avec la nom "+fournisseur.getNom()+" existe déjà.");
		
		if(fournisseur.getNom()!=null && !fournisseur.getNom().isEmpty()) updated.setNom(fournisseur.getNom());
		if(fournisseur.getTelephone()!=null && !fournisseur.getTelephone().isEmpty()) updated.setTelephone(fournisseur.getTelephone());
		if(fournisseur.getAdresse()!=null && !fournisseur.getAdresse().isEmpty()) updated.setAdresse(fournisseur.getAdresse());
		
		rep.save(updated);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a modifié le fournisseur "+updated.getNom());
		
	}

	
	
	//supprimer une fournisseur
	public void deleteFournisseur(Long id) throws NotFoundException
	{
		
		Fournisseur fournisseur= rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun fournisseur avec l'id "+id+" n'existe"));
		rep.delete(fournisseur);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a supprimé le fournisseur "+fournisseur.getNom());
		
	}

}
