package com.projeto.bd.SistemaAcademico.models;


import com.projeto.bd.SistemaAcademico.models.Embeddable.MatriculaTurmaId;
import com.projeto.bd.SistemaAcademico.models.Enum.Situacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Matricula_Turma")
public class MatriculaTurma {

    //  Chave Composta
    @EmbeddedId
    private MatriculaTurmaId id;

    @Column(name = "nota_final", precision = 4, scale = 2)
    private BigDecimal notaFinal; // Usando BigDecimal para precisão de notas

    @Column(name = "frequencia", precision = 5, scale = 2)
    private BigDecimal frequencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Situacao situacao;

    //  Mapeamento das FKs como objetos (Muitas Matrículas para Um Aluno)
    @ManyToOne
    @JoinColumn(name = "id_aluno", insertable = false, updatable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_turma", insertable = false, updatable = false)
    private Turma turma;



}


