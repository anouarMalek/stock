package com.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="EMPLACEMENT")
public @Data class Emplacement {
	
	@Id
	@Column(name="ID_EMP")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@Column(name="DESIGNATION_EMP", unique=true)
	String designation;
	@Column(name="ADRESSE_EMP", unique=true)
	String adresse;
	@JoinColumn(name="STOCK_EMP")
	@OneToOne(targetEntity=Stock.class, fetch=FetchType.EAGER)
	Stock stock;

}
