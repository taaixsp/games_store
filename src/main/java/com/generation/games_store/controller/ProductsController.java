package com.generation.games_store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.games_store.model.Products;
import com.generation.games_store.repository.CategoryRepository;
import com.generation.games_store.repository.ProductsRepository;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProductsController {

	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List <Products>> getAll(){
		return ResponseEntity.ok(productsRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Products> getById(@PathVariable Long id){
		return productsRepository.findById(id)
				.map(response->ResponseEntity.ok(response))
				.orElse(ResponseEntity.notFound().build());
	}
	@PostMapping
    public ResponseEntity <Products> postProducts(@Valid @RequestBody Products products){

        if(categoryRepository.existsById(products.getCategory().getId()))
            return ResponseEntity.status(HttpStatus.CREATED).body(productsRepository.save(products));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity <Products> putProducts(@Valid @RequestBody Products products){

        if(productsRepository.existsById(products.getId())) {
            if(categoryRepository.existsById(products.getCategory().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(productsRepository.save(products));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducts(@PathVariable Long id) {

        return productsRepository.findById(id)
                .map(response -> {
                    productsRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/lower_price/{Price}")
    public ResponseEntity<List <Products>> getPriceLowerThan(@PathVariable double Price){
    	return ResponseEntity.ok(productsRepository.findByPriceLessThanOrderByDesc(Price));
    }
    
    @GetMapping("/greater_price/{Price}")
    public ResponseEntity<List <Products>> getPriceGreaterThan(@PathVariable double Price){
    	return ResponseEntity.ok(productsRepository.findByPriceGreaterThanOrderByDesc(Price));
    }
}
	
