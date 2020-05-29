package com.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Produit;
import com.backend.entities.Stock;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

	Optional<Produit> findByNom(String nom);

	List<Produit> findAllByNom(String nom);


}
