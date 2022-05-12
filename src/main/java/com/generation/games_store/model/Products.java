package com.generation.games_store.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_products")

public class Products {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Atribute products is mandatory")
	@Size(min = 5, max = 255, message = "Atribute text must have minimum 5 characters and maximum 255 characters")
	private String name;
	
	@Size(min = 5, max = 500, message = "Atribute description must have minimum 5 characters and maximum 500 characters")
	private String description;
	
	@NotBlank(message = "Atribute console is mandatory")
	@Size(min = 5, max = 255, message = "Atribute console must have minimum 5 characters and maximum 255 characters")
	private String console;
	
	@NotEmpty(message = "Atribute quantity is mandatory")
	@Positive(message = "Atribute quantity must be positive")
	private int quantity;
	
	@NotEmpty(message = "Atribute price is mandatory")
	@Positive(message = "Atribute price must be positive")
	private double price;
	
	@NotBlank(message = "Atribute picture is mandatory")
	@Size(min = 5, max = 1000, message = "Atribute picture must have minimum 5 characters and maximum 1000 characters")
	private String picture;
	
	@ManyToOne
	@JsonIgnoreProperties("products")
	private Category category;

	@ManyToOne
	@JsonIgnoreProperties("products")
	private Usuario usuario;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
