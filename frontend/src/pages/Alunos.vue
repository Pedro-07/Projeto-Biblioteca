<template>
  <main class="main-content">
    <section id="pending" class="section">
      <h2>Alunos cadastrados</h2>

      <div class="header-container">
        <div class="search-bar">
          <input type="text" v-model="filtro" placeholder="Pesquisar por nome ou matrícula" />
        </div>
        <router-link to="/cadastrarAluno" class="btn-cadastrar">
          <span class="icon">+</span> Cadastrar novo aluno
        </router-link>
      </div>

      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Matrícula</th>
              <th>Nome do Aluno</th>
              <th>Data de nascimento</th>
              <th>Turno</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="aluno in alunosFiltrados" :key="aluno.id">
              <td>{{ aluno.matricula }}</td>
              <td>{{ aluno.nome }}</td>
              <td>{{ aluno.dataNasc }}</td>
              <td>{{ aluno.turno }}</td>
              <td class="acoes">
                <button class="btn-loan" @click="editarAluno(aluno.id)">
                  Editar
                </button>
                <button class="btn-delete" @click="deletarAluno(aluno.id)">
                  Deletar
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </main>
</template>

<script>

export default {
  data() {
    return {
      alunos: [],
      filtro: "",
      sugestoes: [],
      alunoSelecionadoId: null,
    };
  },

  computed: {
    alunosFiltrados() {
      if (this.alunoSelecionadoId) {
        return this.alunos.filter(
          (aluno) => aluno.id === this.alunoSelecionadoId
        );
      }

      const termo = this.filtro.toLowerCase();
      return this.alunos.filter(
        (aluno) =>
          aluno.nome.toLowerCase().includes(termo) ||
          aluno.matricula.toString().includes(termo)
      );
    },
  },

  watch: {
    filtro(novo) {
      if (novo.length < 2) {
        this.sugestoes = [];
        return;
      }

      const termo = novo.toLowerCase();
      this.sugestoes = this.alunos.filter(
        (aluno) =>
          aluno.nome.toLowerCase().includes(termo) ||
          aluno.matricula.toString().includes(termo)
      );
    },
  },

  methods: {
    navegarParaCadastro() {
      this.$router.push('/cadastrar-aluno');
    },

    selecionarAluno(aluno) {
      this.filtro = `${aluno.nome} (${aluno.matricula})`;
      this.alunoSelecionadoId = aluno.id;
      this.sugestoes = [];
    },

    editarAluno(id) {
      this.$router.push(`/editar-aluno?id=${id}`);
    },

    async deletarAluno(id) {
      if (!confirm("Deseja realmente deletar este Aluno?")) return;
      try {
        const response = await fetch(`http://localhost:8081/api/aluno/${id}`, {
          method: "DELETE",
        });
        if (response.ok) {
          alert("Aluno deletado com sucesso!");
          this.carregarAlunos();
          this.alunoSelecionadoId = null;
        } else {
          const erro = await response.text();
          alert(
            "Este Aluno está vinculado a empréstimos e não pode ser deletado."
          );
        }
      } catch (err) {
        console.error("Erro ao deletar Aluno:", err);
        alert("Erro inesperado.");
      }
    },

    async carregarAlunos() {
      try {
        const response = await fetch('http://localhost:8081/api/aluno/listar');
        if (!response.ok) throw new Error('Erro ao carregar Alunos');
        this.alunos = await response.json();
      } catch (err) {
        alert('Erro ao buscar Alunos: ' + err.message);
      }
    }
  },

  mounted() {
    this.carregarAlunos();
  }
};
</script>

<style>
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.btn-cadastrar {
  text-decoration: none;
  background-color: #2563eb;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  white-space: nowrap;
  display: flex;
  align-items: center;
  margin-bottom: 23px;
}

.btn-cadastrar:hover {
  background-color: #1d4ed8;
}

.btn-cadastrar .icon {
  margin-right: 6px;
  font-size: 18px;
  font-weight: bold;
}

.search-bar {
  width: 800px;
  position: relative;
}

.search-bar input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
}

.table-container {
  width: 100%;
  overflow-x: auto;
  margin-top: 8px;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th {
  background-color: #f3f4f6;
  text-align: left;
  padding: 12px 16px;
  font-weight: 600;
  color: #374151;
}

td {
  padding: 12px 16px;
  border-bottom: 1px solid #e5e7eb;
}

.acoes {
  display: flex;
  gap: 8px;
}

.btn-loan {
  background-color: #2563eb;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 6px 12px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-loan:hover {
  background-color: #1d4ed8;
}

.btn-delete {
  background-color: #dc2626;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 6px 12px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-delete:hover {
  background-color: #b91c1c;
}

.autocomplete-suggestions {
  position: absolute;
  background: white;
  border: 1px solid #ccc;
  z-index: 999;
  width: 100%;
  max-height: 200px;
  overflow-y: auto;
  border-radius: 6px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.autocomplete-suggestions div {
  padding: 8px 12px;
  cursor: pointer;
}

.autocomplete-suggestions div:hover {
  background-color: #f3f4f6;
}

tr.destacado {
  border: 2px solid #2563eb;
  background-color: #eff6ff;
}

@media (max-width: 768px) {
  .header-container {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .search-bar {
    width: 100%;
  }
}
</style>