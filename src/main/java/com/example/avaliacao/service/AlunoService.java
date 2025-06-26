package com.example.avaliacao.service;

import com.example.avaliacao.model.Aluno;
import com.example.avaliacao.model.Curso;
import com.example.avaliacao.repository.AlunoRepository;
import com.example.avaliacao.repository.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public Aluno salvarAluno (Aluno aluno){
        if (aluno.getCurso() != null) {
            Optional<Curso> curso = cursoRepository.findById(aluno.getCurso().getId());
            if (curso.isEmpty()) {
                throw new IllegalArgumentException("Curso não encontrado");
            }
            aluno.setCurso(curso.get());
        }
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> atualizarAluno(Long id, Aluno novoAluno) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(novoAluno.getNome());
            aluno.setEmail(novoAluno.getEmail());
            aluno.setDataNascimento(novoAluno.getDataNascimento());
            aluno.setCurso(novoAluno.getCurso());
            return alunoRepository.save(aluno);
        });
    }

    public boolean deletarAluno(Long id) {
        return alunoRepository.findById(id).map(aluno -> {
            alunoRepository.delete(aluno);
            return true;
        }).orElse(false);
    }
}