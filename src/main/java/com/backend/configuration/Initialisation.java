package com.backend.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.entities.Categorie;
import com.backend.entities.Fournisseur;
import com.backend.entities.UniteDeMesure;
import com.backend.repositories.*;

@Component
public class Initialisation {

	
	CategorieRepository categorieRepository;

	UniteDeMesureRepository uniteDeMesureRepository;
	
	FournisseurRepository fournisseurRepository;
	
	@Autowired
	public Initialisation(CategorieRepository categorieRepository, UniteDeMesureRepository uniteDeMesureRepository,
			FournisseurRepository fournisseurRepository) {
		
		this.categorieRepository = categorieRepository;
		this.uniteDeMesureRepository = uniteDeMesureRepository;
		this.fournisseurRepository = fournisseurRepository;
	}


	@PostConstruct
	public void Init()
	{
		if(!categorieRepository.findByDesignation("Non spécifiée").isPresent()) {
			Categorie categorie = new Categorie();
			categorie.setDesignation("Non spécifiée");
			categorieRepository.save(categorie);	
		}

		if(!uniteDeMesureRepository.findByDesignation("Non spécifiée").isPresent()) {
			UniteDeMesure uniteDeMesure = new UniteDeMesure();
			uniteDeMesure.setDesignation("Non spécifiée");
			uniteDeMesureRepository.save(uniteDeMesure);	
		}

		if(!fournisseurRepository.findByNom("Non spécifié").isPresent()) {
			Fournisseur fournisseur = new Fournisseur();
			fournisseur.setNom("Non spécifié");
			fournisseurRepository.save(fournisseur);	
		}
	}


}
