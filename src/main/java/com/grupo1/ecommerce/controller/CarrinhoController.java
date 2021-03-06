package com.grupo1.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.repository.CarrinhoRepository;
import com.grupo1.ecommerce.service.CarrinhoService;

@RestController
@RequestMapping(value = "/carrinho")
@CrossOrigin("*")
public class CarrinhoController {
	
	@Autowired
	private CarrinhoRepository repository;
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	@GetMapping 
	public List<Carrinho> findAll(){
		List<Carrinho> lista = repository.findAll();
		return lista;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Carrinho> getById(@PathVariable Long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/{status}")
	public ResponseEntity<List<Carrinho>> GetByStatus(@PathVariable String status) {
		return ResponseEntity.ok(repository.findAllByStatusContainingIgnoreCase(status));
	}
	
	@PostMapping("/adicionar")
	public ResponseEntity<Carrinho> post(@RequestBody Carrinho carrinho){
		return  ResponseEntity.status(HttpStatus.CREATED).body(repository.save(carrinho));
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Carrinho> put(@RequestBody Carrinho carrinho){
		return ResponseEntity.ok(repository.save(carrinho));
	}
	
	@PutMapping("/pedido")
	public ResponseEntity <List<Carrinho>> fazerPedido(@RequestBody List<Carrinho> carrinho){
		return carrinhoService.fazerPedido(carrinho);
				
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}