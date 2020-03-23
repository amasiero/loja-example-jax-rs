package com.andreymasiero.loja;

import javax.ws.rs.core.MediaType;

import com.andreymasiero.loja.model.Carrinho;
import com.andreymasiero.loja.model.Produto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientePost {
	
	public static void main(String[] args) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/loja/carrinhos/");
		
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(3435l, "Mouse Gamer", 152.0, 1));
		carrinho.setRua("Avenida Lins de Vasconcelos 1222, 10 andar");
		carrinho.setCidade("São Paulo");
		
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, carrinho);
		
		if(response.getStatus() == 201) {
			System.out.println(response.getLocation());
		} else {
			System.err.println("HTTP Status: " + response.getStatus());
		}
	}

}

