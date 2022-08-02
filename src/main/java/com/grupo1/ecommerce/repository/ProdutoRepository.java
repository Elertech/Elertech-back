package com.grupo1.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo1.ecommerce.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	@Query(value = "SELECT * FROM tb_produto ORDER BY id", nativeQuery = true)
	public List<Produto> findAllProduto();
	
	@Query(value = "SELECT * FROM tb_produto WHERE categoria_id = ?1", nativeQuery = true)
	public List<Produto> findByCategoria(Long idCategoria);

}
