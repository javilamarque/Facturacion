package com.itedes.facturacion.dto.sale;

import org.json.JSONObject;

public class SaleRequestCreationDto {
    private Integer product;
    private Integer quantity;

    public SaleRequestCreationDto(JSONObject sale){
        product = sale.getInt("product");
        quantity = sale.getInt("quantity");
    }

    public Integer getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}