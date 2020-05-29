package com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entities.Inventaire;
import com.backend.entities.Mouvement;
import com.backend.entities.Produit;
import com.backend.entities.Stock;
import com.backend.exceptions.ConflictException;
import com.backend.exceptions.NotFoundException;
import com.backend.services.StockService;

@RestController
public class StockController {


	StockService service;

	@Autowired
	public StockController(StockService service) {

		this.service=service;
	}


	//GET

	@GetMapping("/stocks")
	@ResponseStatus(HttpStatus.OK)
	public List<Stock> getStocks(@RequestParam(name="id", required=false) Long id)  throws NotFoundException
	{
		return service.getStocks(id);

	}


	//GET

	@GetMapping("/stock/{id}/produits")
	@ResponseStatus(HttpStatus.OK)
	public List<Produit> getProduits(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		return service.getProduits(id);

	}


	//GET

	@GetMapping("/stock/{id}/inventaires")
	@ResponseStatus(HttpStatus.OK)
	public List<Inventaire> getInventaires(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		return service.getInventaires(id);

	}
	
	//GET

		@GetMapping("/stock/{id}/mouvements")
		@ResponseStatus(HttpStatus.OK)
		public List<Mouvement> getMouvements(@PathVariable(name="id") Long id)  throws NotFoundException
		{
			return service.getMouvements(id);

		}


		
	//POST

	@PostMapping("/stock")
	@ResponseStatus(HttpStatus.CREATED)
	public void addStock(@RequestBody Stock stock)  throws ConflictException
	{
		service.addStock(stock);
	}


	//PUT
	@PutMapping("/stock/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateStock(@PathVariable(name="id") Long id,@RequestBody Stock stock)  throws ConflictException, NotFoundException
	{
		service.updateStock(id,stock);
	}


	//DELETE

	@DeleteMapping("/stock/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteStock(@PathVariable(name="id") Long id)  throws NotFoundException
	{
		service.deleteStock(id);
	}




}
