package com.grupo1.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grupo1.ecommerce.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
    @Query(value = "SELECT * FROM tb_pedido WHERE usuario_id = ?1", nativeQuery = true)
    public List<Pedido> findAllPedidoUsuario(Long idUsuario);
}