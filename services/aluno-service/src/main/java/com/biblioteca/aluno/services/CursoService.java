package com.biblioteca.aluno.services;

import java.util.List;
import java.util.Optional;

import com.biblioteca.aluno.model.Curso;

public interface CursoService {
    List<Curso> listarCursos();
    
    Curso salvarCurso(Curso curso);

    Optional<Curso> buscarPorId(Long id);
}