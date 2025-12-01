package com.projeto.bd.SistemaAcademico.models.Embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CoordenacaoId implements Serializable {

    // PK parte 1: ID do Curso
    @Column(name = "id_curso")
    private Long cursoId;

    // PK parte 2: Data de Início
    @Column(name = "inicio")
    private LocalDate dataInicio;


    // --- Métodos essenciais para Chave Composta ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordenacaoId that = (CoordenacaoId) o;
        return Objects.equals(cursoId, that.cursoId) &&
                Objects.equals(dataInicio, that.dataInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cursoId, dataInicio);
    }
}