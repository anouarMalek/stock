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
@Table(name="CATEGORIE")
public @Data class Categorie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CAT")
	Long id;
	@Column(name="DESIGNATION_CAT", unique=true)
	String designation;
	@Column(name="DESCRIPTION_CAT")
	String description;
	@Column(name="PRODUITS_CAT")
	@OrderColumn(name="PRODUITS_LIST_INDEX")
	@OneToMany(targetEntity=Produit.class, fetch=FetchType.EAGER)
	Produit[] produits;
}
