package com.generation.games_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.games_store.model.Category;

@Repository

public interface CategoryRepository extends JpaRepository<Category, Long>{
	public List<Category>findAllByGenreContainingIgnoreCase(@Param("genre") String genre);
}
