<template>
  <main class="main-content">
    <section id="add" class="section">
      <h2>Cadastrar Novo Aluno</h2>
      <form class="form" @submit.prevent="cadastrarAluno">
        <div class="form-group">
          <label for="nameStudent">Nome do Aluno</label>
          <input
            type="text"
            id="nameStudent"
            v-model="aluno.nome"
            @input="validarNomeAluno"
            :class="{ 'input-error': aluno.nome && !/^[A-Z\s]+$/.test(aluno.nome) }"
            required
          />
        </div>

        <div class="form-group">
          <label for="datebirth">Data Nasc.</label>
          <input
            type="date"
            id="datebirth"
            v-model="aluno.dataNasc"
            :min="minData"
            :max="maxData"
            required
          />
        </div>

        <div class="form-group">
          <label for="email">E-mail</label>
          <input type="email" id="email" v-model="aluno.email" required />
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
          <button type="submit" class="btn-primary">Cadastrar Aluno</button>
        </div>
      </form>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted } from "vue";

const aluno = ref({
  nome: "",
  dataNasc: "",
  turno: "",
  email: ""
});

const minData = ref("");
const maxData = ref("");

function validarNomeAluno() {
  aluno.value.nome = aluno.value.nome
    .toUpperCase()
    .replace(/[^A-Z\s]/g, "");
}

onMounted(() => {
  const hoje = new Date();
  const maxLimite = new Date(hoje.getFullYear() - 10, hoje.getMonth(), hoje.getDate());
  const minLimite = new Date(hoje.getFullYear() - 120, hoje.getMonth(), hoje.getDate());

  maxData.value = maxLimite.toISOString().split("T")[0];
  minData.value = minLimite.toISOString().split("T")[0];
});

async function cadastrarAluno() {
  const dataNasc = new Date(aluno.value.dataNasc);
  const hoje = new Date();
  const idadeMinima = new Date(hoje.getFullYear() - 10, hoje.getMonth(), hoje.getDate());
  const idadeMaxima = new Date(hoje.getFullYear() - 120, hoje.getMonth(), hoje.getDate());

  if (dataNasc > hoje) return alert("Data de nascimento inválida.");
  if (dataNasc > idadeMinima) {
    alert("O aluno deve ter pelo menos 10 anos.");
    return;
  }
  if (dataNasc < idadeMaxima) {
    alert("A idade máxima permitida é de 120 anos.");
    return;
  }

  const alunoData = {
    nome: aluno.value.nome,
    dataNasc: aluno.value.dataNasc,
    email: aluno.value.email,
    turno: aluno.value.turno
  };

  try {
    const response = await fetch("http://localhost:8081/api/aluno/cadastrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(alunoData)
    });

    if (response.ok) {
      alert("Aluno cadastrado com sucesso!");
      aluno.value = {
        nome: "",
        dataNasc: "",
        turno: "",
        email: ""
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
