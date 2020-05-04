package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.models.Emplacement;

public interface EmplacementRepository extends JpaRepository<Emplacement, Long> {

	Optional<Emplacement> findByDesignation(String designation);

}
