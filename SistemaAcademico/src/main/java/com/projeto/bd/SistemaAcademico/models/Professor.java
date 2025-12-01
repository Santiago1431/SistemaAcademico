package com.projeto.bd.SistemaAcademico.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "CPF", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "departamento", length = 100)
    private String departamento;

    // Relacionamento com Turma:
    // Um Professor pode ministrar várias Turmas
    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Turma> turmas;


    // Relacionamento com Coordenacao
    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Coordenacao> coordenacoes;
}
