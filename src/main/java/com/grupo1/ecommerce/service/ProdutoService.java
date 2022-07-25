package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Categoria;
import com.grupo1.ecommerce.model.Produto;
import com.grupo1.ecommerce.repository.CategoriaRepository;
import com.grupo1.ecommerce.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ResponseEntity<Produto> cadastrarNovoProduto(Produto produto, Long idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria).get();
        produto.setCategoria(categoria);
	    return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    public ResponseEntity<Produto> atualizarProduto(Produto produto, Long idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria).get();
        produto.setCategoria(categoria);
        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    public List<Produto> buscarProdutoPorCategoria(Long idCategoria){
        return produtoRepository.findByCategoria(idCategoria);
    }
}