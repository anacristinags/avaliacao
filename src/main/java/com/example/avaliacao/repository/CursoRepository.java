package com.example.avaliacao.repository;

import com.example.avaliacao.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}