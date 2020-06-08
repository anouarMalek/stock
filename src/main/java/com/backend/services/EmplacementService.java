package com.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entities.Emplacement;
import com.backend.entities.Stock;
import com.backend.entities.Utilisateur;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.EmplacementRepository;

@Service
@Transactional
public class EmplacementService {
	
	@Autowired
	EmplacementRepository rep;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	Logger logger = LoggerFactory.getLogger(EmplacementService.class.getName());
	

	//Liste des emplacements
	public List<Emplacement> getEmplacements(Long id) throws NotFoundException
	{
		
		List<Emplacement> emplacements = new ArrayList<Emplacement>();
		if(id!=null) 
			emplacements.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe")));
								
		else emplacements = rep.findAll();
			if(emplacements.isEmpty()) throw new NotFoundException("Aucun emplacement trouvé");		
		
		return emplacements;
	}
	
	
	
	//Stock
	public Stock getStock(Long id) throws NotFoundException
	{
		Emplacement emplacement = rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe"));
			
		if(emplacement.getStock()== null) throw new NotFoundException("Stock pas encore créé pour cette emplacement.");
		return emplacement.getStock();
	}
	
	
	//les emplacements ayant un stock
	public List<Emplacement> stockCreated() throws NotFoundException
	{
		List<Emplacement> emplacements = new ArrayList<Emplacement>();
		//Stock iterator= new Stock();
		List<Stock> stocks = stockService.getStocks(null);
		for (Stock stock : stocks) {
			emplacements.add(stock.getEmplacement());
		}
		
		return emplacements;
	}
	
	
	//les emplacements sans stock
		public List<Emplacement> stockNotCreated() throws NotFoundException
		{
			List<Emplacement> emplacements = getEmplacements(null);
			
			for (Emplacement emplacement : emplacements) {
				if(emplacement.getStock()!=null) emplacements.remove(emplacement);
				if(emplacements.size()==0) break;
			}
			
			if(emplacements.isEmpty()) throw new NotFoundException("Aucun emplacement trouvé");
			
			return emplacements;
		}
	
	
	
	//ajouter une emplacement
	public void addEmplacement(Emplacement emplacement) throws ConflictException
	{
		if(rep.findByDesignation(emplacement.getDesignation()).isPresent()) 
			throw new ConflictException("Un emplacement avec la designation "+emplacement.getDesignation()+" existe déjà.");
		
		rep.save(emplacement);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a créé l'emplacement "+emplacement.getDesignation());
	}
	
	
	

	//modifier une emplacement
	public void updateEmplacement(Long id , Emplacement emplacement) throws ConflictException, NotFoundException
	{
		
		Emplacement updated=rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe"));
		
		if(rep.findByDesignation(emplacement.getDesignation()).isPresent() && !rep.findByDesignation(emplacement.getDesignation()).get().equals(updated))
			throw new ConflictException("Un emplacement avec la designation "+emplacement.getDesignation()+" existe déjà.");
		
		if(emplacement.getDesignation()!=null && !emplacement.getDesignation().isEmpty()) updated.setDesignation(emplacement.getDesignation());
		if(emplacement.getAdresse()!=null && !emplacement.getAdresse().isEmpty()) updated.setAdresse(emplacement.getAdresse());
		
		rep.save(updated);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a modifié l'emplacement "+updated.getDesignation());
		
	}

	
	
	//supprimer une emplacement
	public void deleteEmplacement(Long id) throws NotFoundException
	{
		
		Emplacement emplacement= rep.findById(id)
				.orElseThrow(() -> new NotFoundException("Aucun emplacement avec l'id "+id+" n'existe"));
		if(emplacement.getStock()!=null)
		{
			stockService.deleteStock(emplacement.getStock().getId());
		}
		
		rep.delete(emplacement);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'administrateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a supprimé l'emplacement "+emplacement.getDesignation());
		
	}

}
