package com.itedes.facturacion.dto.sale;

import org.json.JSONObject;

public class SaleResponseDto {
    private Integer id;
    private String date;
    private String time;
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer productQuantity;

    public SaleResponseDto (
        Integer id,
        String date,
        String time,
        Integer productId,
        String productName,
        String productDescription,
        Integer productQuantity
    ){
        this.id = id;
        this.date = date;
        this.time = time;
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
    }
    public Integer getId(){
        return id;
    }

    public String getDate(){
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }
}