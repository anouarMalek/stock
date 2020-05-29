package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

	Optional<Fournisseur> findByNom(String nom);

}
