package com.example.avaliacao.controller;

import com.example.avaliacao.model.Aluno;
import com.example.avaliacao.service.AlunoService;

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
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Criar um novo aluno(a)",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Corpo da requisição para criar um novo aluno",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de Aluna",
                    description = "Aluna matriculada no curso de ID 1",
                    value = """
                    {
                    "nome": "Ana Cristina",
                    "email": "anaExemplo@gmail.com",
                    "dataNascimento": "2004-10-10",
                    "curso": {
                        "id": 1
                    }
                    }
                    """
                )
            )
        )
    )
    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody Aluno aluno) {
        try {
            Aluno novoAluno = alunoService.salvarAluno(aluno);
            return ResponseEntity.ok(novoAluno);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        return ResponseEntity.ok(alunoService.listarAlunos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
      @io.swagger.v3.oas.annotations.Operation(
        summary = "Atualizar um aluno",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Corpo da requisição para atualizar um aluno",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de Aluna",
                    description = "Aluna matriculada no curso de ID 1",
                    value = """
                    {
                    "nome": "Ana Cristina ATUALIZADA",
                    "email": "anaExemploATUALIZADO@gmail.com",
                    "dataNascimento": "2004-10-10",
                    "curso": {
                        "id": 1
                    }
                    }
                    """
                )
            )
        )
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        return alunoService.atualizarAluno(id, aluno)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAluno(@PathVariable Long id) {
        if (alunoService.deletarAluno(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}