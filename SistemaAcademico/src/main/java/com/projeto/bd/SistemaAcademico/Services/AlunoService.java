package com.projeto.bd.SistemaAcademico.Services;

import com.projeto.bd.SistemaAcademico.DTO.AlunoMatriculaMultiplaDTO;
import com.projeto.bd.SistemaAcademico.DTO.HistoricoDetalhadoDTO;
import com.projeto.bd.SistemaAcademico.Repositories.AlunoRepository;
import com.projeto.bd.SistemaAcademico.models.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    /**
     * Serviço que chama a consulta complexa de JOINs múltiplos para obter o histórico detalhado do aluno.
     * @param alunoId ID do aluno para buscar o histórico.
     * @return Lista de HistoricoDetalhadoDTO.
     */

    public List<Aluno> findAll(){
        return alunoRepository.findAll();
    }

    public List<HistoricoDetalhadoDTO> obterHistoricoDetalhado(Long alunoId) {
        // A lógica de negócio pode incluir validação (ex: verificar se o aluno existe)
        // Antes de chamar o Repositório.

        // Chamada direta para a @Query implementada no Repositório
        return alunoRepository.buscarHistoricoDetalhado(alunoId);
    }

    public Aluno salvarAluno(Aluno aluno) {
        // Aqui pode adicionar lógica de negócio, como validação de CPF.
        return alunoRepository.save(aluno);
    }

    public List<AlunoMatriculaMultiplaDTO> buscarAlunosComMultiplasMatriculas() {
        return alunoRepository.buscarAlunosComMultiplasMatriculas();
    }
}
