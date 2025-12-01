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
@Table(name = "Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "carga", nullable = false)
    private Float cargaHoraria;

    // Relacionamento com Disciplina (M:N), mapeado pela tabela associativa CursoDisciplina
    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private List<CursoDisciplina> disciplinas;

    // Relacionamento com Coordenacao (Histórico de coordenadores)
    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private List<Coordenacao> historicoCoordenacao;

}
