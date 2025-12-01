package com.projeto.bd.SistemaAcademico.Repositories;

import com.projeto.bd.SistemaAcademico.models.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {


    // (Subconsulta com NOT IN): disciplinas que nunca tiveram turma associada

    @Query(value = "SELECT d.nome " +
            "FROM Disciplina d " +
            "WHERE d.id NOT IN (" +
            "SELECT t.disciplina.id " +
            "FROM Turma t" +
            ")")
    List<String> buscarDisciplinasSemTurmaAberta();

    // (Comparação de strings - LIKE): busca por nome parcial para apoiar filtro pela interface
    @Query(value = "SELECT d " +
            "FROM Disciplina d " +
            "WHERE d.nome LIKE CONCAT('%', :termo, '%') " +
            "ORDER BY d.nome")
    List<Disciplina> buscarPorNomeParcial(@Param("termo") String termo);

    // Método auxiliar de busca por ID (usado no serviço)
    Disciplina getById(Long id);

}
