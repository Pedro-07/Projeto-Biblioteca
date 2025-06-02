<template>
  <main class="main-content">
    <section class="section">
      <h2>Novo Empréstimo</h2>
      <form class="form" @submit.prevent="realizarEmprestimo">
        <div class="form-group">
          <label for="titulo">Livro</label>
          <input type="text" id="titulo" v-model="tituloLivro" @input="
            tituloLivro = tituloLivro.toUpperCase();
          buscarLivros();
          " autocomplete="off" />

          <!-- SUGESTÕES DE LIVROS CORRIGIDAS -->
          <div class="suggestions-list" v-if="sugestoesLivros.length">
            <div class="suggestion-item" v-for="livro in sugestoesLivros" :key="livro.id"
              @click="selecionarLivro(livro)">
              <span class="suggestion-title">
                {{ livro.titulo }}
              </span>
              <div class="suggestion-description">
                ISBN: {{ livro.isbn || 'N/A' }}
              </div>
            </div>
          </div>
        </div> <!-- ← FECHAMENTO DO form-group do livro -->

        <div class="form-group">
          <label for="isbn">ISBN</label>
          <input type="text" id="isbn" v-model="emprestimo.livro.isbn" readonly />
        </div>

        <div class="form-group">
          <label for="nome-aluno">Nome do Aluno</label>
          <input type="text" id="nome-aluno" v-model="nomeAluno"
            :placeholder="erroNomeAluno ? 'Aluno não encontrado' : 'Digite o nome do aluno'"
            :class="{ 'input-error': !/^[A-Z\s]*$/.test(nomeAluno) && nomeAluno }" @input="
              erroNomeAluno = false;
            nomeAluno = nomeAluno.toUpperCase().replace(/[^A-Z\s]/g, '');
            buscarAlunosPorNome();
            " />

          <!-- SUGESTÕES DE ALUNOS CORRIGIDAS -->
          <div class="suggestions-list" v-if="sugestoesAlunos.length">
            <div class="suggestion-item" v-for="aluno in sugestoesAlunos" :key="aluno.id"
              @click="selecionarAluno(aluno)">
              <span class="suggestion-title">
                {{ aluno.nome }}
              </span>
              <div class="suggestion-description">
                Matrícula: {{ aluno.matricula }}
              </div>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label for="matricula">Matrícula do Aluno</label>
          <input type="text" id="matricula" v-model="emprestimo.aluno.matricula" maxlength="4" inputmode="numeric"
            pattern="^\d{4}$" :class="{
              'input-error':
                !/^\d{4}$/.test(emprestimo.aluno.matricula) && emprestimo.aluno.matricula
            }" @input="
              emprestimo.aluno.matricula = emprestimo.aluno.matricula
                .replace(/\D/g, '')
                .slice(0, 4);
            buscarAlunoPorMatricula();
            " />
        </div>

        <div class="form-group">
          <label>Data Empréstimo</label>
          <input type="date" v-model="emprestimo.dataEmprestimo" disabled />
        </div>

        <div class="form-group">
          <label>Data Devolução</label>
          <input type="date" v-model="emprestimo.dataDevolucao" disabled />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn-primary">Emprestar</button>
        </div>
      </form>
    </section>
  </main>
</template>


<script setup>
import { ref } from "vue";

const tituloLivro = ref("");
const nomeAluno = ref("");
const sugestoesLivros = ref([]);
const sugestoesAlunos = ref([]);
const livroSelecionado = ref(null);
const alunoSelecionado = ref(null);

const emprestimo = ref({
  aluno: { matricula: "" },
  livro: { isbn: "" },
  status: "ATIVO",
  dataEmprestimo: "",
  dataDevolucao: "",
});

const erroNomeAluno = ref(false);
const hoje = new Date();
const devolucao = new Date();
devolucao.setDate(hoje.getDate() + 10);
emprestimo.value.dataEmprestimo = hoje.toISOString().split("T")[0];
emprestimo.value.dataDevolucao = devolucao.toISOString().split("T")[0];

async function buscarLivros() {
  if (tituloLivro.value.length < 2) return;
  try {
    const response = await fetch(
      `http://localhost:8082/api/livro/titulos?termo=${encodeURIComponent(tituloLivro.value)}`
    );
    sugestoesLivros.value = await response.json();
  } catch {
    sugestoesLivros.value = [];
  }
}

async function selecionarLivro(livro) {
  tituloLivro.value = livro.titulo;
  sugestoesLivros.value = [];
  livroSelecionado.value = livro;

  try {
    const res = await fetch(`http://localhost:8082/api/livro/${livro.id}`);
    if (!res.ok) throw new Error("Erro ao buscar o livro");
    const detalhes = await res.json();
    emprestimo.value.livro.isbn = detalhes.isbn || "";
  } catch (err) {
    console.error("Erro ao carregar detalhes do livro:", err);
    emprestimo.value.livro.isbn = "";
  }
}

async function buscarAlunoPorMatricula() {
  if (!emprestimo.value.aluno.matricula) return;
  try {
    const response = await fetch(
      `http://localhost:8081/api/aluno/buscar/${emprestimo.value.aluno.matricula}`
    );
    if (!response.ok) throw new Error();
    const aluno = await response.json();
    nomeAluno.value = aluno.nome;
    alunoSelecionado.value = aluno;
    erroNomeAluno.value = false;
  } catch {
    nomeAluno.value = "";
    erroNomeAluno.value = true;
    alunoSelecionado.value = null;
  }
}

async function buscarAlunosPorNome() {
  if (nomeAluno.value.length < 2) return;
  try {
    const response = await fetch(
      `http://localhost:8081/api/aluno/buscar-por-nome?nome=${encodeURIComponent(nomeAluno.value)}`
    );
    sugestoesAlunos.value = await response.json();
  } catch {
    sugestoesAlunos.value = [];
  }
}

function selecionarAluno(aluno) {
  nomeAluno.value = aluno.nome;
  emprestimo.value.aluno.matricula = aluno.matricula;
  alunoSelecionado.value = aluno; // ✅ Correção aqui
  sugestoesAlunos.value = [];
}

async function realizarEmprestimo() {
  if (!alunoSelecionado.value || !livroSelecionado.value) {
    alert("Selecione um aluno e um livro válidos.");
    return;
  }

  const payload = {
    alunoId: alunoSelecionado.value.id,
    livroId: livroSelecionado.value.id,
    status: emprestimo.value.status,
    dataEmprestimo: emprestimo.value.dataEmprestimo,
    dataDevolucao: emprestimo.value.dataDevolucao,
  };

  console.log("Enviando:", payload);

  try {
    const response = await fetch("http://localhost:8083/api/emprestimo/emprestar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    if (response.ok) {
      alert("Empréstimo registrado com sucesso!");
      tituloLivro.value = "";
      nomeAluno.value = "";
      emprestimo.value.livro.isbn = "";
      emprestimo.value.aluno.matricula = "";
      livroSelecionado.value = null;
      alunoSelecionado.value = null;
    } else {
      const erro = await response.text();
      if (erro.includes("Limite")) {
        alert("Limite de empréstimos atingido.");
      } else {
        alert("Erro ao registrar empréstimo.");
      }
    }
  } catch (err) {
    alert("Erro ao conectar com o servidor.");
    console.error(err);
  }
}
</script>



<style scoped>
/* Layout base */
.main-content {
  padding: 2rem;
  background: #f9fafb;
}

/* Títulos e textos */
h2 {
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #1f2937;
}

/* Formulário */
.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  position: relative;
  display: flex;
  flex-direction: column;
}

label {
  font-size: 0.875rem;
  color: #374151;
  margin-bottom: 0.25rem;
}

input {
  padding: 0.5rem 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  font-size: 1rem;
  transition: all 0.2s ease;
  width: 100%;
}

input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

/* Validação */
.input-error {
  border-color: #ef4444 !important;
  background-color: #fef2f2;
}

/* Botão */
.btn-primary {
  background-color: #2563eb;
  color: white;
  padding: 0.75rem 1.25rem;
  font-size: 1rem;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-primary:hover {
  background-color: #1e40af;
}

/* Sugestões (autocomplete) */
.suggestions-list {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  z-index: 10;
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 0.5rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  margin-top: 0.25rem;
  max-height: 15rem;
  overflow-y: auto;
}

.suggestion-item {
  padding: 0.75rem 1rem;
  cursor: pointer;
  border-bottom: 1px solid #f3f4f6;
}

.suggestion-item:last-child {
  border-bottom: none;
}

.suggestion-item:hover {
  background-color: #f0f7ff;
  color: #1d4ed8;
}

.suggestion-title {
  font-weight: 500;
}

.suggestion-description {
  font-size: 0.875rem;
  color: #6b7280;
  margin-top: 0.25rem;
}

/* Responsividade */
@media (max-width: 640px) {
  .section {
    padding: 1rem;
  }

  .btn-primary {
    width: 100%;
  }
}
</style>
