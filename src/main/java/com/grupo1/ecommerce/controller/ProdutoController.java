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

import com.grupo1.ecommerce.model.Produto;
import com.grupo1.ecommerce.repository.ProdutoRepository;
import com.grupo1.ecommerce.service.ProdutoService;

@RestController
@RequestMapping(value = "/produto")
@CrossOrigin("*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping 
	public List<Produto> findAll(){
		List<Produto> lista = repository.findAll();
		return lista;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/pesquisar/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}

	@GetMapping("/pesquisar/categoria/{idCategoria}")
	public List<Produto> getByCategoria(@PathVariable Long idCategoria) {
		return produtoService.buscarProdutoPorCategoria(idCategoria);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Produto> post(@RequestBody Produto produto){
		return produtoService.cadastrarNovoProduto(produto);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Produto> put(@RequestBody Produto produto){
		return ResponseEntity.ok(repository.save(produto));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}

