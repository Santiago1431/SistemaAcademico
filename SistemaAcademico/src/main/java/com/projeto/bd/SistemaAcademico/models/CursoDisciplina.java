package com.projeto.bd.SistemaAcademico.models;


import com.projeto.bd.SistemaAcademico.models.Embeddable.CursoDisciplinaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Curso_Disciplina")
public class CursoDisciplina {

    // Mapeia a chave composta definida na classe CursoDisciplinaId
    @EmbeddedId
    private CursoDisciplinaId id;

    // Mapeamento da FK id_curso
    @ManyToOne
    @JoinColumn(name = "id_curso", insertable = false, updatable = false)
    private Curso curso;

    // Mapeamento da FK id_disciplina
    @ManyToOne
    @JoinColumn(name = "id_disciplina", insertable = false, updatable = false)
    private Disciplina disciplina;
}
