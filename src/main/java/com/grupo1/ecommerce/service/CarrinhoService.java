package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.model.ItemCarrinho;
import com.grupo1.ecommerce.model.Produto;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.CarrinhoRepository;
import com.grupo1.ecommerce.repository.ItemCarrinhoRepository;
import com.grupo1.ecommerce.repository.ProdutoRepository;
import com.grupo1.ecommerce.repository.UsuarioRepository;

@Service
public class CarrinhoService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemCarrinhoRepository itemRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void criarCarrinho(Usuario usuario){
		Carrinho carrinho = new Carrinho();
		carrinho.setUsuario(usuario);
		carrinhoRepository.save(carrinho);
		usuario.setCarrinho(carrinho);
		usuarioRepository.save(usuario);
	}

	public ResponseEntity<Carrinho> adicionarItem(Long idProduto, Long idCarrinho, int quantidade){
		Produto produto = produtoRepository.findById(idProduto).get();
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho).get();
		List<ItemCarrinho> listaItemCarrinho = carrinho.getItem();

		if(listaItemCarrinho.isEmpty()){
			produtoNovo(produto, carrinho, quantidade);
		} else {
			boolean controleItemRepetido = false;
			ItemCarrinho itemRepetido = new ItemCarrinho();
			for(ItemCarrinho itemCarrinho : listaItemCarrinho){
				if(produto.getId() == itemCarrinho.getProduto().getId()){
					controleItemRepetido = true;
					itemRepetido = itemCarrinho;
				}
			}

			if(controleItemRepetido == true){
				produtoExistente(produto, itemRepetido, quantidade);
			} else {
				produtoNovo(produto, carrinho, quantidade);
			}
			controleItemRepetido = false;
		}
		calcularCarrinho(carrinho.getId());
		
		return ResponseEntity.ok(carrinhoRepository.save(carrinho));
	}

	public void produtoNovo(Produto produto, Carrinho carrinho, int quantidade){
		ItemCarrinho itemCarrinho = new ItemCarrinho();
		itemCarrinho.setProduto(produto);
		itemCarrinho.setCarrinho(carrinho);
		itemCarrinho.setQuantidade(quantidade);
		itemCarrinho.setValorTotal(produto.getPreco() * quantidade);
		itemRepository.save(itemCarrinho);
		atualizarEstoqueAoAdicionar(produto, quantidade);
	}

	public void produtoExistente(Produto produto, ItemCarrinho itemRepetido, int quantidade){
		itemRepetido.setQuantidade(itemRepetido.getQuantidade() + quantidade);
		itemRepetido.setValorTotal(itemRepetido.getProduto().getPreco() * itemRepetido.getQuantidade());
		itemRepository.save(itemRepetido);
		atualizarEstoqueAoAdicionar(produto, quantidade);
	}	

	public void calcularCarrinho(Long idCarrinho){
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho).get();
		int quantidadeItens = 0;
		double valorTotalItens = 0;
		if(carrinho.getItem().isEmpty()){
			carrinho.setValorTotalItem(0);
			carrinho.setQuantidadeItem(0);
		} else {
			for(ItemCarrinho itemCarrinho : carrinho.getItem()){
				quantidadeItens = quantidadeItens + itemCarrinho.getQuantidade();
				valorTotalItens = valorTotalItens + (itemCarrinho.getProduto().getPreco() * itemCarrinho.getQuantidade());
			}
			carrinho.setQuantidadeItem(quantidadeItens);
			carrinho.setValorTotalItem(valorTotalItens);
		}
		carrinhoRepository.save(carrinho);
	}

	public void deletarItemDoCarrinho(Long idCarrinho, Long idItem){
		ItemCarrinho itemCarrinho = itemRepository.findById(idItem).get();
		atualizarEstoqueAoExcluir(itemCarrinho.getProduto(), itemCarrinho);
		itemRepository.delete(itemCarrinho);
		calcularCarrinho(idCarrinho);
	}

	public void limparCarrinho(Long idCarrinho){
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho).get();
		List<ItemCarrinho> listaItemCarrinho = carrinho.getItem();
		for(ItemCarrinho itemCarrinho : listaItemCarrinho){
			atualizarEstoqueAoExcluir(itemCarrinho.getProduto(), itemCarrinho);
		}
		itemRepository.deleteAll(carrinho.getItem());
		calcularCarrinho(idCarrinho);
	}

	public void atualizarEstoqueAoAdicionar(Produto produto, int quantidade){
		produto.setEstoque(produto.getEstoque() - quantidade);
		produtoRepository.save(produto);
	}

	public void atualizarEstoqueAoExcluir(Produto produto, ItemCarrinho itemCarrinho){
		produto.setEstoque(produto.getEstoque() + itemCarrinho.getQuantidade());
		produtoRepository.save(produto);
	}
	
}