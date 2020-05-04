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
	@Column(name="PRODUITS_UDM")
	@OrderColumn(name="PRODUITS_LIST_INDEX")
	@OneToMany(targetEntity=Produit.class, fetch=FetchType.EAGER)
	Produit[] produits;
}
