package com.projeto.bd.SistemaAcademico.models;


import com.projeto.bd.SistemaAcademico.models.Embeddable.CoordenacaoId;
import jakarta.persistence.*;

@Entity
@Table(name = "coordenacao")
public class Coordenacao {

    // Mapeia a chave composta
    @EmbeddedId
    private CoordenacaoId id;

    // Mapeamento da FK id_curso (implícita na chave composta)
    @ManyToOne
    // insertable/updatable = false para evitar que o JPA tente gerenciar esta coluna fora da chave composta
    @JoinColumn(name = "id_curso", insertable = false, updatable = false)
    private Curso curso;

    // Mapeamento da FK id_professor (quem coordenou)
    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;
}
