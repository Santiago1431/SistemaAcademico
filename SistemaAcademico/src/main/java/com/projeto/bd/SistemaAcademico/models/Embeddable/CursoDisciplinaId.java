package com.projeto.bd.SistemaAcademico.models.Embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class CursoDisciplinaId implements Serializable {


    @Column(name = "id_curso")
    private Long cursoId;


    @Column(name = "id_disciplina")
    private Long disciplinaId;



    // --- Métodos essenciais para Chave Composta ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoDisciplinaId that = (CursoDisciplinaId) o;
        return Objects.equals(cursoId, that.cursoId) &&
                Objects.equals(disciplinaId, that.disciplinaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cursoId, disciplinaId);
    }
}
