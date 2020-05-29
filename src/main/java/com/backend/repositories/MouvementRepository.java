package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Mouvement;

public interface MouvementRepository extends JpaRepository<Mouvement, Long> {

}
