package com.backend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@Column(name="PRODUITS_FNSR")
	@OneToMany(mappedBy="fournisseur", cascade=CascadeType.ALL)
	List<Produit> produits;


}
