package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Mouvement;
import com.backend.repositories.MouvementRepository;

@Service
public class MouvementService {
	
	@Autowired
	MouvementRepository rep;
	

	//Liste des mouvements
	public List<Mouvement> getMouvements() throws NotFoundException
	{
		
		List<Mouvement> mouvements = rep.findAll();
		if(mouvements.isEmpty()) throw new NotFoundException("Aucun mouvement trouvé");		
		
		return mouvements;
	}
	
	
	//ajouter une mouvement
	public void addMouvement(Mouvement mouvement)
	{
		rep.save(mouvement);
	}
	

}

