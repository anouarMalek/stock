package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.models.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

	Optional<Categorie> findByDesignation(String designation);

}
