package com.projeto.bd.SistemaAcademico.Services;

import com.projeto.bd.SistemaAcademico.Repositories.CursoRepository;
import com.projeto.bd.SistemaAcademico.models.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> buscarTodos() {
        return cursoRepository.findAll();
    }

    // Métodos CRUD (salvar, buscar por ID, deletar) seriam adicionados aqui
}
