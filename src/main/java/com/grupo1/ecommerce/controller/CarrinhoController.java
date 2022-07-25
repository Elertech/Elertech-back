package com.grupo1.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.repository.CarrinhoRepository;
import com.grupo1.ecommerce.service.CarrinhoService;

@RestController
@RequestMapping(value = "/carrinho")
@CrossOrigin(origins = "*")
public class CarrinhoController {
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	@GetMapping 
	public List<Carrinho> findAll(){
		List<Carrinho> lista = carrinhoRepository.findAll();
		return lista;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Carrinho> getById(@PathVariable Long id){
		carrinhoService.calcularCarrinho(id);
		return carrinhoRepository.findById(id)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/{idCarrinho}/adicionar/produto/{idProduto}/quantidade/{quantidade}")
	public ResponseEntity<Carrinho> getById(@PathVariable Long idCarrinho, @PathVariable Long idProduto, @PathVariable int quantidade){
		return carrinhoService.adicionarItem(idProduto, idCarrinho, quantidade);
	}

	@DeleteMapping("/{idCarrinho}/deleteitem/{idItem}")
	public void delete(@PathVariable Long idCarrinho, @PathVariable Long idItem) {
		carrinhoService.deletarItemDoCarrinho(idCarrinho, idItem);
	}

	@DeleteMapping("/limpar/{idCarrinho}")
	public void clear(@PathVariable Long idCarrinho) {
		carrinhoService.limparCarrinho(idCarrinho);
	}

}