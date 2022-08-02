package com.grupo1.ecommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_carrinho")
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quantidadeItem", nullable = false)
	private int quantidadeItem;

	@Column(name = "valorTotalItem", nullable = false)
	private double valorTotalItem;

	@OneToOne
	@JsonIgnoreProperties(value = "carrinho")
	private Usuario usuario;

	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties(value = "carrinho")
	private List<ItemCarrinho> itemCarrinho;

	public Carrinho(Long id, int quantidadeItem, double valorTotalItem, Usuario usuario, List<ItemCarrinho> itemCarrinho) {
		this.id = id;
		this.quantidadeItem = quantidadeItem;
		this.valorTotalItem = valorTotalItem;
		this.usuario = usuario;
		this.itemCarrinho = itemCarrinho;
	}

	public Carrinho() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidadeItem() {
		return quantidadeItem;
	}

	public void setQuantidadeItem(int quantidadeItem) {
		this.quantidadeItem = quantidadeItem;
	}

	public double getValorTotalItem() {
		return valorTotalItem;
	}

	public void setValorTotalItem(double valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemCarrinho> getItem() {
		return itemCarrinho;
	}

	public void setItem(List<ItemCarrinho> itemCarrinho) {
		this.itemCarrinho = itemCarrinho;
	}

}
