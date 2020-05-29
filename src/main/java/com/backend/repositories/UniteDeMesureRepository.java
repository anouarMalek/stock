package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.UniteDeMesure;

public interface UniteDeMesureRepository extends JpaRepository<UniteDeMesure, Long> {

	Optional<UniteDeMesure> findByDesignation(String designation);

}
