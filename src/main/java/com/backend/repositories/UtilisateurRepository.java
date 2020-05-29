package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Utilisateur;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
	Optional<Utilisateur> findByUsername(String username);

	Optional<Utilisateur> findByCin(String cin);

}
