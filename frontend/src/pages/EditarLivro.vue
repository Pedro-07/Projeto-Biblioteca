<template>
  <main class="main-content">
    <section class="section">
      <h2>Editar Livro</h2>
      <form class="form" @submit.prevent="atualizarLivro">
        <div class="form-group">
          <label>Título:</label>
          <input
            type="text"
            v-model="livro.titulo"
            @input="validarTitulo"
            required
          />
        </div>

        <div class="form-group">
          <label>Autor:</label>
          <input
            type="text"
            v-model="livro.autor"
            @input="validarAutor"
            :class="{
              'input-error': livro.autor && !/^[A-Z\s]*$/.test(livro.autor),
            }"
            required
          />
        </div>

        <div class="form-group">
          <label>Editora:</label>
          <input
            type="text"
            v-model="livro.editora"
            @input="validarEditora"
            required
          />
        </div>

        <div class="form-group">
          <label>ISBN:</label>
          <input
            type="text"
            v-model="livro.isbn"
            @input="validarISBN"
            :class="{ 'input-error': livro.isbn && livro.isbn.length !== 13 }"
            required
          />
        </div>

        <div class="form-group">
          <label>Ano de Publicação:</label>
          <input
            type="number"
            v-model.number="livro.anoPubli"
            @input="validarAno"
            :class="{ 'input-error': anoInvalido }"
            required
          />
        </div>

        <div class="form-group">
          <label>Quantidade:</label>
          <input
            type="number"
            v-model.number="livro.quantidade"
            min="1"
            max="100"
            :class="{ 'input-error': livro.quantidade <= 0 }"
            required
          />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn-primary">Salvar Alterações</button>
          <button type="button" class="btn-delete" @click="cancelar">Cancelar</button>
        </div>
      </form>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const idLivro = route.query.id

const livro = ref({
  titulo: '',
  autor: '',
  editora: '',
  isbn: '',
  anoPubli: '',
  quantidade: ''
})

const anoInvalido = ref(false)

function validarTitulo() {
  livro.value.titulo = livro.value.titulo.toUpperCase()
}

function validarAutor() {
  livro.value.autor = livro.value.autor.toUpperCase().replace(/[^A-Z\s]/g, "")
}

function validarEditora() {
  livro.value.editora = livro.value.editora.replace(/[^A-Z0-9 ]/g, "")
}

function validarISBN() {
  livro.value.isbn = livro.value.isbn.replace(/\D/g, '').slice(0, 13)
}

function validarAno() {
  const anoAtual = new Date().getFullYear()
  const ano = livro.value.anoPubli?.toString().slice(0, 4)
  livro.value.anoPubli = ano
  anoInvalido.value = !/^\d{4}$/.test(ano) || parseInt(ano) > anoAtual
}

function isISBNValido(isbn) {
  const digitsOnly = isbn.replace(/\D/g, "")
  return digitsOnly.length === 13
}

const carregarLivro = async () => {
  try {
    const response = await fetch(`http://localhost:8082/api/livro/${idLivro}`)
    if (!response.ok) throw new Error('Livro não encontrado')
    const data = await response.json()
    livro.value = { ...data } // preenche os dados
  } catch (err) {
    alert('Erro ao carregar livro')
    router.push('/livros')
  }
}

const atualizarLivro = async () => {
  if (!isISBNValido(livro.value.isbn)) {
    alert("O ISBN deve conter exatamente 13 dígitos numéricos.")
    return
  }

  if (livro.value.quantidade > 100 || livro.value.quantidade <= 0) {
    alert("A quantidade deve ser entre 1 e 100.")
    return
  }

  try {
    const response = await fetch(`http://localhost:8082/api/livro/${idLivro}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(livro.value)
    })
    if (!response.ok) throw new Error()
    alert('Livro atualizado com sucesso!')
    router.push('/livros')
  } catch (err) {
    alert('Erro ao atualizar livro.')
  }
}

const cancelar = () => {
  router.push('/livros')
}

onMounted(() => {
  if (!idLivro) {
    alert('ID do livro não informado.')
    router.push('/livros')
    return
  }
  carregarLivro()
})
</script>
