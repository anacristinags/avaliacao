package com.example.avaliacao;

import com.example.avaliacao.model.Curso;
import com.example.avaliacao.service.CursoService;
import com.example.avaliacao.controller.CursoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CursoControllerTest {

    @InjectMocks
    private CursoController cursoController;

    @Mock
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testar POST /cursos
    @Test
    void testCriarCurso() {
        Curso curso = new Curso(null, "Engenharia de Software", "Curso avançado", 100, new ArrayList<>());
        Curso salvo = new Curso(1L, "Engenharia de Software", "Curso avançado", 100, new ArrayList<>());

        when(cursoService.salvarCurso(curso)).thenReturn(salvo);

        ResponseEntity<Curso> response = cursoController.criarCurso(curso);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(salvo, response.getBody());
    }

    // Testar GET /cursos
    @Test
    void testListarCursos() {
        List<Curso> cursos = List.of(new Curso(1L, "Curso A", "Descrição", 60, new ArrayList<>()));
        when(cursoService.listarCursos()).thenReturn(cursos);

        ResponseEntity<List<Curso>> response = cursoController.listarCursos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cursos, response.getBody());
    }

    // Testar GET /cursos/{id} quando encontrar
    @Test
    void testBuscarCursoPorId_Encontrado() {
        Curso curso = new Curso(1L, "Curso A", "Descrição", 60, new ArrayList<>());
        when(cursoService.buscarCursoPorId(1L)).thenReturn(Optional.of(curso));

        ResponseEntity<?> response = cursoController.buscarCursoPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(curso, response.getBody());
    }

    // Testar GET /cursos/{id} quando não encontrar
    @Test
    void testBuscarCursoPorId_NaoEncontrado() {
        when(cursoService.buscarCursoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = cursoController.buscarCursoPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    // Testar PUT /cursos/{id}
    @Test
    void testAtualizarCurso() {
        Curso novo = new Curso(null, "Atualizado", "Novo", 40, new ArrayList<>());
        Curso atualizado = new Curso(1L, "Atualizado", "Novo", 40, new ArrayList<>());

        when(cursoService.atualizarCurso(1L, novo)).thenReturn(Optional.of(atualizado));

        ResponseEntity<?> response = cursoController.atualizarCurso(1L, novo);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(atualizado, response.getBody());
    }

    // Testar PUT /cursos/{id} - curso não encontrado
    @Test
    void testAtualizarCurso_NaoEncontrado() {
        Curso novo = new Curso(null, "Atualizado", "Novo", 40, new ArrayList<>());
        when(cursoService.atualizarCurso(1L, novo)).thenReturn(Optional.empty());

        ResponseEntity<?> response = cursoController.atualizarCurso(1L, novo);

        assertEquals(404, response.getStatusCodeValue());
    }

    // Testar DELETE /cursos/{id} - sucesso
    @Test
    void testDeletarCurso_Sucesso() {
        when(cursoService.deletarCurso(1L)).thenReturn(true);

        ResponseEntity<?> response = cursoController.deletarCurso(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    // Testar DELETE /cursos/{id} - curso não encontrado
    @Test
    void testDeletarCurso_NaoEncontrado() {
        when(cursoService.deletarCurso(1L)).thenReturn(false);

        ResponseEntity<?> response = cursoController.deletarCurso(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}