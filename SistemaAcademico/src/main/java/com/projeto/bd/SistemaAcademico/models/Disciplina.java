package com.projeto.bd.SistemaAcademico.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_disciplina")
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "Carga",  nullable = false)
    private float cargaHoraria;

    // Relacionamento com Turma:
    // Uma Disciplina pode ter muitas Turmas abertas
    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    private List<Turma> turmas;

    // Relacionamento M:N com Curso, mapeado pela tabela associativa CursoDisciplina
    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    private List<CursoDisciplina> cursos;
}
