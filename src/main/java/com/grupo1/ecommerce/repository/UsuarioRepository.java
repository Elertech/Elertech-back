package com.grupo1.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo1.ecommerce.model.Usuario;

@Repository
public interface  UsuarioRepository  extends JpaRepository<Usuario, Long> {

}
