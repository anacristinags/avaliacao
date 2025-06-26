package com.example.avaliacao.controller;

import com.example.avaliacao.model.Curso;
import com.example.avaliacao.service.CursoService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Criar um novo curso",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Corpo da requisição para criar um novo curso",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de Curso",
                    description = "Curso de Arquitetura WEB com 80 horas",
                    value = """
                    {
                    "nomeCurso": "Arquitetura de Aplicacoes WEB",
                    "descricao": "Estudo da estrutura de sistemas.",
                    "cargaHoraria": 80
                    }
                    """
                )
            )
        )
    )
    @PostMapping
    public ResponseEntity<Curso> criarCurso(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.salvarCurso(curso));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCursoPorId(@PathVariable Long id) {
        return cursoService.buscarCursoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Atualizar curso",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Corpo da requisição para atualizar um curso",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de Curso",
                    description = "Curso de Arquitetura WEB com 100 horas",
                    value = """
                    {
                    "nomeCurso": "Arquitetura de Aplicacoes WEB ATUALIZADO",
                    "descricao": "Estudo da estrutura de sistemas. ATUALIZADO",
                    "cargaHoraria": 100
                    }
                    """
                )
            )
        )
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        return cursoService.atualizarCurso(id, curso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCurso(@PathVariable Long id) {
        if (cursoService.deletarCurso(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
