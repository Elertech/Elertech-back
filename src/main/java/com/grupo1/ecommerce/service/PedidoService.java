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
<<<<<<< HEAD
import com.grupo1.ecommerce.model.Item;
=======
import com.grupo1.ecommerce.model.ItemCarrinho;
>>>>>>> main
import com.grupo1.ecommerce.model.ItemPedido;
import com.grupo1.ecommerce.model.Pedido;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.CartaoCreditoRepository;
import com.grupo1.ecommerce.repository.EnderecoRepository;
import com.grupo1.ecommerce.repository.ItemPedidoRepository;
<<<<<<< HEAD
import com.grupo1.ecommerce.repository.ItemRepository;
=======
import com.grupo1.ecommerce.repository.ItemCarrinhoRepository;
>>>>>>> main
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
    private ItemCarrinhoRepository itemCarrinhoRepository;

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
        Pedido novoPedido = pedidoRepository.save(pedido);

        novoPedido.setEnderecoEntrega(endereco.getEndereco() + " - " + endereco.getCep());
        novoPedido.setFormaPagamento(cartao.getApelido() + " - final " + cartao.getNumeroCartao().substring(15, 19));
        novoPedido.setUsuario(usuario);

        for (ItemCarrinho itemCarrinho : carrinho.getItem()) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(novoPedido);
            itemPedido.setProduto(itemCarrinho.getProduto());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setValorTotal(itemCarrinho.getValorTotal());
            itemPedidoRepository.save(itemPedido);

            itemCarrinhoRepository.delete(itemCarrinho);

            novoPedido.setQuantidadeItens(novoPedido.getQuantidadeItens() + itemPedido.getQuantidade());
            novoPedido.setValorTotalPedido(novoPedido.getValorTotalPedido() + itemPedido.getValorTotal());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(novoPedido));
    }

    public List<Pedido> pedidosDoUsuario(Long idUsuario) {
        return pedidoRepository.findAllPedidoUsuario(idUsuario);
    }
}