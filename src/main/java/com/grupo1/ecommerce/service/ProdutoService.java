package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Produto;
import com.grupo1.ecommerce.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<Produto> cadastrarNovoProduto(Produto produto){
		  return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    public List<Produto> buscarProdutoPorCategoria(Long idCategoria){
        return produtoRepository.findByCategoria(idCategoria);
    }
}