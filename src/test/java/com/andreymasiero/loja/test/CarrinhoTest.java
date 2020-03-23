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
import com.thoughtworks.xstream.XStream;

public class CarrinhoTest {
	
	@Test
	public void testeCarrinhoExistenteNoBanco() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Assert.assertTrue(conteudo.contains("Lins de Vasconcelos"));
	}
	
	@Test
	public void testeCarrinhoCriadoViaPost() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(3456l, "Mouse Gamer", 120, 1));
		carrinho.setRua("Avenida Lins de Vasconcelos 1222, 10 andar");
		carrinho.setCidade("São Paulo");
		
		String xml = carrinho.toXML();
		
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
		
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		Assert.assertTrue(conteudo.contains("Mouse"));
	}
	
	@Test
	public void testeRemoverNotebookCarrinho() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		
		Response response = target.path("/carrinhos/1/produto/6924").request().delete();
		Assert.assertEquals(200, response.getStatus());
		
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Assert.assertFalse(conteudo.contains("Notebook"));
	}
	
	@Test
	public void testeAtualizacaoQtdeDeMonitoresCarrinho() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/loja");
		
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		
		Produto produto = (Produto) carrinho.getProdutos()
				.stream().filter(p -> p.getId() == 6669l)
				.toArray()[0];
		produto.setQuantidade(1);
		
		String xml = new XStream().toXML(produto);
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response response = target.path("/carrinhos/1/produto/6669/quantidade")
				.request().put(entity);
		Assert.assertEquals(200, response.getStatus());
		
		conteudo = target.path("/carrinhos/1").request().get(String.class);
		Assert.assertTrue(conteudo.contains("<quantidade>1</quantidade>"));
	}
}
