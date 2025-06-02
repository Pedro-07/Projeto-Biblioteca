<template>
    <button @click="toggleTheme" class="theme-toggle">
      {{ theme === 'dark' ? 'Modo Claro' : 'Modo Escuro' }}
    </button>
  </template>
  
  <script>
  export default {
    data() {
      return {
        theme: localStorage.getItem('theme') || 'light', // Obtém o tema armazenado ou 'light' como padrão
      };
    },
    methods: {
      toggleTheme() {
        this.theme = this.theme === 'dark' ? 'light' : 'dark';
        localStorage.setItem('theme', this.theme); // Armazena a escolha do tema
        this.applyTheme();
      },
      applyTheme() {
        if (this.theme === 'dark') {
          document.documentElement.classList.add('dark'); // Adiciona classe 'dark' ao HTML
          document.documentElement.classList.remove('light');
        } else {
          document.documentElement.classList.add('light');
          document.documentElement.classList.remove('dark');
        }
      }
    },
    mounted() {
      this.applyTheme(); // Aplica o tema ao montar o componente
    }
  };
  </script>
  
  <style scoped>
  .theme-toggle {
    padding: 10px;
    font-size: 14px;
    cursor: pointer;
    background-color: #ccc;
    border: none;
    border-radius: 5px;
  }
  
  .theme-toggle:hover {
    background-color: #999;
  }
  </style>
  