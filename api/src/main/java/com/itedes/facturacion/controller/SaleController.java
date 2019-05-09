package com.itedes.facturacion.controller;

import com.itedes.facturacion.dto.sale.SaleRequestCreationDto;
import com.itedes.facturacion.dto.sale.SaleResponseDto;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SaleController extends Controller {
    public SaleResponseDto create(SaleRequestCreationDto saleDto) throws Exception {
        String queryString = "SELECT sale FROM sale ("
            + saleDto.getProduct().toString() + ", "
            + saleDto.getQuantity().toString()
            +")";

        initializeConnection();

        try {
            resultset = statement.executeQuery(queryString);

            Integer saleId = null;

            while(resultset.next())
                saleId = resultset.getInt("sale");

            queryString = "SELECT * FROM sale_identify_by_id(" + saleId.toString() + ")";

            resultset = statement.executeQuery(queryString);

            SaleResponseDto responseDto = null;

            while(resultset.next())
                responseDto = new SaleResponseDto (
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
        }catch(Exception e) {
            throw new Exception(e);
        }
    }

    public void destroy(Integer id) throws Exception {
        String queryString = "SELECT sale_destroy(" + id.toString() + ")";

        initializeConnection();

        try {
            statement.executeQuery(queryString);

            closeConnection();
        }catch(Exception e){
            throw new  Exception(e);
        }
    }


    public ArrayList<SaleResponseDto> search(
        String from,
        String to,
        String name
    ) throws Exception {
        String queryString = "SELECT * FROM sale_search (";

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

            queryString += ")";
        }
            initializeConnection();

        try {
            resultset = statement.executeQuery(queryString);

            ArrayList<SaleResponseDto> sales = new ArrayList<SaleResponseDto>();

            while(resultset.next()) {
                SaleResponseDto saleResponse = new SaleResponseDto (
                    resultset.getInt("id"),
                    resultset.getString("date"),
                    resultset.getString("time"),
                    resultset.getInt("product"),
                    resultset.getString("name"),
                    resultset.getString("description"),
                    resultset.getInt("quantity")
                );

                sales.add(saleResponse);
            }

            closeConnection();

            return sales;
        }catch(Exception e) {
            throw new Exception(e);
        }        
    }
}