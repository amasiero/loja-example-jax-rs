package com.andreymasiero.loja;

import javax.ws.rs.core.MediaType;

import com.andreymasiero.loja.model.Produto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientePut {
	
	public static void main(String[] args) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/loja/carrinhos/1/produto/6669/quantidade");
		
		Produto produto = new Produto(6669l, "Monitor 29 UltraWide", 1000, 1);
		
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, produto);
		
		if(response.getStatus() == 200) {
			System.out.println("Sucesso");
		} else {
			System.err.println("HTTP Status: " + response.getStatus());
		}
	}
}
