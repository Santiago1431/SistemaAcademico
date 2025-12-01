package com.projeto.bd.SistemaAcademico.Repositories;

import com.projeto.bd.SistemaAcademico.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
