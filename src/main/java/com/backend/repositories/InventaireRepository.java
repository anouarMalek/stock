package com.backend.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.models.Inventaire;
import com.backend.models.Stock;

public interface InventaireRepository extends JpaRepository<Inventaire, Long> {

	Optional<Inventaire> findByStockAndStock(Stock stock, LocalDateTime date);

}
