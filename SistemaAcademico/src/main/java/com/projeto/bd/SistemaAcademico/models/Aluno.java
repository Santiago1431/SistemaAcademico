package com.projeto.bd.SistemaAcademico.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id;

    @Column(name = "CPF", length = 14, nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    // Relacionamento com Matricula_Turma:
    // Um Aluno pode ter muitas Matrículas
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MatriculaTurma> matriculas;

}
