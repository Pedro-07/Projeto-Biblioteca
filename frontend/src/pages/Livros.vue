<template>
  <main class="main-content">
    <section id="books" class="section">
      <h2>Acervo de Livros</h2>

      <div class="header-container">
        <div class="search-bar">
          <input
            type="text"
            v-model="filtro"
            placeholder="Pesquisar por nome ou matrícula"
          />
        </div>
        <router-link to="/cadastrarLivro" class="btn-cadastrar">
          <span class="icon">+</span> Cadastrar novo Livro
        </router-link>
      </div>

      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Título</th>
              <th>Autor</th>
              <th>Editora</th>
              <th>ISBN</th>
              <th>Ano</th>
              <th>Disponíveis</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="livrosFiltrados.length === 0">
              <td colspan="7">Nenhum livro encontrado</td>
            </tr>
            <tr v-for="livro in livrosFiltrados" :key="livro.idLivro">
              <td>{{ livro.titulo || 'N/A' }}</td>
              <td>{{ livro.autor || 'N/A' }}</td>
              <td>{{ livro.editora || 'N/A' }}</td>
              <td>{{ livro.isbn || 'N/A' }}</td>
              <td>{{ livro.anoPubli || 'N/A' }}</td>
              <td :class="{ 'text-danger': livro.quantidade <= 0 }">
                {{ livro.quantidade ?? 'N/A' }}
              </td>
              <td class="acoes">
                <button class="btn-loan" @click="editarLivro(livro.idLivro)">Editar</button>
                <button class="btn-delete" @click="deletarLivro(livro.idLivro)">Deletar</button>
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
      livros: [],
      filtro: '',
      sugestoes: [],
      livroSelecionadoId: null
    };
  },
  computed: {
    livrosFiltrados() {
      if (this.livroSelecionadoId) {
        return this.livros.filter(livro => livro.idLivro === this.livroSelecionadoId);
      }

      const termo = this.filtro.toLowerCase();
      return this.livros.filter(livro =>
        livro.titulo?.toLowerCase().includes(termo) ||
        livro.autor?.toLowerCase().includes(termo) ||
        livro.isbn?.toLowerCase().includes(termo)
      );
    }
  },
  watch: {
    filtro(novo) {
      if (novo.length < 2) {
        this.sugestoes = [];
        return;
      }

      const termo = novo.toLowerCase();
      this.sugestoes = this.livros.filter(livro =>
        livro.titulo?.toLowerCase().includes(termo) ||
        livro.autor?.toLowerCase().includes(termo) ||
        livro.isbn?.toLowerCase().includes(termo)
      );
    }
  },
  methods: {
    selecionarLivro(livro) {
      this.filtro = `${livro.titulo} (${livro.autor})`;
      this.livroSelecionadoId = livro.idLivro;
      this.sugestoes = [];
    },
    editarLivro(id) {
      this.$router.push(`/editar-livro?id=${id}`);
    },
    async deletarLivro(id) {
      if (!confirm('Deseja realmente deletar este livro?')) return;
      try {
        const response = await fetch(`http://localhost:8082/api/livro/${id}`, {
          method: 'DELETE'
        });
        if (response.ok) {
          alert('Livro deletado com sucesso!');
          this.carregarLivros();
          this.livroSelecionadoId = null;
        } else {
          alert('Este livro está vinculado a empréstimos e não pode ser deletado.');
        }
      } catch (err) {
        alert('Erro ao deletar livro.');
      }
    },
    async carregarLivros() {
      try {
        const response = await fetch('http://localhost:8082/api/livro/listar');
        if (!response.ok) throw new Error('Erro ao carregar livros');
        this.livros = await response.json();
      } catch (err) {
        alert('Erro ao buscar livros: ' + err.message);
      }
    }
  },
  mounted() {
    this.carregarLivros();
  }
};
</script>


<style scoped>
.text-danger {
  color: red;
  font-weight: bold;
}
</style>
