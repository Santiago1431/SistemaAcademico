package com.projeto.bd.SistemaAcademico.Controllers;


import com.projeto.bd.SistemaAcademico.Services.DisciplinaService;
import com.projeto.bd.SistemaAcademico.models.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    // --- CRUD BÁSICO ---

    // Endpoint GET: Buscar todas as disciplinas (CRUD Básico)
    @GetMapping
    public ResponseEntity<List<Disciplina>> buscarTodas() {
        return ResponseEntity.ok(disciplinaService.buscarTodas());
    }

    // --- CONSULTAS COMPLEXAS (Etapa 5) ---

    /**
     * Consulta Complexa #5.1 (Subquery NOT IN): Disciplinas sem nenhuma turma aberta.
     * URL: GET http://localhost:8080/api/disciplinas/sem-turma
     */
    @GetMapping("/sem-turma")
    public ResponseEntity<List<String>> listarDisciplinasSemTurma() {
        // Retorna apenas uma lista de nomes (Strings), conforme definido no Repository
        List<String> lista = disciplinaService.obterDisciplinasSemTurma();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Consulta Complexa #5.2 (Strings LIKE): Busca por nome parcial.
     * URL: GET http://localhost:8080/api/disciplinas/buscar?termo=Calculo
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Disciplina>> buscarPorNomeParcial(@RequestParam String termo) {
        // Implementa a funcionalidade de busca da interface (LIKE '%termo%')
        List<Disciplina> resultado = disciplinaService.buscarPorNome(termo);

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    // OBS: O CRUD de POST/PUT/DELETE também deve ser implementado, mas está omitido aqui.


    @PostMapping("add")
    public ResponseEntity<Disciplina> adicionarDisciplina(@RequestBody Disciplina disciplina) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaService.salvar(disciplina));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizarDisciplina( @PathVariable Long id, @RequestBody Disciplina disciplinaDetalhes ) {
        disciplinaDetalhes.setId(id);
        Disciplina novaDisciplina = disciplinaService.salvar(disciplinaDetalhes);
        return ResponseEntity.ok(novaDisciplina);
    }

    @DeleteMapping("/{id}/dell")
    public ResponseEntity<Disciplina> removerDisciplina( @PathVariable Long id){
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        return ResponseEntity.ok(disciplinaService.deletar(disciplina));

    }


}
