Sistema de Gerenciamento Bibliotecário com Microsserviços e Docker:

Este projeto consiste em uma aplicação distribuída para gerenciamento de uma biblioteca, desenvolvida com arquitetura de microsserviços utilizando Spring Boot. 
Os serviços são divididos em: Aluno, Livro e Empréstimo, cada um com seu próprio banco de dados MySQL. 
O sistema conta com frontend em Vue.js e é totalmente containerizado com Docker Compose, incluindo monitoramento com Prometheus e Grafana. 
A comunicação entre os serviços é feita via Feign Client e os dados são expostos por APIs RESTful.
