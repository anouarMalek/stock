package com.backend.models;

import java.time.LocalDateTime;

import javax.persistence.*;



@Entity
@Table(name="MOUVEMENT")
public class Mouvement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MVNT")
	Long id;
	@Column(name="DATE_MVNT")
	LocalDateTime date;
	@Column(name="TYPE_MVNT")
	String type;
	@Column(name="QUANTITE_MVNT")
	int quantite;
	@JoinColumn(name="PRODUIT_MVNT")
	@ManyToOne(targetEntity=Produit.class, fetch=FetchType.EAGER)
	Produit produit;
	@JoinColumn(name="STOCK_MVNT")
	@ManyToOne(targetEntity=Stock.class, fetch=FetchType.EAGER)	
	Stock stock;
	

}
