package com.projeto.bd.SistemaAcademico.models.Embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MatriculaTurmaId implements Serializable {

    @Column(name = "id_aluno")
    private Long alunoId;

    @Column(name = "id_turma")
    private Long turmaId;



    // --- Métodos essenciais para Chave Composta ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatriculaTurmaId that = (MatriculaTurmaId) o;
        return Objects.equals(alunoId, that.alunoId) &&
                Objects.equals(turmaId, that.turmaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alunoId, turmaId);
    }
}