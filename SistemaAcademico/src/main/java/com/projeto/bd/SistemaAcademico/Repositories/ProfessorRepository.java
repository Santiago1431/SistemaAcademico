package com.projeto.bd.SistemaAcademico.Repositories;

import com.projeto.bd.SistemaAcademico.DTO.ProfessorRankingDTO;
import com.projeto.bd.SistemaAcademico.models.Professor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {


    // As consultas complexas do Professor:
    // (Agregada com Ranking/LIMIT): Top Professores
    // O uso de 'findTop1By' ou 'findTop3By...' permite ao Spring gerar o LIMIT.
    //  o método List<T> com Top 1 para simplificar o DTO.
    @Query(value = "SELECT new com.projeto.bd.SistemaAcademico.DTO.ProfessorRankingDTO(" +
            "p.nome, COUNT(t.id)) " +
            "FROM Professor p " +
            "JOIN p.turmas t " +
            "WHERE t.periodo LIKE CONCAT(:ano, '%') " + // Filtra por ano (e.g., '2025%')
            "GROUP BY p.nome " +
            "ORDER BY COUNT(t.id) DESC")
    List<ProfessorRankingDTO> buscarTopProfessoresPorAno(@Param("ano") String ano);

    @Nullable
    Professor getProfessorById(Long id);
}
