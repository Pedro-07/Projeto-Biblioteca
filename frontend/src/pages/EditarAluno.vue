<template>
  <main class="main-content">
    <section class="section">
      <h2>Editar Aluno</h2>
      <form class="form" @submit.prevent="atualizarAluno">
        <div class="form-group">
          <label>Matrícula:</label>
          <input type="number" v-model="aluno.matricula" readonly />
        </div>

        <div class="form-group">
          <label>Nome:</label>
          <input
            type="text"
            v-model="aluno.nome"
            @input="validarNomeAluno"
            :class="{ 'input-error': aluno.nome && !/^[A-ZÀ-Ú\s]+$/.test(aluno.nome) }"
            required
          />
        </div>

        <div class="form-group">
          <label>Data de Nascimento:</label>
          <input type="date" v-model="aluno.dataNasc" required />
        </div>

        <div class="form-group">
          <label for="shift">Turno</label>
          <select id="shift" v-model="aluno.turno" class="form-control" required>
            <option value="" disabled>Selecione...</option>
            <option value="MATUTINO">Matutino</option>
            <option value="VESPERTINO">Vespertino</option>
            <option value="NOTURNO">Noturno</option>
          </select>
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

const idAluno = route.query.id
const aluno = ref({
  id_aluno: null,
  matricula: '',
  nome: '',
  dataNasc: '',
  turno: ''
})

function validarNomeAluno() {
  aluno.value.nome = aluno.value.nome
    .toUpperCase()
    .replace(/[^A-ZÀ-Ú\s]/g, '')
}

const carregarAluno = async () => {
  try {
    const response = await fetch(`http://localhost:8081/api/aluno/${idAluno}`);
    if (!response.ok) throw new Error('Erro ao buscar aluno');

    const dados = await response.json();
    aluno.value = {
      id_aluno: dados.id,
      matricula: dados.matricula,
      nome: dados.nome,
      dataNasc: dados.dataNasc,
      turno: dados.turno
    };
  } catch (err) {
    alert('Erro ao carregar aluno');
    router.push('/alunos');
  }
};

const atualizarAluno = async () => {
  try {
    const alunoData = {
      nome: aluno.value.nome,
      dataNasc: aluno.value.dataNasc,
      email: aluno.value.email,
      turno: aluno.value.turno
    };

    const response = await fetch(`http://localhost:8081/api/aluno/${idAluno}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(alunoData)
    });

    if (!response.ok) throw new Error()
    alert('Aluno atualizado com sucesso!')
    router.push('/alunos')
  } catch (err) {
    alert('Erro ao atualizar aluno.')
  }
}

const cancelar = () => {
  router.push('/alunos')
}

onMounted(async () => {
  if (!idAluno) {
    alert('ID do aluno não informado.')
    router.push('/alunos')
    return
  }
  await carregarAluno()
})
</script>

<style scoped>
.input-error {
  border: 1px solid red;
}
</style>
