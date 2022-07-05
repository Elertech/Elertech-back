package com.grupo1.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.repository.CarrinhoRepository;

@Service
public class CarrinhoService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	public ResponseEntity <List<Carrinho>> fazerPedido(List<Carrinho> carrinho){
		carrinho.forEach((item) -> {
			Optional<Carrinho> carrinhoPedido = carrinhoRepository.findById(item.getId());
			carrinhoPedido.get().setStatus("pedido");
			carrinhoRepository.save(item);
		});
		return ResponseEntity.ok(carrinho);
	}
}