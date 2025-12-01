package com.projeto.bd.SistemaAcademico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Aplicação principal Spring Boot do Sistema Acadêmico.
 *
 * Este projeto faz parte de um trabalho prático de Banco de Dados:
 * - expõe uma API REST conectada ao banco relacional;
 * - demonstra consultas SQL complexas (JOINs, agregações, subconsultas etc.);
 * - é consumida por uma página HTML/JS simples.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.projeto.bd.SistemaAcademico")
public class SistemaAcademicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaAcademicoApplication.class, args);
	}

}
