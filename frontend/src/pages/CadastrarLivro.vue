<template>
  <main class="main-content">
    <section id="add" class="section">
      <h2>Cadastrar Novo Livro</h2>
      <form class="form" @submit.prevent="cadastrarLivro">
        <div class="form-group">
          <label for="title">Título</label>
          <input
            type="text"
            id="title"
            v-model="livro.titulo"
            @input="livro.titulo = livro.titulo.toUpperCase()"
            :class="{ 'input-error': !livro.titulo }"
            required
          />
        </div>

        <div class="form-group">
          <label for="author">Autor</label>
          <input
            type="text"
            id="author"
            v-model="livro.autor"
            @input="validarAutor"
            :class="{
              'input-error': livro.autor && !/^[A-Z\s]*$/.test(livro.autor),
            }"
            required
          />
        </div>

        <div class="form-group">
          <label for="editora">Editora</label>
          <input
            type="text"
            id="editora"
            v-model="livro.editora"
            @input="
              livro.editora = livro.editora
                .toUpperCase()
                .replace(/[^A-Z0-9 ]/g, '')
            "
            required
          />
        </div>

        <div class="form-group">
          <label for="isbn">ISBN</label>
          <input
            type="text"
            id="isbn"
            v-model="livro.isbn"
            @input="livro.isbn = livro.isbn.replace(/\D/g, '').slice(0, 13)"
            :class="{ 'input-error': livro.isbn && livro.isbn.length !== 13 }"
            required
          />
        </div>

        <div class="form-group">
          <label for="anoPubli">Ano de Publicação</label>
          <input
            type="number"
            id="anoPubli"
            v-model="livro.anoPubli"
            @input="validarAno"
            :class="{ 'input-error': anoInvalido }"
            required
          />
        </div>

        <div class="form-group">
          <label for="quantidade">Quantidade</label>
          <input
            type="number"
            id="quantidade"
            v-model="livro.quantidade"
            min="1"
            max="100"
            :class="{ 'input-error': livro.quantidade <= 0 }"
            required
          />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn-primary">Cadastrar Livro</button>
        </div>
      </form>
    </section>
  </main>
</template>

<script setup>
import { ref } from "vue";

const livro = ref({
  titulo: '',
  autor: '',
  editora: '',
  isbn: '',
  anoPubli: '',
  quantidade: ''
});

const anoInvalido = ref(false);

function validarAno() {
  const anoAtual = new Date().getFullYear();
  const ano = livro.value.anoPubli?.toString().slice(0, 4);
  livro.value.anoPubli = ano;

  anoInvalido.value =
    !/^\d{4}$/.test(ano) || parseInt(ano) > anoAtual;
}


function validarTitulo() {
  livro.value.titulo = livro.value.titulo.toUpperCase();
}

function validarAutor() {
  livro.value.autor = livro.value.autor.toUpperCase().replace(/[^A-Z\s]/g, "");
}

function validarEditora() {
  livro.value.editora = livro.value.editora.replace(/[^a-zA-Z0-9\s]/g, "");
}

function validarISBN() {
  // Permite apenas números
  livro.value.isbn = livro.value.isbn.replace(/\D/g, "").slice(0, 13);
}

function isISBNValido(isbn) {
  const digitsOnly = isbn.replace(/\D/g, "");
  return digitsOnly.length === 13;
}

function limitarAno() {
  if (livro.value.anoPubli.toString().length > 4) {
    livro.value.anoPubli = livro.value.anoPubli.toString().slice(0, 4);
  }
}

async function cadastrarLivro() {
  // Verifica se o ISBN é válido
  if (!isISBNValido(livro.value.isbn)) {
    alert("O ISBN deve conter exatamente 13 dígitos numéricos.");
    return;
  }

  // Validação de quantidade
  if (livro.value.quantidade > 100) {
    alert("A quantidade disponível não pode ultrapassar 100 unidades.");
    return;
  }

  // Dados a serem enviados para o backend
  const livroData = {
    titulo: livro.value.titulo,
    autor: livro.value.autor,
    editora: livro.value.editora,
    isbn: livro.value.isbn,
    anoPubli: parseInt(livro.value.anoPubli),
    quantidade: parseInt(livro.value.quantidade),
  };

  try {
    // Fazendo a requisição para cadastrar o livro
    const response = await fetch("http://localhost:8082/api/livro/cadastrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(livroData),
    });

    if (response.ok) {
      alert("Livro cadastrado com sucesso!");
      // Limpa os campos após o cadastro
      livro.value = {
        titulo: "",
        autor: "",
        editora: "",
        isbn: "",
        anoPubli: "",
        quantidade: "",
      };
    } else {
      const erro = await response.json();
      alert("Erro: " + (erro.message || "Erro desconhecido."));
    }
  } catch (error) {
    alert("Erro ao conectar com o servidor.");
  }
}

</script>


