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

import com.generation.games_store.model.Category;
import com.generation.games_store.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins="*", allowedHeaders="*")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity <List<Category>> getAll(){
		return ResponseEntity.ok(categoryRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getById(@PathVariable Long id){
		return categoryRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/genre/{genre}")
	public ResponseEntity<List<Category>> getByGenre(@PathVariable String genre){
		return ResponseEntity.ok(categoryRepository.findAllByGenreContainingIgnoreCase(genre));
	}
	
	@PostMapping
	public ResponseEntity<Category> postCategory(@Valid @RequestBody Category category) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
	}
	
	@PutMapping
	public ResponseEntity<Category> putCategory(@Valid @RequestBody Category category) {
					
		return categoryRepository.findById(category.getId())
				.map(response -> {
					return ResponseEntity.ok().body(categoryRepository.save(category));
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		
		return categoryRepository.findById(id)
				.map(response -> {
					categoryRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
