package com.backend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="STOCK")
public @Data class Stock{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_STK")
	Long id;
	
	@Column(name="TELEPHONE_STK")
	String telephone;
	
	@Column(name="FAX_STK")
	String fax;
	
	@JsonIgnore
	@Column(name="PRODUITS_STK")
	@OneToMany(mappedBy="stock", cascade=CascadeType.ALL)
	List<Produit> produits;
	
	@JsonIgnore
	@Column(name="INVENTAIRES_STK")
	@OneToMany(mappedBy="stock", cascade=CascadeType.ALL)
	List<Inventaire> inventaires;
	
	@JoinColumn(name="EMPLACEMENT_STK", unique=true)
	@OneToOne
	Emplacement emplacement;
	
	@JsonIgnore
	@Column(name="MOUVEMENTS_STK")
	@OneToMany(mappedBy="stock", cascade=CascadeType.ALL)
	List<Mouvement> mouvements;

}
