package com.projeto.bd.SistemaAcademico.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MediaTurmaDTO {

    private Long idTurma;
    private String nomeDisciplina;
    private BigDecimal mediaNotas;
    private Long totalAlunos;

    public MediaTurmaDTO(Long idTurma, String nomeDisciplina, Double mediaNotas, Long totalAlunos) {
        this.idTurma = idTurma;
        this.nomeDisciplina = nomeDisciplina;
        this.mediaNotas = mediaNotas != null ? BigDecimal.valueOf(mediaNotas) : null;
        this.totalAlunos = totalAlunos;
    }
}
