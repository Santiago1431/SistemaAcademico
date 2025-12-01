package com.projeto.bd.SistemaAcademico.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfessorRankingDTO {
    private String nomeProfessor;
    private Long totalTurmas;

    public ProfessorRankingDTO(String nomeProfessor, Long totalTurmas) {
        this.nomeProfessor = nomeProfessor;
        this.totalTurmas = totalTurmas;
    }
}
