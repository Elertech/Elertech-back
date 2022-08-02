package com.grupo1.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_item")
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "valorTotal", nullable = false)
    private double valorTotal;

    @OneToOne
    @JsonIgnoreProperties(value = "item")
    private Produto produto;

    @ManyToOne
    @JsonIgnoreProperties(value = "item")
    private Carrinho carrinho;

    public ItemCarrinho(Long id, int quantidade, double valorTotal, Produto produto, Carrinho carrinho) {
        this.id = id;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.produto = produto;
        this.carrinho = carrinho;
    }

    public ItemCarrinho() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

}