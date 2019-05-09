package com.itedes.facturacion.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.PATCH;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.itedes.facturacion.controller.ProductController;
import com.itedes.facturacion.dto.product.ProductRequestCreationDto;
import com.itedes.facturacion.dto.product.ProductResponseDto;

import java.util.ArrayList;

import org.json.*;

@Path("/product")
public class ProductResource {
	ProductController controller = new ProductController();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String p_product) {

		ProductRequestCreationDto productDto = new ProductRequestCreationDto (
			new JSONObject(p_product)
		);
		
		try {
			ProductResponseDto productResponseDto = controller.create(productDto);

			JSONObject response = new JSONObject();

			response.put("status", "OK");
			response.put("message", "Operation completed");
			response.put("data", new JSONObject(productResponseDto));

			return Response.status(Status.OK).entity(response.toString()).build();
		} catch(Exception e) {
			JSONObject response = new JSONObject();

			response.put("status", "ERROR");
			response.put("message", "Operation failed");
			response.put("data", e.toString());
	
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
				response.toString()
			).build();
		}	
	}
	

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
    public Response destroy(@PathParam("id") Integer id) {	
		try {
			controller.destroy(id);

			JSONObject response = new JSONObject();

			response.put("status", "OK");
			response.put("message", "Operation completed");

			return Response.status(Status.OK).entity(response.toString()).build();
		} catch(Exception e) {
			JSONObject response = new JSONObject();

			response.put("status", "ERROR");
			response.put("message", "Operation failed");
			response.put("data", e.toString());
	
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
				response.toString()
			).build();
		}	
	}
	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@DefaultValue("%") @QueryParam("name") String name) {	
		try {
			ArrayList<ProductResponseDto> products = controller.search(name);

			JSONObject response = new JSONObject();

			response.put("status", "OK");
			response.put("message", "Operation completed");
			response.put("data", new JSONArray(products));

			return Response.status(Status.OK).entity(response.toString()).build();
		} catch(Exception e) {
			JSONObject response = new JSONObject();

			response.put("status", "ERROR");
			response.put("message", "Operation failed");
			response.put("data", e.toString());
	
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
				response.toString()
			).build();
		}	
	}


	@PATCH
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/name")
	public Response setName(@PathParam("id") Integer id, String name) {
		if(name.isEmpty()) {
			JSONObject response = new JSONObject();

			response.put("status", "ERROR");
			response.put("message", "Operation failed");
	
			return Response.status(Status.BAD_REQUEST).entity(
				response.toString()
			).build();
		}

		try {
			controller.setName(id, name);

			JSONObject response = new JSONObject();

			response.put("status", "OK");
			response.put("message", "Operation completed");
			
			return Response.status(Status.OK).entity(response.toString()).build();
		} catch(Exception e) {
			JSONObject response = new JSONObject();

			response.put("status", "ERROR");
			response.put("message", "Operation failed");
			response.put("error", e.toString());

			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
				response.toString()
			).build();
		}
	}

}