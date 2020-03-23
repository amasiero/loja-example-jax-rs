package com.andreymasiero.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.andreymasiero.loja.dao.CarrinhoDAO;
import com.andreymasiero.loja.model.Carrinho;
import com.andreymasiero.loja.model.Produto;

@Path("/carrinhos")
public class CarrinhoResource {

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Carrinho busca(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adiciona(Carrinho carrinho) {
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create("/loja/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}
	
	@Path("/{id}/produto/{produtoId}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}
	
	@Path("/{id}/produto/{produtoId}/quantidade")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}
}
