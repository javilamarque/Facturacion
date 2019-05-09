package com.itedes.facturacion.dto.purchase;

import org.json.JSONObject;

public class PurchaseRequestCreationDto {
	private Integer product;
	private Integer quantity;

	public PurchaseRequestCreationDto(JSONObject purchase) {
		product = purchase.getInt("product");
		quantity = purchase.getInt("quantity");
	}

	public Integer getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}
}