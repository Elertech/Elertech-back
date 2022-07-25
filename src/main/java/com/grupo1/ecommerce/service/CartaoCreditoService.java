package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.CartaoCredito;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.CartaoCreditoRepository;
import com.grupo1.ecommerce.repository.UsuarioRepository;

@Service
public class CartaoCreditoService {
    
    @Autowired
    private CartaoCreditoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<CartaoCredito> cadastrarNovoCartao(CartaoCredito cartao, Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        cartao.setUsuario(usuario);
        cartao.setNomeCartao(cartao.getNomeCartao().toUpperCase());
       return ResponseEntity.status(HttpStatus.CREATED).body(cartaoRepository.save(cartao));
    }

    public List<CartaoCredito> obterCartaoUsuario(Long idUsuario){
        return cartaoRepository.findAllCartaoUsuario(idUsuario);
    }
    
}
