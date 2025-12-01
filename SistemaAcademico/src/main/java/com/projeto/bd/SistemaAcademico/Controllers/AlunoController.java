package com.projeto.bd.SistemaAcademico.Controllers;

import com.projeto.bd.SistemaAcademico.DTO.AlunoMatriculaMultiplaDTO;
import com.projeto.bd.SistemaAcademico.DTO.HistoricoDetalhadoDTO;
import com.projeto.bd.SistemaAcademico.Services.AlunoService;
import com.projeto.bd.SistemaAcademico.models.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;


    /**
     * Endpoint para consultar o histórico detalhado de um aluno (Consulta Complexa #1 - JOINs Múltiplas).
     * URL de exemplo: GET http://localhost:8080/api/alunos/1/historico
     * @param id ID do aluno.
     * @return O histórico detalhado em formato JSON.
     */

    @GetMapping
    public ResponseEntity<List<Aluno>> buscarTodosAlunos(){
        return ResponseEntity.ok(alunoService.findAll());
    }
    @GetMapping("/{id}/historico")
    public ResponseEntity<List<HistoricoDetalhadoDTO>> buscarHistorico(
            @PathVariable("id") Long id) {

        List<HistoricoDetalhadoDTO> historico = alunoService.obterHistoricoDetalhado(id);

        if (historico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(historico);
    }

    @PostMapping("/add")
    public ResponseEntity<Aluno>  salvarAluno(@RequestBody Aluno aluno) {
        Aluno novoAluno = alunoService.salvarAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno alunoDetalhes){
        alunoDetalhes.setId(id);
        Aluno alunoAtualizado = alunoService.salvarAluno(alunoDetalhes);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @GetMapping("/regulares")
    public ResponseEntity<List<AlunoMatriculaMultiplaDTO>> buscarAlunosRegulares() {
        List<AlunoMatriculaMultiplaDTO> alunos = alunoService.buscarAlunosComMultiplasMatriculas();
        if (alunos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alunos);
    }




}
