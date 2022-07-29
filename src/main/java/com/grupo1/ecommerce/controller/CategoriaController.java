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

import com.grupo1.ecommerce.model.Categoria;
import com.grupo1.ecommerce.repository.CategoriaRepository;
import com.grupo1.ecommerce.service.CategoriaService;

@RestController
@RequestMapping(value = "/categoria")
@CrossOrigin("*")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping 
	public List<Categoria> findAll(){
		List<Categoria> lista = repository.findAll();
		return lista;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("filtrar/{nomeCategoria}")
	public ResponseEntity<List<Categoria>> getByCategoria(@PathVariable String nomeCategoria){
		return ResponseEntity.ok(repository.findByNomeCategoriaContainingIgnoreCase(nomeCategoria));
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Categoria> post(@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria));
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Categoria> put(@RequestBody Categoria categoria){
		return ResponseEntity.ok(repository.save(categoria));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> delete(@PathVariable Long id) {
		return categoriaService.deleteCategoria(id);
	}

}
