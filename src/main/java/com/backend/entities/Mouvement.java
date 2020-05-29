package com.backend.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;



@Entity
@Table(name="MOUVEMENT")
public @Data class Mouvement {
	
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
	@ManyToOne
	Produit produit;
	
	@JoinColumn(name="STOCK_MVNT")
	@ManyToOne
	Stock stock;
	

}
