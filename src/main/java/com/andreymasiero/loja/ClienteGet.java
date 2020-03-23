package com.andreymasiero.loja;

import javax.ws.rs.core.MediaType;

import com.andreymasiero.loja.model.Carrinho;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClienteGet {
	
	public static void main(String[] args) {
		
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/loja/carrinhos/1");
		
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			Carrinho carrinho = response.getEntity(Carrinho.class);
			carrinho.getProdutos().forEach(produto -> System.out.println(produto.getNome()));
		} else {
			System.err.println("HTTP Status: " + response.getStatus());
		}
		
	}

}
