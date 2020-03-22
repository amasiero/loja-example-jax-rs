package com.andreymasiero.loja.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

public class CarrinhoTest {
	
	@Test
	public void testeCarrinhoExistenteNoBanco() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		String conteudo = target.path("/carrinhos").request().get(String.class);
		Assert.assertTrue(conteudo.contains("Lins de Vasconcelos"));
	}

}
