package com.projeto.bd.SistemaAcademico.Controllers;


import com.projeto.bd.SistemaAcademico.DTO.ProfessorRankingDTO;
import com.projeto.bd.SistemaAcademico.Repositories.DisciplinaRepository;
import com.projeto.bd.SistemaAcademico.Repositories.ProfessorRepository;
import com.projeto.bd.SistemaAcademico.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable("id") Long id) {
        return ResponseEntity.ok(professorRepository.getProfessorById(id));
    }

    @GetMapping
    public ResponseEntity<List<Professor>> getProfessores() {
        return ResponseEntity.ok(professorRepository.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Professor>  addProfessor(@RequestBody Professor professor) {
        return ResponseEntity.ok(professorRepository.save(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable("id") Long id, @RequestBody Professor professor) {
        professor.setId(id);
        Professor atualizado = professorRepository.save(professor);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<ProfessorRankingDTO>> buscarRanking(
            @RequestParam(name = "ano", defaultValue = "2025") String ano) {

        // Chamada à @Query: COUNT, GROUP BY, ORDER BY (para Top N)
        List<ProfessorRankingDTO> ranking = professorRepository.buscarTopProfessoresPorAno(ano);

        if (ranking.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ranking);
    }
}
