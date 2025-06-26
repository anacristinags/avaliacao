package com.example.avaliacao;

import com.example.avaliacao.model.Aluno;
import com.example.avaliacao.model.Curso;
import com.example.avaliacao.controller.AlunoController;
import com.example.avaliacao.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarAlunoComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Cristina");
        aluno.setEmail("anaExemplo@gmail.com");
        aluno.setDataNascimento(Date.valueOf(LocalDate.of(2004, 10, 10)));
        aluno.setCurso(new Curso());

        when(alunoService.salvarAluno(any(Aluno.class))).thenReturn(aluno);

        ResponseEntity<?> response = alunoController.criarAluno(aluno);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(aluno);
    }

    @Test
    void testListarAlunos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana");
        when(alunoService.listarAlunos()).thenReturn(List.of(aluno));

        ResponseEntity<List<Aluno>> response = alunoController.listarAlunos();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void testBuscarAlunoPorIdEncontrado() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        when(alunoService.buscarAlunoPorId(1L)).thenReturn(Optional.of(aluno));

        ResponseEntity<?> response = alunoController.buscarAlunoPorId(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(aluno);
    }

    @Test
    void testBuscarAlunoPorIdNaoEncontrado() {
        when(alunoService.buscarAlunoPorId(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = alunoController.buscarAlunoPorId(999L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void testAtualizarAlunoComSucesso() {
        Aluno alunoAtualizado = new Aluno();
        alunoAtualizado.setNome("Atualizado");

        when(alunoService.atualizarAluno(eq(1L), any())).thenReturn(Optional.of(alunoAtualizado));

        ResponseEntity<?> response = alunoController.atualizarAluno(1L, alunoAtualizado);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(alunoAtualizado);
    }

    @Test
    void testAtualizarAlunoNaoEncontrado() {
        when(alunoService.atualizarAluno(eq(1L), any())).thenReturn(Optional.empty());

        ResponseEntity<?> response = alunoController.atualizarAluno(1L, new Aluno());

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void testDeletarAlunoComSucesso() {
        when(alunoService.deletarAluno(1L)).thenReturn(true);

        ResponseEntity<?> response = alunoController.deletarAluno(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testDeletarAlunoNaoEncontrado() {
        when(alunoService.deletarAluno(99L)).thenReturn(false);

        ResponseEntity<?> response = alunoController.deletarAluno(99L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}