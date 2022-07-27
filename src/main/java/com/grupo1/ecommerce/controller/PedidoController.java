package com.grupo1.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo1.ecommerce.model.Pedido;
import com.grupo1.ecommerce.service.PedidoService;

@RestController
@RequestMapping(value = "/pedido")
@CrossOrigin("*")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable Long id){
        return pedidoService.getById(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Pedido> getAllByUsuario(@PathVariable Long idUsuario){
        return pedidoService.pedidosDoUsuario(idUsuario);
    }

    @PostMapping("/fecharpedido/{idUsuario}/entrega/{idEndereco}/pagamento/{idCartao}")
    public ResponseEntity<Pedido> fecharPedido(@PathVariable Long idUsuario, @PathVariable Long idEndereco, @PathVariable Long idCartao){
        return pedidoService.fazerPedido(idUsuario, idEndereco, idCartao);
    }
}