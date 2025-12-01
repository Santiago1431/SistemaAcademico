package com.projeto.bd.SistemaAcademico.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.bd.SistemaAcademico.models.Enum.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Long id;

    @Column(name = "horario", length = 50)
    private String horario;

    // Mapeamento do ENUM do SQL para um ENUM em Java
    @Enumerated(EnumType.STRING)
    @Column(name = "turno", nullable = false)
    private Turno turno; // Usará a classe Turno

    @Column(name = "periodo", nullable = false, length = 10)
    private String periodo;

    // --- Mapeamentos ManyToOne (FKs) ---

    // FK para Disciplina
    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    // FK para Professor
    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    // --- Relacionamentos OneToMany ---

    // Um Turma tem muitas Matrículas
    @OneToMany(mappedBy = "turma")
    @JsonIgnore
    private List<MatriculaTurma> matriculas;
}



