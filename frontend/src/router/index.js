

import { createRouter, createWebHistory } from 'vue-router'

import Inicio from '../pages/Inicio.vue'
import Alunos from '../pages/Alunos.vue'
import CadastrarAluno from '../pages/CadastrarAluno.vue'
import EditarAluno from '../pages/EditarAluno.vue'
import Livros from '../pages/Livros.vue'
import CadastrarLivro from '../pages/CadastrarLivro.vue'
import EditarLivro from '../pages/EditarLivro.vue'
import Emprestimo from '../pages/Emprestimo.vue'
import NovoEmprestimo from '../pages/NovoEmprestimo.vue'


const routes = [
  {
    path: '/',
    name: 'Inicio',
    component: Inicio
  },
  {
    path: '/alunos',
    name: 'Alunos',
    component: Alunos
  },
  {
    path: '/cadastrarAluno',
    name: 'CadastrarAluno',
    component: CadastrarAluno
  },
  {
    path: '/editar-aluno',
    name: 'EditarAluno',
    component: EditarAluno
  },
  {
    path: '/livros',
    name: 'Livros',
    component: Livros
  },
  {
    path: '/cadastrarLivro',
    name: 'CadastrarLivro',
    component: CadastrarLivro
  },
  {
    path: '/editar-livro',
    name: 'EditarLivro',
    component: EditarLivro
  },
  {
    path: '/emprestimo',
    name: 'Emprestimo',
    component: Emprestimo
  },
  {
    path: '/novoEmprestimo',
    name: 'NovoEmprestimo',
    component: NovoEmprestimo
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
