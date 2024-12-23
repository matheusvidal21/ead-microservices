[![Finalizado](https://img.shields.io/badge/Status-Conclu%C3%ADdo-brightgreen)](https://github.com/matheusvidal21/ead-microservices)

<h1 align="center">🚀 EAD Microservices</h1>

<p align='center'> 
    <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot"/>
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>  
    <img src="https://img.shields.io/badge/JWT-F2F4F9?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=black"/>
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"/>
    <img src="https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white"/>
    <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white"/>
</p>    

<p align="center">
  <img src="https://github.com/user-attachments/assets/bf7c6b5e-00a9-4539-850f-1e1e81cdc96a" alt="Logo DSCommerce" height="600">
</p>

Bem-vindo ao repositório do **EAD Microservices**! Este projeto foi desenvolvido como parte de uma formação em microservices com Java e Spring e tem como objetivo demonstrar uma arquitetura robusta e escalável utilizando padrões e ferramentas modernas. 

Para isso, foi construído um projeto que simula uma plataforma de cursos EAD (Educação a Distância), onde instrutores podem criar cursos e módulos, e alunos podem se cadastrar e se inscrever nos cursos disponíveis.

---

## 🏗️ Arquitetura de Microservices

Este projeto segue uma arquitetura de microservices, dividindo a aplicação em vários serviços independentes que se comunicam entre si. Cada microservice é responsável por uma funcionalidade específica e pode ser escalado e implantado separadamente.

### **Microservices Principais**

1. **AuthUser 🔐**:
   - Gerencia a autenticação e autorização de usuários.
   - Implementa **Spring Security** com suporte para JWT.

2. **Course 📚**:
   - Responsável por gerenciar cursos, módulos, lições e inscrições.

3. **Notification 📣**:
   - Lida com notificações e alertas para os usuários.

---

## 🛠️ Tecnologias Utilizadas

### **Linguagem e Frameworks**
- **Java 17** ☕
- **Spring Boot 3.1.0** 🌱:
  - Spring Web (APIs RESTful)
  - Spring Data JPA (Banco de dados)
  - Spring Security (Autenticação/Autorização)
  - Spring Cloud (Padrões de microsserviços)

### **Banco de Dados**
- **PostgreSQL** 🗃️
- Docker para provisionamento do banco de dados.

### **Mensageria**
- **RabbitMQ** 🐇:
  - Comunicação assíncrona entre microservices.

### **Outras Ferramentas**
- **Spring Cloud Netflix Eureka** 📡: Service Registry.
- **Spring Cloud Config Server** 📜: Gerenciamento centralizado de configurações para todos os microservices.
- **Resilience4j** 🔄: Implementação de Circuit Breaker e Retry.
- **Orika Mapper** 🗺️: Ferramenta de mapeamento de objetos Java para conversões rápidas e eficientes entre DTOs e entidades.
- **Lombok** ✂️: Redução de verbosidade no código.

---

## 📂 Estrutura do Repositório

- **`api-gateway`**: Microservice que atua como o ponto único de entrada da aplicação, roteando requisições para os serviços internos.
  
- **`config-server`**: Fornece gerenciamento centralizado de configurações.

   **`service-registry`**: Registro de serviços com Eureka.
  
- **`authuser`**: Gerencia autenticação, autorização e usuários.
  
- **`course`**: Serviço responsável por cursos, módulos e lições.
  
- **`notification`**: Serviço que gerencia as notificações.
  
- **`notification-hex`**: Serviço de notificações com arquitetura hexagonal.
  
- **`docker-compose.yml`**: Provisiona banco de dados necessários para cada serviço.
  
- **`init-db.sh`**: Script para inicializar o banco de dados.

---

## 🚦 Funcionalidades Implementadas

### **Comunicação e Padrões**

#### **API Gateway Pattern**
- Utilizado para roteamento centralizado de requisições.
- Implementado com Spring Cloud Gateway e Service Registry.

#### **Service Registry Pattern**
- Gerenciado com Eureka Server.
- Permite a descoberta dinâmica de serviços.

#### **Config Server**
- Gerenciamento centralizado de configurações em um repositório git.
- Atualização em tempo de execução com Spring Cloud Config.

#### **API Composition Pattern**
- Comunicação síncrona entre microservices para compor dados de múltiplos serviços.

### **Comunicação Síncrona**
- Inscrição de usuários em cursos.
- Deleção de dados com integração entre microservices.

### **Comunicação Assíncrona**
- **Mensageria com RabbitMQ**:
  - Eventos de notificação entre serviços.
- **Event-Carried State Transfer**:
  - Transferência de estado por meio de eventos.

### **Confiabilidade**
- Implementação do **Circuit Breaker Pattern** com Resilience4j para melhorar a resiliência de falhas.

### **Autenticação e Autorização**
- **JWT (Json Web Token)**:
  - Autorização baseada em papéis (Roles).
  - Integração de autenticação entre microservices.

---

## ⚙️ Configuração e Execução

### **Requisitos**
- JDK 17
- Maven
- Docker

### **Instalação**

1. Clone o repositório:
```bash
git clone git@github.com:matheusvidal21/ead-microservices.git
cd ead-microservices
```

2. Inicie containers para criar os bancos de dados:
```bash
docker-compose up -d
```

3. Inicie os microsserviços principais:
  - service-registry
  - config-server
  - api-gateway

4. Inicie os demais microsserviços:
  - auth-user
  - courses
  - notification

# 👥 Autor

| [<img src="https://avatars.githubusercontent.com/u/102569695?s=400&u=f20bbb53cc46ec2bae01f8d60a28492bfdccbdd5&v=4" width=115><br><sub>Matheus Vidal</sub>](https://github.com/matheusvidal21) |
| :---: |
