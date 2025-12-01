package com.projeto.bd.SistemaAcademico.Repositories;

import com.projeto.bd.SistemaAcademico.DTO.AlunoMatriculaMultiplaDTO;
import com.projeto.bd.SistemaAcademico.DTO.HistoricoDetalhadoDTO;
import com.projeto.bd.SistemaAcademico.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {


    // Consultas complexas ligadas ao domínio de Aluno
    // (JOINs múltiplos entre Aluno, MatriculaTurma, Turma, Disciplina, Professor)
    @Query(value = "SELECT new com.projeto.bd.SistemaAcademico.DTO.HistoricoDetalhadoDTO(" +
            "a.nome, d.nome, p.nome, mt.notaFinal, mt.situacao, t.periodo, mt.frequencia) " +
            "FROM Aluno a " +
            "JOIN a.matriculas mt " +
            "JOIN mt.turma t " +
            "JOIN t.disciplina d " +
            "JOIN t.professor p " +
            "WHERE a.id = :alunoId " +
            "ORDER BY t.periodo, d.nome")
    List<HistoricoDetalhadoDTO> buscarHistoricoDetalhado(@Param("alunoId") Long alunoId);

    // (Subconsulta com IN e agregação: COUNT + HAVING):
    // retorna apenas alunos que possuem mais de uma matrícula em turmas
    @Query(value = "SELECT new com.projeto.bd.SistemaAcademico.DTO.AlunoMatriculaMultiplaDTO(a.id, a.nome) " +
            "FROM Aluno a " +
            "WHERE a.id IN (" +
            "SELECT mt.aluno.id " +
            "FROM MatriculaTurma mt " +
            "GROUP BY mt.aluno.id " +
            "HAVING COUNT(mt.turma.id) > 1" +
            ") " +
            "ORDER BY a.nome")
    List<AlunoMatriculaMultiplaDTO> buscarAlunosComMultiplasMatriculas();





}
