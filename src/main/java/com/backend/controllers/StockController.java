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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.exceptions.AlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.models.Emplacement;
import com.backend.models.Produit;
import com.backend.models.Stock;
import com.backend.services.StockService;

@RequestMapping("/stocks")
@RestController
public class StockController {
	
	
	StockService service;
	
	@Autowired
	public StockController(StockService service) {
		
		this.service=service;
	}
	
	
	//GET
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Stock> getStocks()  throws NotFoundException
	{
		return service.getStocks();
		 
	}
	
	
	//GET
	
		@GetMapping("/products")
		@ResponseStatus(HttpStatus.OK)
		public List<Produit> getProducts(@RequestParam Emplacement emplacement)  throws NotFoundException
		{
			return service.getProducts(emplacement);
			 
		}
	
	
	//POST
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addStock(@RequestBody Stock stock)  throws AlreadyExistsException
	{
		service.addStock(stock);
	}
	
	
	//PUT
	@PutMapping("/{emplacement}")
	@ResponseStatus(HttpStatus.OK)
	public void updateStock(@PathVariable Emplacement emplacement,@RequestBody Stock stock)  throws AlreadyExistsException, NotFoundException
	{
		service.updateStock(emplacement,stock);
	}
	
	
	//DELETE
	
	@DeleteMapping("/{emplacement}") 
	@ResponseStatus(HttpStatus.OK)
	public void deleteStock(@PathVariable Emplacement emplacement)  throws NotFoundException
	{
		service.deleteStock(emplacement);
	}
	
	
	

}
