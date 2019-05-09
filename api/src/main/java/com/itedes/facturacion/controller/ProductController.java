package com.itedes.facturacion.controller;

import com.itedes.facturacion.dto.product.ProductRequestCreationDto;
import com.itedes.facturacion.dto.product.ProductResponseDto;

import java.util.ArrayList;


public class ProductController extends Controller {
	public ProductResponseDto create(ProductRequestCreationDto productDto) throws Exception {
		String queryString = "SELECT * FROM product('" 
			+ productDto.getName() +"', '"
			+ productDto.getDescription() + "', "
			+ productDto.getPrice().toString() 
		+ ")";

		initializeConnection();

		try {	
			resultset = statement.executeQuery(queryString);

			ProductResponseDto productResponse = null;

			while(resultset.next()) 
				productResponse = new ProductResponseDto (
					resultset.getInt("id"), 
					resultset.getString("name"), 
					resultset.getString("description"), 
					resultset.getFloat("price"), 
					resultset.getInt("stock")
				);
			
			closeConnection();
			
			return productResponse;
		} catch(Exception e) {
            throw new Exception(e);
		}
	}


	public void destroy(Integer id) throws Exception {
		String queryString = "SELECT product_destroy(" + id.toString() + ")";

		initializeConnection();

		try {	
			statement.executeQuery(queryString);

			closeConnection();
		} catch(Exception e) {
            throw new Exception(e);
		}
	}


	public ArrayList<ProductResponseDto> search(String name) throws Exception {
		String queryString = "SELECT * FROM product_search('" + name +"')";

		initializeConnection();

		try {	
			resultset = statement.executeQuery(queryString);

			ArrayList<ProductResponseDto> products = new ArrayList<ProductResponseDto>();

			while(resultset.next()) { 
				ProductResponseDto productResponse = new ProductResponseDto (
					resultset.getInt("id"), 
					resultset.getString("name"), 
					resultset.getString("description"), 
					resultset.getFloat("price"), 
					resultset.getInt("stock")
				);

				products.add(productResponse);
			}
			
			closeConnection();
			
			return products;
		} catch(Exception e) {
            throw new Exception(e);
		}
	}


	public void setName(Integer id, String name) throws Exception {
		String queryString = "SELECT product_set_name(" + id.toString() + ", '" + name + "')"; 

		initializeConnection();

		try {	
			statement.executeQuery(queryString);		
			closeConnection();
		} catch(Exception e) {
            throw new Exception(e);
		}
	}
}