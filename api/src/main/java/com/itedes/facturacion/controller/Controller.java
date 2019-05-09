package com.itedes.facturacion.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public abstract class Controller {
	private Connection connection ;
    protected Statement statement;
    protected ResultSet resultset ;
	
	protected void initializeConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/facturacion",
                    "dba", 
                    "1234"
            );
            
            statement = connection.createStatement();   
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }  
    }

    protected void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (Exception e) {} 
	}
}