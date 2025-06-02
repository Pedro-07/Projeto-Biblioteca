<template>
    <aside class="sidebar">
      <div class="sidebar-header">
        <h1>Sistema Biblioteca</h1>
      </div>
  
      <nav class="sidebar-nav">
        <div class="nav-item">
          <router-link to="/" class="nav-link">
            <i class="fas fa-home fa-sm"></i>
            <span>Início</span>
          </router-link>
        </div>
  
        <div class="nav-item">
          <div class="nav-link">
            <i class="fas fa-user-graduate fa-sm"></i>
            <span>Alunos</span>
          </div>
          <div class="dropdown">
            <router-link to="/alunos" class="dropdown-item">Ver Lista de Alunos</router-link>
            <router-link to="/cadastrarAluno" class="dropdown-item">Cadastrar Alunos</router-link>

          </div>
        </div>
  
        <div class="nav-item">
          <div class="nav-link">
            <i class="fas fa-book fa-sm"></i>
            <span>Livros</span>
          </div>
          <div class="dropdown">
            <router-link to="/livros" class="dropdown-item">Ver Lista de Livros</router-link>
            <router-link to="/cadastrarLivro" class="dropdown-item">Cadastrar Livros</router-link>
          </div>
        </div>
  
        <div class="nav-item">
          <div class="nav-link">
            <i class="fas fa-exchange-alt fa-sm"></i>
            <span>Empréstimos</span>
          </div>
          <div class="dropdown">
            <router-link to="/emprestimo" class="dropdown-item">Ver Empréstimos</router-link>
            <router-link to="/novoEmprestimo" class="dropdown-item">Novo Empréstimo</router-link>
          </div>
        </div>
      </nav>
    </aside>
  </template>
  