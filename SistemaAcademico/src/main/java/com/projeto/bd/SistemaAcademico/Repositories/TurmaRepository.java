package com.projeto.bd.SistemaAcademico.Repositories;

import com.projeto.bd.SistemaAcademico.DTO.MediaTurmaDTO;
import com.projeto.bd.SistemaAcademico.models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    // Consulta agregada: média geral e contagem de alunos por turma
    @Query(value = "SELECT new com.projeto.bd.SistemaAcademico.DTO.MediaTurmaDTO(" +
            "t.id, d.nome, AVG(mt.notaFinal), COUNT(mt.aluno.id)) " +
            "FROM Turma t " +
            "JOIN t.disciplina d " +
            "JOIN t.matriculas mt " +
            "GROUP BY t.id, d.nome " +
            "ORDER BY t.periodo DESC, AVG(mt.notaFinal) DESC")
    List<MediaTurmaDTO> calcularMediaGeralPorTurma();
}
