package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Emplacement;
import com.backend.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {


	Optional<Stock> findByEmplacement(Emplacement emplacement);

}
