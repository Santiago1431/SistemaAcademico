package com.projeto.bd.SistemaAcademico.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlunoMatriculaMultiplaDTO {
    private Long idAluno;
    private String nomeAluno;

    public AlunoMatriculaMultiplaDTO(Long idAluno, String nomeAluno) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
    }

}
