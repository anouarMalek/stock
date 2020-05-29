package com.backend.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="UTILISATEUR")
public @Data class Utilisateur {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_UTILISATEUR")
	Long id;
	@Column(name="NOM_UTILISATEUR")
	String nom;
	@Column(name="PRENOM_UTILISATEUR")
	String prenom;
	@Column(name="CIN_UTILISATEUR",unique=true)
	String cin;
	@Column(name="ADRESSE_UTILISATEUR")
	String adresse;
	@Column(name="TELEPHONE_UTILISATEUR")
	String telephone;
	@Column(name="EMAIL_UTILISATEUR")
	String email;
	@Column(name="USERNAME_UTILISATEUR",nullable=false, unique=true)
	String username;
	@Column(name="PASSWORD_UTILISATEUR",nullable=false)
	String password;
	@Column(name="ROLE_UTILISATEUR",nullable=false)
	String role;
}
