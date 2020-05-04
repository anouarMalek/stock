package com.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name="STOCK")
public @Data class Stock {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_STK")
	Long id;
	@Column(name="TELEPHONE_STK")
	String telephone;
	@Column(name="FAX_STK")
	String fax;
	@Column(name="PRODUIT_STK")
	@OrderColumn(name="PRODUITS_LIST_INDEX")
	@OneToMany(targetEntity=Produit.class, fetch=FetchType.EAGER)
	Produit[] produits;
	@JoinColumn(name="EMPLACEMENT_STK", unique=true)
	@OneToOne(targetEntity=Emplacement.class, fetch=FetchType.EAGER)
	Emplacement emplacement;

}
