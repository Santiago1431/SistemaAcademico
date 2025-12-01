package com.projeto.bd.SistemaAcademico.Controllers;

import com.projeto.bd.SistemaAcademico.DTO.MediaTurmaDTO;
import com.projeto.bd.SistemaAcademico.Services.TurmasService;
import com.projeto.bd.SistemaAcademico.models.Turma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmasService turmaService;

    /**
     * Endpoint para consultar a média geral e contagem de alunos de todas as turmas
     * (Consulta Complexa #2 - Agregadas).
     * URL de exemplo: GET http://localhost:8080/api/turmas/media-geral
     */

    @GetMapping
    public ResponseEntity<List<Turma>> buscarTodas() {
        return ResponseEntity.ok(turmaService.buscarTodas());
    }

    @GetMapping("/media-geral")
    public ResponseEntity<List<MediaTurmaDTO>> obterMediaGeral() {

        List<MediaTurmaDTO> medias = turmaService.calcularMediaGeral();

        if (medias.isEmpty()) {
            // Retorna 204 No Content se não houver dados agregados
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(medias);
    }
}
