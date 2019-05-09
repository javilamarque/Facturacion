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

import com.itedes.facturacion.controller.PurchaseController;
import com.itedes.facturacion.dto.purchase.PurchaseRequestCreationDto;
import com.itedes.facturacion.dto.purchase.PurchaseResponseDto;

import java.util.ArrayList;

import org.json.*;

@Path("/purchase")
public class PurchaseResource {
	PurchaseController controller = new PurchaseController();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String p_purchase) {
		PurchaseRequestCreationDto productDto = new PurchaseRequestCreationDto (
			new JSONObject(p_purchase)
		);
		
		try {
			PurchaseResponseDto purchaseResponseDto = controller.create(productDto);

			JSONObject response = new JSONObject();

			response.put("status", "OK");
			response.put("message", "Operation completed");
			response.put("data", new JSONObject(purchaseResponseDto));

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
    public Response search(
		@DefaultValue("--") @QueryParam("from") String from,
		@DefaultValue("--") @QueryParam("to") String to,
		@DefaultValue("%") @QueryParam("product") String productName 
	) {
		try {
			ArrayList<PurchaseResponseDto> purchases = controller.search(from, to, productName);

			JSONObject response = new JSONObject();

			response.put("status", "OK");
			response.put("message", "Operation completed");
			response.put("data", new JSONArray(purchases));

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

}