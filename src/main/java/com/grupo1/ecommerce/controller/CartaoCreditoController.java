package com.grupo1.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ecommerce.model.CartaoCredito;
import com.grupo1.ecommerce.repository.CartaoCreditoRepository;
import com.grupo1.ecommerce.service.CartaoCreditoService;

@RestController
@RequestMapping(value = "/cartaocredito")
@CrossOrigin("*")
public class CartaoCreditoController {
	
	@Autowired
	private CartaoCreditoRepository cartaoRepository;

	@Autowired
	private CartaoCreditoService cartaoService;
	
	@GetMapping("/buscar/user/{idUsuario}")
	public List<CartaoCredito> getAll(@PathVariable Long idUsuario){
		return cartaoService.obterCartaoUsuario(idUsuario);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CartaoCredito> getById(@PathVariable Long id){
		return cartaoRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/cadastrar/user/{idUsuario}")
	public ResponseEntity<CartaoCredito> post(@RequestBody CartaoCredito cartao, @PathVariable Long idUsuario){
		return cartaoService.cadastrarNovoCartao(cartao, idUsuario);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		cartaoRepository.deleteById(id);
	}
}