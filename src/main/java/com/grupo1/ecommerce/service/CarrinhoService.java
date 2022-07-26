package com.grupo1.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.ecommerce.model.Carrinho;
import com.grupo1.ecommerce.model.Item;
import com.grupo1.ecommerce.model.Produto;
import com.grupo1.ecommerce.model.Usuario;
import com.grupo1.ecommerce.repository.CarrinhoRepository;
import com.grupo1.ecommerce.repository.ItemRepository;
import com.grupo1.ecommerce.repository.ProdutoRepository;
import com.grupo1.ecommerce.repository.UsuarioRepository;

@Service
public class CarrinhoService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemRepository itemRepository;

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
		List<Item> listaItem = carrinho.getItem();

		if(listaItem.isEmpty()){
			produtoNovo(produto, carrinho, quantidade);
		} else {
			boolean controleItemRepetido = false;
			Item itemRepetido = new Item();
			for(Item item : listaItem){
				if(produto.getId() == item.getProduto().getId()){
					controleItemRepetido = true;
					itemRepetido = item;
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
		Item item = new Item();
		item.setProduto(produto);
		item.setCarrinho(carrinho);
		item.setQuantidade(quantidade);
		item.setValorTotal(produto.getPreco() * quantidade);
		itemRepository.save(item);
		atualizarEstoqueAoAdicionar(produto, item);
	}

	public void produtoExistente(Produto produto, Item itemRepetido, int quantidade){
		itemRepetido.setQuantidade(itemRepetido.getQuantidade() + quantidade);
		itemRepetido.setValorTotal(itemRepetido.getProduto().getPreco() * itemRepetido.getQuantidade());
		itemRepository.save(itemRepetido);
		atualizarEstoqueAoAdicionar(produto, itemRepetido);
	}	

	public void calcularCarrinho(Long idCarrinho){
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho).get();
		int quantidadeItens = 0;
		double valorTotalItens = 0;
		if(carrinho.getItem().isEmpty()){
			carrinho.setValorTotalItem(0);
			carrinho.setQuantidadeItem(0);
		} else {
			for(Item item : carrinho.getItem()){
				quantidadeItens = quantidadeItens + item.getQuantidade();
				valorTotalItens = valorTotalItens + (item.getProduto().getPreco() * item.getQuantidade());
			}
			carrinho.setQuantidadeItem(quantidadeItens);
			carrinho.setValorTotalItem(valorTotalItens);
		}
		carrinhoRepository.save(carrinho);
	}

	public void deletarItemDoCarrinho(Long idCarrinho, Long idItem){
		Item item = itemRepository.findById(idItem).get();
		atualizarEstoqueAoExcluir(item.getProduto(), item);
		itemRepository.delete(item);
		calcularCarrinho(idCarrinho);
	}

	public void limparCarrinho(Long idCarrinho){
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho).get();
		List<Item> listItem = carrinho.getItem();
		for(Item item : listItem){
			atualizarEstoqueAoExcluir(item.getProduto(), item);
		}
		itemRepository.deleteAll(carrinho.getItem());
		calcularCarrinho(idCarrinho);
	}

	public void atualizarEstoqueAoAdicionar(Produto produto, Item item){
		produto.setEstoque(produto.getEstoque() - item.getQuantidade());
		produtoRepository.save(produto);
	}

	public void atualizarEstoqueAoExcluir(Produto produto, Item item){
		produto.setEstoque(produto.getEstoque() + item.getQuantidade());
		produtoRepository.save(produto);
	}
	
}