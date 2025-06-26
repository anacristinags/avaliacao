package com.example.avaliacao.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data 
@NoArgsConstructor
@AllArgsConstructor

public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nome;
    private String email;
    private Date dataNascimento;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonBackReference
    private Curso curso;

}