package com.grupo1.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Categoria;
import com.grupo1.ecommerce.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ResponseEntity<Categoria> deleteCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id).get();
        ResponseEntity resposta;
        if (categoria.getProduto().isEmpty()) {
            categoriaRepository.delete(categoria);
            resposta = ResponseEntity.status(HttpStatus.OK).build();
        } else{
            resposta = ResponseEntity.status(HttpStatus.CONFLICT).build(); 
        }

        return resposta;
    }
}