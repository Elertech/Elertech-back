package com.grupo1.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.grupo1.ecommerce.model.Endereco;
import com.grupo1.ecommerce.repository.EnderecoRepository;
import com.grupo1.ecommerce.service.EnderecoService;

@RestController
@RequestMapping(value = "/endereco")
@CrossOrigin("*")
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
    private EnderecoService enderecoService;

	
	@GetMapping("buscar/user/{idUsuario}")
	public List<Endereco> findAll(@PathVariable Long idUsuario){
		return enderecoService.obterEnderecoUsuario(idUsuario);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> getById(@PathVariable Long id){
	    return enderecoRepository.findById(id)
	    		.map(resp -> ResponseEntity.ok(resp))
	            .orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/cadastrar/user/{idUsuario}")
	public ResponseEntity<Endereco> post(@RequestBody Endereco endereco, @PathVariable Long idUsuario){
	    return enderecoService.cadastrarNovoEndereco(endereco, idUsuario);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Endereco> put(@RequestBody Endereco endereco){
	    return ResponseEntity.ok(enderecoRepository.save(endereco));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		enderecoRepository.deleteById(id);
	}

}

