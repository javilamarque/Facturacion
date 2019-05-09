package com.itedes.facturacion.model;

public class Product {
	private Integer id;
	private String name;
	private String description;
	private Float price;
	private Integer stock;

	public Product (
		Integer id, 
		String name, 
		String description, 
		Float price, 
		Integer stock
	) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public Integer getId() {
		return id;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void addItemsToStock(Integer quantity) {
		if(quantity > 0)
			stock += quantity;
	}

	public void removeItemfFromStock(Integer quantity) {
		if(quantity > 0 && stock >= quantity)
			stock -= quantity;
	}
}