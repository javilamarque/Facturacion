package com.itedes.facturacion.dto.purchase;

import org.json.JSONObject;

public class PurchaseResponseDto {
	private Integer id;
	private String date;
	private String time;
	private Integer productId;
	private String productName;
	private String productDescription;
	private Integer productQuantity; 

	public PurchaseResponseDto (
		Integer id,
		String date,
		String time,
		Integer productId,
		String productName,
		String productDescription,
		Integer productQuantity
	) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productQuantity = productQuantity;
	}

	public Integer getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public Integer getproductQuantity() {
		return productQuantity;
	}
}