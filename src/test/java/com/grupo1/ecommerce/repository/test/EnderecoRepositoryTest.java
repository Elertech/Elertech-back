package com.grupo1.ecommerce.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.stereotype.Repository;

import com.grupo1.ecommerce.model.Endereco;
import com.grupo1.ecommerce.repository.EnderecoRepository;

@Repository
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnderecoRepositoryTest {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@BeforeAll
	void start() {
		enderecoRepository.deleteAll();
		enderecoRepository.save(new Endereco(0L, "Avenida 1", "123456789",  true));
		enderecoRepository.save(new Endereco(0L, "Avenida 2", "123456789",  false));
		enderecoRepository.save(new Endereco(0L, "Avenida 3", "123456789",  false));
		enderecoRepository.save(new Endereco(0L, "Avenida 4", "123456789",  false));

	}
	
	@Test
	@DisplayName("Retorna 1 endereço")
	public void deveRetornarUmUsuario() {
		Optional<Endereco> endereco = enderecoRepository.findByStatus(true);
		assertTrue(endereco.get().getStatus().equals(true));
	}
	
	@Test
	@DisplayName("Retorna 3 endereços")
	public void deveRetornarTresEnderecos() {
		List<Endereco> listaDeEnderecos = enderecoRepository.findAll();
		
		assertEquals(3, listaDeEnderecos.size());
		assertTrue(listaDeEnderecos.get(0).getStatus().equals(true));																			
		assertTrue(listaDeEnderecos.get(1).getStatus().equals(false));

	}
}

