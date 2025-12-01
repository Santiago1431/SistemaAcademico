package com.projeto.bd.SistemaAcademico.Services;

import com.projeto.bd.SistemaAcademico.DTO.MediaTurmaDTO;
import com.projeto.bd.SistemaAcademico.Repositories.TurmaRepository;
import com.projeto.bd.SistemaAcademico.models.Turma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmasService {
    @Autowired
    private TurmaRepository turmaRepository;

    /**
     * Serviço que chama a consulta agregada para calcular média e total de alunos por turma.
     * (Consulta Complexa #2: AVG, COUNT, GROUP BY)
     */
    public List<MediaTurmaDTO> calcularMediaGeral() {
        return turmaRepository.calcularMediaGeralPorTurma();
    }

    public List<Turma> buscarTodas() {
        return turmaRepository.findAll();
    }
}
