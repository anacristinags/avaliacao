package com.example.avaliacao.service;

import com.example.avaliacao.model.Curso;
import com.example.avaliacao.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso salvarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Optional<Curso> atualizarCurso(Long id, Curso novoCurso) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNomeCurso(novoCurso.getNomeCurso());
            curso.setDescricao(novoCurso.getDescricao());
            curso.setCargaHoraria(novoCurso.getCargaHoraria());
            return cursoRepository.save(curso);
        });
    }

    public boolean deletarCurso(Long id) {
        return cursoRepository.findById(id).map(curso -> {
            cursoRepository.delete(curso);
            return true;
        }).orElse(false);
    }
}