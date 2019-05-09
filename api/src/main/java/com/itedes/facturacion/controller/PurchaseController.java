package com.itedes.facturacion.controller;

import com.itedes.facturacion.dto.purchase.PurchaseRequestCreationDto;
import com.itedes.facturacion.dto.purchase.PurchaseResponseDto;

import java.util.ArrayList;

public class PurchaseController extends Controller {
	public PurchaseResponseDto create(PurchaseRequestCreationDto purchaseDto) throws Exception {
		String queryString = "SELECT purchase FROM purchase (" 
			+ purchaseDto.getProduct().toString() + ", "
			+ purchaseDto.getQuantity().toString()
		+ ")";

		initializeConnection();

		try {	
			resultset = statement.executeQuery(queryString);

			Integer purchaseId = null;

			while(resultset.next()) 
				purchaseId = resultset.getInt("purchase");
			
			queryString = "SELECT * FROM purchase_identify_by_id(" + purchaseId.toString() + ")";

			resultset = statement.executeQuery(queryString);
		
			PurchaseResponseDto responseDto = null;
			
			while(resultset.next())
				responseDto = new PurchaseResponseDto (
					resultset.getInt("id"), 
					resultset.getString("date"), 
					resultset.getString("time"), 
					resultset.getInt("product"), 
					resultset.getString("name"), 
					resultset.getString("description"), 
					resultset.getInt("quantity")
				);
			
			closeConnection();
			
			return responseDto;
		} catch(Exception e) {
            throw new Exception(e);
		}
	}


	public void destroy(Integer id) throws Exception {
		String queryString = "SELECT purchase_destroy(" + id.toString() + ")";

		initializeConnection();

		try {	
			statement.executeQuery(queryString);

			closeConnection();
		} catch(Exception e) {
            throw new Exception(e);
		}
	}


	public ArrayList<PurchaseResponseDto> search (
		String from, 
		String to, 
		String name
	) throws Exception {
		String queryString = "SELECT * FROM purchase_search(";

		if(!from.equals("--"))
			queryString += "p_date_from := '" + from + "'";
		
		if(!to.equals("--")) {
			if(!from.equals("--"))
				queryString += ", ";
			
			queryString += "p_date_to := '" + to + "'";
		}

		if(!name.equals("%")) {
			if(!from.equals("--") || !to.equals("--"))
				queryString += ", ";
			
			queryString += "p_product_name := '" + name + "'";
		}

		queryString += ")";


		initializeConnection();

		try {	
			resultset = statement.executeQuery(queryString);

			ArrayList<PurchaseResponseDto> purchases = new ArrayList<PurchaseResponseDto>();

			while(resultset.next()) { 
				PurchaseResponseDto purchaseResponse = new PurchaseResponseDto (
					resultset.getInt("id"), 
					resultset.getString("date"), 
					resultset.getString("time"), 
					resultset.getInt("product"), 
					resultset.getString("name"), 
					resultset.getString("description"), 
					resultset.getInt("quantity")
				);

				purchases.add(purchaseResponse);
			}
			
			closeConnection();
			
			return purchases;
		} catch(Exception e) {
            throw new Exception(e);
		}
	}
}