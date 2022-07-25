package com.grupo1.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grupo1.ecommerce.model.CartaoCredito;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long>{

	@Query(value = "SELECT * FROM tb_cartao_credito WHERE usuario_id = ?1", nativeQuery = true)
	public List<CartaoCredito> findAllCartaoUsuario(Long idUsuario);

}
