package com.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import lombok.*;


@Entity
@Table(name="FOURNISSEUR")
public @Data class Fournisseur {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_FNSR")
	Long id;
	@Column(name="NOM_FNSR", unique=true)
	String nom;
	@Column(name="TELEPHONE_FNSR")
	String telephone;
	@Column(name="ADRESSE_FNSR")
	String adresse;
	@Column(name="PRODUITS_FNSR")
	@OrderColumn(name="PRODUITS_LIST_INDEX")
	@OneToMany(targetEntity=Produit.class, fetch=FetchType.EAGER)
	Produit[] produits;


}
