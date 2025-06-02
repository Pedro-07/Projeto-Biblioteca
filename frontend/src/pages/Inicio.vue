<template>
  <main class="main-content">
    <div class="content-wrapper">
      <div class="hero">
        <div class="hero-overlay">
          <h1>Sistema de Gerenciamento de Biblioteca</h1>
        </div>
      </div>

      <div class="cards-grid">
        <router-link to="/alunos" class="card-link">
          <div class="card">
            <div class="card-header">
              <i class="fas fa-user-graduate fa-2x"></i>
              <h3>Alunos</h3>
            </div>
            <p>Gerencie cadastros e informações dos alunos</p>
          </div>
        </router-link>

        <router-link to="/livros" class="card-link">
          <div class="card">
            <div class="card-header">
              <i class="fas fa-book fa-2x"></i>
              <h3>Livros</h3>
            </div>
            <p>Controle o acervo da biblioteca</p>
          </div>
        </router-link>

        <router-link to="/emprestimo" class="card-link">
          <div class="card">
            <div class="card-header">
              <i class="fas fa-exchange-alt fa-sm"></i>
              <h3>Empréstimos</h3>
            </div>
            <p>Gerencie empréstimos e devoluções</p>
          </div>
        </router-link>
      </div>

    </div>
  </main>
</template>


<script>
export default {
  data() {
    return {
      stats: {
        livrosDisponiveis: 0,
        emprestimosAtivos: 0,
        alunosCadastrados: 0,
      },
      isLoading: false,
      error: null,
    }
  }
}

 
</script>

<!--
<script>
export default {
  data() {
    return {
      sidebarAberta: false, // inicialmente recolhida
    };
  },
  methods: {
    toggleSidebar() {
      this.sidebarAberta = !this.sidebarAberta;
    },
  },
};
</script>
-->



<style>
:root {
  --primary: #2563eb;
  --primary-dark: #1d4ed8;
  --danger: #dc2626;
  --success: #16a34a;
  --warning: #ca8a04;
  --gray-50: #f9fafb;
  --gray-100: #f3f4f6;
  --gray-200: #e5e7eb;
  --gray-300: #d1d5db;
  --gray-400: #9ca3af;
  --gray-500: #6b7280;
  --gray-600: #4b5563;
  --gray-700: #374151;
  --gray-800: #1f2937;
  --gray-900: #111827;
}

/* Reset and base styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: system-ui, -apple-system, sans-serif;
  background-color: #f3f4f6;
  color: #1f2937;
}

/* Layout */
.layout {
  display: flex;
  min-height: 100vh;
}

/* Sidebar */
.sidebar {
  width: 256px;
  background-color: #2563eb;
  color: white;
  padding: 1.5rem;
  position: fixed;
  height: 100vh;
}

.sidebar-header {
  margin-bottom: 2rem;
}

.sidebar-header h1 {
  font-size: 1.25rem;
  font-weight: bold;
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

/* Navigation items */
.nav-item {
  position: relative;
}

.nav-link a {
  color: #fff;
  text-decoration: none;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.nav-link:hover {
  background-color: #1d4ed8;
}

.nav-link svg {
  width: 1.25rem;
  height: 1.25rem;
}

.nav-link,
.router-link {
  color: #fff;
  text-decoration: none;
}

.nav-link i {
  margin-right: 8px;
  color: #fff;
  /* ou outra cor */
}


/* Dropdown */
.dropdown {
  display: none;
  background-color: #1e40af;
  border-radius: 0.5rem;
  margin-top: 0.25rem;
  width: 100%;
}

.nav-item:hover .dropdown {
  display: block;
}

.dropdown-item {
  display: block;
  padding: 0.5rem 1rem;
  color: white;
  text-decoration: none;
  transition: background-color 0.2s;
}

.dropdown-item:hover {
  background-color: #1e40af;
}

/* Main Content */
.main-content {
  flex: 1;
  margin-left: 256px;
  padding: 2rem;
}

.content-wrapper {
  max-width: 72rem;
  margin: 0 auto;
}

/* Hero Section */
.hero {
  position: relative;
  height: 16rem;
  border-radius: 0.75rem;
  overflow: hidden;
  margin-bottom: 2rem;
}

.hero img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background-color: #2563eb;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hero-overlay h1 {
  color: white;
  font-size: 2.25rem;
  font-weight: bold;
  text-align: center;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;

}

.card-header i {
  color: #000810;
  font-size: 20px;
}

/* Cards Grid */
.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.card-link {
  text-decoration: none;
  color: inherit;
  display: block;
}

.card {
  background-color: white;
  padding: 1.5rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.2s;
}

.card:hover {
  transform: translateY(-0.25rem);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.card-header svg {
  color: #2563eb;
}

.card-header h3 {
  font-size: 1.25rem;
  font-weight: 600;
}

.card p {
  color: #4b5563;
}

/* Stats Section */
.stats-section {
  background-color: white;
  padding: 1.5rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.stats-section h2 {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.stat-card {
  background-color: #eff6ff;
  padding: 1rem;
  border-radius: 0.5rem;
}

.stat-value {
  font-size: 1.25rem;
  font-weight: 600;
  color: #2563eb;
}

.stat-label {
  color: #4b5563;
}

/* Search Bar */
.search-bar {
  position: relative;
  margin-bottom: 1.5rem;
}

.search-bar svg {
  position: absolute;
  left: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--gray-400);
}

.search-bar input {
  width: 100%;
  padding: 0.75rem 1rem 0.75rem 2.5rem;
  border: 1px solid var(--gray-300);
  border-radius: 0.375rem;
  font-size: 1rem;
}

.search-bar input:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

/* Table */
.table-container {
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th {
  background-color: var(--gray-50);
  padding: 0.75rem 1.5rem;
  text-align: left;
  font-size: 0.75rem;
  font-weight: 500;
  text-transform: uppercase;
  color: var(--gray-500);
  border-bottom: 1px solid var(--gray-200);
}

td {
  padding: 1rem 1.5rem;
  border-bottom: 1px solid var(--gray-200);
  color: var(--gray-700);
}

.actions {
  display: flex;
  gap: 0.5rem;
}

/* Buttons */
.btn-primary {
  background-color: var(--primary);
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 0.375rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-primary:hover {
  background-color: var(--primary-dark);
}

.btn-loan {
  background-color: var(--primary);
  color: white;
  padding: 0.25rem 0.75rem;
  border: none;
  border-radius: 0.25rem;
  font-size: 0.875rem;
  cursor: pointer;
}

.btn-delete {
  background-color: var(--danger);
  color: white;
  padding: 0.25rem 0.75rem;
  border: none;
  border-radius: 0.25rem;
  font-size: 0.875rem;
  cursor: pointer;
}

.btn-return {
  background-color: var(--primary);
  color: white;
  padding: 0.25rem 0.75rem;
  border: none;
  border-radius: 0.25rem;
  font-size: 0.875rem;
  cursor: pointer;
}

.status-badge {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 500;
  color: white;
}

.status-badge.success {
  background-color: var(--success);
}

.status-badge.danger {
  background-color: var(--danger);
}

.status-badge.warning {
  background-color: var(--warning);
}

.status-badge.atrasado {
  background-color: var(--danger);
}

/* Status Badge 
.status-badge {
    display: inline-block;
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
    background-color: var(--success);
    color: white;
}

.status-badge.atrasado {
    background-color: var(--danger);
}

.status-badge.success {
    background-color: green;
}

.status-badge.danger {
    background-color: red;
}

.status-badge.warning {
    background-color: orange;
}

.status-badge.info {
    background-color: steelblue;
}

.status-badge.neutral {
    background-color: gray;
}
*/

/* Form */
.form {
  background-color: white;
  padding: 1.5rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  max-width: 32rem;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  color: var(--gray-700);
  margin-bottom: 0.5rem;
}

.form-control {
  width: 100%;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--gray-300);
  border-radius: 0.375rem;
  font-size: 1rem;
}

.form-group input {
  width: 100%;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--gray-300);
  border-radius: 0.375rem;
  font-size: 1rem;
}

.form-group input:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

.form-group textarea {
  width: 100%;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--gray-300);
  border-radius: 0.375rem;
  font-size: 1rem;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
}

.autocomplete-suggestions {
  position: absolute;
  background: #ccc;
  border: 1px solid white;
  max-height: 200px;
  overflow-y: auto;
  z-index: 1000;
  width: 24%;
}

.autocomplete-suggestions div {
  padding: 8px;
  cursor: pointer;
}

.autocomplete-suggestions div:hover {
  background-color: #eee;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .sidebar {
    width: 100%;
    height: auto;
    position: relative;
  }

  .main-content {
    margin-left: 0;
  }

  .layout {
    flex-direction: column;
  }

  .hero-overlay h1 {
    font-size: 1.75rem;
    padding: 0 1rem;
  }
}
</style>