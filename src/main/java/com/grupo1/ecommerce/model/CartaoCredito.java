package com.grupo1.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tb_cartao_credito", uniqueConstraints = {@UniqueConstraint(columnNames = {"numeroCartao"})})
public class CartaoCredito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 3, max = 50)
	private String apelido;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String nomeCartao;
	
	@NotNull
	@Size(min = 16, max = 19)
	private String numeroCartao;
	
	@NotNull
	@Size(min = 5, max = 8)
	private String dataValidade;
	
	@NotNull
	@Size(min = 3, max = 3)
	private String cvv;
	
	@ManyToOne
	@JsonIgnoreProperties("cartaoCredito")
	private Usuario usuario;

	
	public CartaoCredito(Long id, String apelido, String nomeCartao, String numeroCartao, String dataValidade, String cvv, Usuario usuario) {
		this.id = id;
		this.apelido = apelido;
		this.nomeCartao = nomeCartao;
		this.numeroCartao = numeroCartao;
		this.dataValidade = dataValidade;
		this.cvv = cvv;
		this.usuario = usuario;
	}
	
	public CartaoCredito() {
		
	}

	public Long getId() {
		return id;
}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	
	
}