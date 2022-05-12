package com.generation.games_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.games_store.model.Products;

@Repository

public interface ProductsRepository extends JpaRepository<Products, Long> {
	public List<Products> findAllByNameContainingIgnoreCase(@Param("name") String name);
	
	public List <Products> findByPriceLessThanOrderByPriceDesc(double price);
	public List <Products> findByPriceGreaterThanOrderByPriceDesc(double price);
	

}
