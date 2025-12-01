package com.projeto.bd.SistemaAcademico.Controllers;

import com.projeto.bd.SistemaAcademico.Services.CursoService;
import com.projeto.bd.SistemaAcademico.models.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    /**
     * Endpoint para listar todos os cursos (Busca Básica CRUD).
     * URL: GET http://localhost:8080/api/cursos
     */
    @GetMapping
    public ResponseEntity<List<Curso>> buscarTodos() {
        return ResponseEntity.ok(cursoService.buscarTodos());
    }
}
