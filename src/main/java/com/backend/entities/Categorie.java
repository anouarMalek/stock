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
	
	@JsonIgnore
	@Column(name="PRODUITS_CAT")
	@OneToMany(mappedBy="categorie", cascade=CascadeType.ALL)
	List<Produit> produits;
}
