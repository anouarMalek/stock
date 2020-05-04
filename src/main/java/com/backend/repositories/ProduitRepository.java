package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.models.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

	Optional<Produit> findByNom(String nom);

}
