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
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito = cartao;
        cartaoCredito.setUsuario(usuario);

       return ResponseEntity.status(HttpStatus.OK).body(cartaoRepository.save(cartaoCredito));
    }

    public List<CartaoCredito> obterCartaoUsuario(Long idUsuario){
        return cartaoRepository.findAllCartaoUsuario(idUsuario);
    }
    
}
