package com.andreymasiero.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.andreymasiero.loja.model.Carrinho;
import com.andreymasiero.loja.model.Produto;

public class CarrinhoDAO {
	
	private static Map<Long, Carrinho> banco = new HashMap<Long, Carrinho>();
	private static AtomicLong contador = new AtomicLong(1);
	
	static {
		Produto notebook = new Produto(6924, "Notebook Gamer", 6000, 1);
		Produto monitor = new Produto(6669, "Monitor 29 UltraWide", 1000, 2);
		Carrinho carrinho = new Carrinho()
								.adiciona(notebook)
								.adiciona(monitor)
								.para("Avenida Lins de Vasconcelos 1222, 10 andar", "São Paulo")
								.setId(1l);
		banco.put(1l, carrinho);
	}
	
	public void adiciona(Carrinho carrinho) {
		long id = contador.incrementAndGet();
		carrinho.setId(id);
		banco.put(id, carrinho);
	}
	
	public Carrinho busca(Long id) {
		return banco.get(id);
	}
	
	public Carrinho remove(long id) {
		return banco.remove(id);
	}

}
