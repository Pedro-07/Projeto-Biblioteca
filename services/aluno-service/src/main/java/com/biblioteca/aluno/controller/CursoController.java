package com.biblioteca.aluno.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.aluno.model.Curso;
import com.biblioteca.aluno.services.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/curso")
@Tag(name = "Curso", description = "Operações relacionadas aos cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Operation(summary = "Listar todos os cursos disponíveis")
    @GetMapping("/listar")
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @Operation(summary = "Cadastrar um novo curso")
    @PostMapping("/cadastrar")
    public ResponseEntity<Curso> cadastrarCurso(@RequestBody Curso curso) {
        Curso novoCurso = cursoService.salvarCurso(curso);
        return ResponseEntity.ok(novoCurso);
    }

    @Operation(summary = "Buscar curso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.buscarPorId(id);
        return curso.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
