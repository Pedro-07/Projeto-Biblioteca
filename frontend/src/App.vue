<template>
  <div :class="themeClass">
    <SideBarComponent />
    <main>
      <!-- O conteúdo das rotas será renderizado aqui -->
      <router-view />
    </main>
  </div>
</template>

<script>
import SideBarComponent from './components/SideBarComponent.vue';
import { ref } from 'vue';

export default {
  components: {
    SideBarComponent
  },
  setup() {
    const themeClass = ref('theme-default'); // Variável para controlar a classe do tema
    return { themeClass };
  }
}
</script>

<style scoped>
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
  background-color: var(--primary);
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
  background-color: var(--primary-dark);
}

.nav-link svg {
  width: 1.25rem;
  height: 1.25rem;
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
  background-color: var(--primary);
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

/* Cards Grid */
.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
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
  position: relative;
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


</style>