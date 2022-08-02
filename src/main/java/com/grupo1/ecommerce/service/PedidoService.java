package com.grupo1.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.model.CartaoCredito;
import com.grupo1.ecommerce.model.Endereco;
import com.grupo1.ecommerce.model.Item;
import com.grupo1.ecommerce.model.ItemPedido;
import com.grupo1.ecommerce.model.Pedido;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.CartaoCreditoRepository;
import com.grupo1.ecommerce.repository.EnderecoRepository;
import com.grupo1.ecommerce.repository.ItemPedidoRepository;
import com.grupo1.ecommerce.repository.ItemRepository;
import com.grupo1.ecommerce.repository.PedidoRepository;
import com.grupo1.ecommerce.repository.UsuarioRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CartaoCreditoRepository cartaoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido getById(Long id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.get();
    }

    public ResponseEntity<Pedido> fazerPedido(Long idUsuario, Long idEndereco, Long idCartao) {
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        Endereco endereco = enderecoRepository.findById(idEndereco).get();
        CartaoCredito cartao = cartaoRepository.findById(idCartao).get();

        Carrinho carrinho = usuario.getCarrinho();

        Pedido pedido = new Pedido();
        Pedido novoPedido = new Pedido();
        ResponseEntity <Pedido> resposta;
        
        if(!carrinho.getItem().isEmpty()) {
        	
            novoPedido = pedidoRepository.save(pedido);

            novoPedido.setEnderecoEntrega(endereco.getEndereco() + " - " + endereco.getCep());
            novoPedido.setFormaPagamento(cartao.getApelido() + " - final " + cartao.getNumeroCartao().substring(15, 19));
            novoPedido.setUsuario(usuario);

            for (Item item : carrinho.getItem()) {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setPedido(novoPedido);
                itemPedido.setProduto(item.getProduto());
                itemPedido.setQuantidade(item.getQuantidade());
                itemPedido.setValorTotal(item.getValorTotal());
                itemPedidoRepository.save(itemPedido);

                itemRepository.delete(item);

                novoPedido.setQuantidadeItens(novoPedido.getQuantidadeItens() + itemPedido.getQuantidade());
                novoPedido.setValorTotalPedido(novoPedido.getValorTotalPedido() + itemPedido.getValorTotal());
            }
            resposta = ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(novoPedido));
            
        }
        else {
        	System.out.println("Carrinho vazio");
        	resposta = ResponseEntity.status(HttpStatus.CONFLICT).build(); 
        	
        }
        
        return resposta;
    }



    // public ResponseEntity<Pedido> fazerPedido(Long idUsuario, Long idEndereco, Long idCartao) {
    //     Usuario usuario = usuarioRepository.findById(idUsuario).get();
    //     Endereco endereco = enderecoRepository.findById(idEndereco).get();
    //     CartaoCredito cartao = cartaoRepository.findById(idCartao).get();

    //     Carrinho carrinho = usuario.getCarrinho();
    //     Pedido novoPedido = new Pedido();
    //     Pedido pedido = pedidoRepository.save(novoPedido);

    //     pedido.setEnderecoEntrega(endereco.getEndereco() + " - " + endereco.getCep());
    //     pedido.setFormaPagamento(cartao.getApelido() + " - final " + cartao.getNumeroCartao().substring(15, 19));
    //     pedido.setUsuario(usuario);

    //     for (Item item : carrinho.getItem()) {
    //         item.setCarrinho(null);
    //         item.setPedido(pedido);
    //         pedido.setQuantidadeItens(pedido.getQuantidadeItens() + item.getQuantidade());
    //         pedido.setValorTotalPedido(pedido.getValorTotalPedido() + item.getValorTotal());
    //     }

    //     return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
    // }

    public List<Pedido> pedidosDoUsuario(Long idUsuario) {
        return pedidoRepository.findAllPedidoUsuario(idUsuario);
    }
}