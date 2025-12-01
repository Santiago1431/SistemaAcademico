package com.projeto.bd.SistemaAcademico.DTO;

import com.projeto.bd.SistemaAcademico.models.Enum.Situacao;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class HistoricoDetalhadoDTO {

    private String nomeAluno;
    private String nomeDisciplina;
    private String nomeProfessor;
    private BigDecimal notaFinal;
    private String situacao;
    private String periodo;
    private BigDecimal frequencia;

    public HistoricoDetalhadoDTO(String nomeAluno, String nomeDisciplina, String nomeProfessor,
                                 BigDecimal notaFinal, Situacao situacaoEnum, String periodo, // <-- Aqui está a mudança
                                 BigDecimal frequencia) {
        this.nomeAluno = nomeAluno;
        this.nomeDisciplina = nomeDisciplina;
        this.nomeProfessor = nomeProfessor;
        this.notaFinal = notaFinal;
        this.situacao = situacaoEnum.name(); // Converte o ENUM para String aqui dentro
        this.periodo = periodo;
        this.frequencia = frequencia;
    }
}
