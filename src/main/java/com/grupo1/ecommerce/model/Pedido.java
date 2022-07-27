package com.grupo1.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidadeItens", nullable = true)
    private int quantidadeItens;

    @Column(name = "valorTotalPedido", nullable = true)
    private double valorTotalPedido;

    @Column(name = "enderecoEntrega", nullable = true)
    private String enderecoEntrega;

    @Column(name = "formaPagamento", nullable = true)
    private String formaPagamento;

    @UpdateTimestamp
    @Column(name = "dataPedido", nullable = true)
	private LocalDateTime dataPedido;

    @ManyToOne
    @JsonIgnoreProperties(value = "pedido")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "pedido")
    private List<ItemPedido> itemPedido;

    public Pedido(Long id, int quantidadeItens, double valorTotalPedido, String enderecoEntrega, String formaPagamento,
    LocalDateTime dataPedido, Usuario usuario, List<ItemPedido> itemPedido) {
        this.id = id;
        this.quantidadeItens = quantidadeItens;
        this.valorTotalPedido = valorTotalPedido;
        this.enderecoEntrega = enderecoEntrega;
        this.formaPagamento = formaPagamento;
        this.dataPedido = dataPedido;
        this.usuario = usuario;
        this.itemPedido = itemPedido;
    }

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(int quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public double getValorTotalPedido() {
        return valorTotalPedido;
    }

    public void setValorTotalPedido(double valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPedido> getItem() {
        return itemPedido;
    }

    public void setItem(List<ItemPedido> itemPedido) {
        this.itemPedido = itemPedido;
    }

    

    
}