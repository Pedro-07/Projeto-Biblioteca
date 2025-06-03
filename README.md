# Projeto Biblioteca - Sistema de Gerenciamento Bibliotecário

Este projeto é um sistema distribuído desenvolvido com arquitetura de microsserviços para o gerenciamento de uma biblioteca. 
Ele permite cadastrar alunos, livros, realizar empréstimos e enviar notificações por e-mail.

## Tecnologias Utilizadas

- Backend: Java + Spring Boot
- Frontend: Vue.js
- Banco de Dados: MySQL
- Observabilidade: Prometheus + Grafana
- Orquestração: Docker Compose
- Integração externa: API Resend (e-mails)
- Documentação: Swagger (OpenAPI)

## Estrutura dos Serviços

- `aluno-service`: Gerencia alunos
- `livro-service`: Gerencia livros
- `emprestimo-service`: Realiza e controla empréstimos
- `frontend`: Interface em Vue.js
- `prometheus` e `grafana`: Monitoramento

## Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- Git

## Como Executar

1. Clone o repositório:

git clone https://github.com/Pedro-07/Projeto-Biblioteca.git
cd Projeto-Biblioteca
Construa e suba todos os serviços:
docker-compose up --build

Acesse os serviços no navegador:
Serviço	URL
Frontend	http://localhost:5174
Aluno API (Swagger)	http://localhost:8081/swagger-ui.html
Livro API (Swagger)	http://localhost:8082/swagger-ui.html
Empréstimo API (Swagger)	http://localhost:8083/swagger-ui.html
Grafana	http://localhost:3000
Prometheus	http://localhost:9090

As portas podem ser ajustadas no docker-compose.yml.

## Monitoramento
O serviço emprestimo-service expõe métricas para o Prometheus.

Grafana está configurado com dashboards para análise de desempenho.

Notificações por E-mail
O emprestimo-service envia e-mails de confirmação utilizando a API da Resend.

A chave da API deve ser gerada no site https://resend.com e configurada na variável de ambiente no application.properties.

Exemplos de Requisições
Você pode testar as APIs com ferramentas como Postman ou através do frontend.


POST http://localhost:8081/api/aluno/cadastrar
Content-Type: application/json

{
  "nome": "João da Silva",
  "dataNasc": "2001-08-22",
  "email": "joao@email.com",
  "turno": "matutino"
}

##Documentação
Cada serviço possui sua documentação Swagger acessível por navegador.
(http://localhost:8081/swagger-ui/index.html) mude apenas a porta, se preciso, para acessar outras docs.

##Observações
O sistema utiliza rede personalizada e volumes Docker para persistência de dados.

Os serviços se comunicam entre si via Feign Client.
