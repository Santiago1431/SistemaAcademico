package com.projeto.bd.SistemaAcademico.Services;

import com.projeto.bd.SistemaAcademico.Repositories.DisciplinaRepository;
import com.projeto.bd.SistemaAcademico.models.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;



    // --- CONSULTAS COMPLEXAS ---

    public List<String> obterDisciplinasSemTurma() {
        return disciplinaRepository.buscarDisciplinasSemTurmaAberta();
    }

    public List<Disciplina> buscarPorNome(String termo) {
        return disciplinaRepository.buscarPorNomeParcial(termo);
    }

    // --- CRUD BÁSICO ---
    public List<Disciplina> buscarTodas() {
        return disciplinaRepository.findAll();
    }

    // Implementar salvar/deletar...

    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina deletar(Disciplina disciplina) {
         disciplinaRepository.delete(disciplina);
         return disciplina;
    }

    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.getById(id);
    }


}
