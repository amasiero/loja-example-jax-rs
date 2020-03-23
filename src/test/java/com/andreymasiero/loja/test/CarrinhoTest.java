package com.andreymasiero.loja.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.andreymasiero.loja.model.Carrinho;
import com.andreymasiero.loja.model.Produto;

public class CarrinhoTest {
	
	@Test
	public void testeCarrinhoExistenteNoBanco() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals("Avenida Lins de Vasconcelos 1222, 10 andar", carrinho.getRua());
	}
	
	@Test
	public void testeCarrinhoCriadoViaPost() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(3456l, "Mouse Gamer", 120, 1));
		carrinho.setRua("Avenida Lins de Vasconcelos 1222, 10 andar");
		carrinho.setCidade("São Paulo");
		
		Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
		
		String location = response.getHeaderString("Location");
		Carrinho carrinhoCarregado = client.target(location).request().get(Carrinho.class);
		Assert.assertTrue(carrinhoCarregado.getProdutos().get(0).getNome().contains("Mouse"));
	}
	
	@Test
	public void testeRemoverNotebookCarrinho() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		
		Response response = target.path("/carrinhos/1/produto/6924").request().delete();
		Assert.assertEquals(200, response.getStatus());
		
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals(1, carrinho.getProdutos().size());
	}
	
	@Test
	public void testeAtualizacaoQtdeDeMonitoresCarrinho() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		
		Produto produto = (Produto) carrinho.getProdutos()
				.stream().filter(p -> p.getId() == 6669l)
				.toArray()[0];
		produto.setQuantidade(1);
		
		Entity<Produto> entity = Entity.entity(produto, MediaType.APPLICATION_JSON);
		
		Response response = target.path("/carrinhos/1/produto/6669/quantidade")
				.request().put(entity);
		Assert.assertEquals(200, response.getStatus());
		
		carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		Assert.assertEquals(1, carrinho.getProdutos().get(0).getQuantidade());
	}
}
