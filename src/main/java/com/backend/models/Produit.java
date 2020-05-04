package com.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name="PRODUIT")
public @Data class Produit {
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="ID_PROD")
		Long id;
		@Column(name="NOM_PROD", unique=true)
		String nom;
		@Column(name="DESCRIPTION_PROD")
		String description;
		@Column(name="TYPE_PROD")
		String Type;
		@Column(name="PRIX_ACHAT_PROD")
		double prixAchat;
		@Column(name="QUANTITE_PROD")
		int quantite;
		@JoinColumn(name="CATEGORIE_PROD")
		@ManyToOne(targetEntity=Categorie.class, fetch=FetchType.EAGER)
		Categorie categorie;
		@JoinColumn(name="FOURNISSEUR_PROD")
		@ManyToOne(targetEntity=Fournisseur.class, fetch=FetchType.EAGER)
		Fournisseur fournisseur;
		@JoinColumn(name="UNITE_DE_MESURE_PROD")
		@ManyToOne(targetEntity=UniteDeMesure.class, fetch=FetchType.EAGER)
		UniteDeMesure uniteDeMesure;
		@JoinColumn(name="EMPLACEMENT_PROD")
		@ManyToOne(targetEntity=Emplacement.class, fetch=FetchType.EAGER)
		Emplacement emplacement;


}
