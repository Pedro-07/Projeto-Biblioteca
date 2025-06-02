<template>
  <main class="main-content">
    <section class="section">
      <h2>Empréstimos Registrados</h2>

      <div class="header-container">
        <div class="search-bar">
          <input
            type="text"
            v-model="filtro"
            placeholder="Pesquisar por nome ou matrícula"
          />
        </div>
        <router-link to="/novoEmprestimo" class="btn-cadastrar">
          <span class="icon">+</span> Novo Empréstmo
        </router-link>
      </div>

      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Aluno</th>
              <th>Matrícula</th>
              <th>Livro</th>
              <th>ISBN</th>
              <th>Data Empréstimo</th>
              <th>Data Devolução</th>
              <th>Status</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="emprestimosFiltrados.length === 0">
              <td colspan="8">Nenhum empréstimo encontrado.</td>
            </tr>
            <tr v-for="emp in emprestimosFiltrados" :key="emp.id">
              <td>{{ emp.alunoNome || 'N/A' }}</td>
              <td>{{ emp.alunoMatricula || 'N/A' }}</td>
              <td>{{ emp.livroTitulo || 'N/A' }}</td>
              <td>{{ emp.livroIsbn || 'N/A' }}</td>
              <td>{{ formatarData(emp.dataEmprestimo) }}</td>
              <td>{{ formatarData(emp.dataDevolucao) }}</td>
              <td>
                <span class="status-badge" :class="statusClass(emp.status)">
                  {{ traduzirStatus(emp.status) }}
                </span>
              </td>
              <td>
                <button class="btn-delete" @click="deletarEmprestimo(emp.id)">
                  Excluir
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
      emprestimos: [],
      filtro: ''
    };
  },
  mounted() {
    this.carregarEmprestimos();
  },
  computed: {
    emprestimosFiltrados() {
      const termo = this.filtro.toLowerCase();
      return this.emprestimos.filter(e =>
        (e.alunoNome || '').toLowerCase().includes(termo) ||
        (e.livroTitulo || '').toLowerCase().includes(termo)
      );
    }
  },
  methods: {
    async carregarEmprestimos() {
      try {
        const response = await fetch('http://localhost:8083/api/emprestimo/listar');
        if (!response.ok) throw new Error('Erro ao carregar empréstimos');
        this.emprestimos = await response.json();
      } catch (error) {
        console.error('Erro ao carregar empréstimos:', error.message);
        this.emprestimos = [];
      }
    },

    formatarData(dataISO) {
      if (!dataISO) return '';
      const data = new Date(dataISO);
      return data.toLocaleDateString('pt-BR');
    },

    traduzirStatus(status) {
      switch ((status || '').toUpperCase()) {
        case 'ATIVO':
          return 'Ativo';
        case 'ATRASADO':
          return 'Atrasado';
        case 'FINALIZADO':
          return 'Finalizado';
        default:
          return status || 'N/A';
      }
    },

    statusClass(status) {
      return (status || '').toUpperCase() === 'ATRASADO' ? 'atrasado' : 'success';
    },

    async deletarEmprestimo(id) {
      if (!confirm('Tem certeza que deseja excluir este empréstimo?')) return;
      try {
        const response = await fetch(`http://localhost:8083/api/emprestimo/${id}`, {
          method: 'DELETE',
        });
        if (!response.ok) throw new Error(await response.text());
        alert('Empréstimo deletado com sucesso!');
        this.carregarEmprestimos();
      } catch (error) {
        console.error('Erro ao deletar empréstimo:', error.message);
        alert(`Erro ao deletar empréstimo: ${error.message}`);
      }
    }
  }
};
</script>
