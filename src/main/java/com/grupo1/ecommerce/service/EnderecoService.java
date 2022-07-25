package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Endereco;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.EnderecoRepository;
import com.grupo1.ecommerce.repository.UsuarioRepository;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<Endereco> cadastrarNovoEndereco(Endereco endereco, Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        endereco.setUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(endereco));

    }

    public List<Endereco> obterEnderecoUsuario(Long idUsuario){
        return enderecoRepository.findAllEnderecoUsuario(idUsuario);
    }
}