package com.andreymasiero.loja;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClienteDelete {
	
	public static void main(String[] args) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/loja/carrinhos/1/produto/6924");
		
		ClientResponse response = resource.delete(ClientResponse.class);
		
		if(response.getStatus() == 200) {
			System.out.println("Sucesso");
		} else {
			System.err.println("HTTP Status: " + response.getStatus());
		}
	}

}
