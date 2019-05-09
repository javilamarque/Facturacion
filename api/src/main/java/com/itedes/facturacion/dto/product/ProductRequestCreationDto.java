package com.itedes.facturacion.dto.product;

import org.json.JSONObject;

public class ProductRequestCreationDto {
	private String name;
	private String description;
	private Float price;

	public ProductRequestCreationDto(JSONObject product) {
		name = product.getString("name");
		description = product.getString("description");
		price = product.getFloat("price");
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
}