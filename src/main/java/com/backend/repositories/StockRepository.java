package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.models.Emplacement;
import com.backend.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {


	Optional<Stock> findByEmplacement(Emplacement emplacement);

}
