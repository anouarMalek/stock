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

import lombok.Data;

@Entity
@Table(name="UNITE_DE_MESURE")
public @Data class UniteDeMesure {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_UDM")
	Long id;
	
	@Column(name="DESIGNATION_UDM", unique=true)
	String designation;
	
	@Column(name="DESCRIPTION_UDM")
	String description;
	
	@JsonIgnore
	@Column(name="PRODUITS_UDM")
	@OneToMany(mappedBy="uniteDeMesure", cascade=CascadeType.ALL)
	List<Produit> produits;
}
