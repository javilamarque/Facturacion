package com.itedes.facturacion.dto.product;

public class ProductResponseDto {
	private Integer id;
	private String name;
	private String description;
	private Float price;
	private Integer stock;

	public ProductResponseDto (
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

	public String getDescription() {
		return description;
	}

	public Float getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}
}