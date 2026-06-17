# 🛒 E-Commerce API

API REST de um sistema de e-commerce desenvolvida com Java e Spring Boot.

O projeto foi criado com o objetivo de consolidar conhecimentos em desenvolvimento backend e servir como projeto de portfólio para vagas de Estágio e Desenvolvedor Júnior.

---

## 🚀 Tecnologias Utilizadas

- Java 25
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Lombok

---

## 📂 Arquitetura

O projeto segue uma arquitetura em camadas:

```
Controller
 ↓
Service
 ↓
Repository
 ↓
Database
```

Além disso, foi utilizada separação entre:

- DTOs de Request
- DTOs de Response
- DTOs de Entidade
- Mappers
- Entidades
- Configurações de Segurança

---

## 📌 Funcionalidades

### 👤 Usuários

- Cadastro de usuário
- Login com autenticação JWT
- Busca de usuário
- Atualização de dados

### 📦 Produtos

- Cadastro de produtos
- Atualização
- Remoção
- Busca por ID
- Listagem de produtos

### 🏷 Categorias

- Cadastro de categorias
- Atualização
- Exclusão
- Listagem

### 🛒 Carrinho

- Criar carrinho automaticamente para usuário
- Adicionar produtos
- Atualizar quantidade
- Remover itens
- Calcular total do carrinho

### 📍 Endereços

- Cadastro de endereços
- Definir endereço padrão
- Buscar endereços do usuário

### 💳 Métodos de pagamento

- Cadastro de método de pagamento
- Definir método padrão
- Listar métodos cadastrados

### 📦 Pedidos

- Criar pedido a partir do carrinho
- Buscar pedido por ID
- Listar pedidos do usuário
- Atualizar pedido
- Cancelar pedido

---

## 🔐 Segurança

A aplicação utiliza:

- Spring Security
- JWT (JSON Web Token)
- BCrypt Password Encoder
- Filtro de autenticação JWT
- Controle de acesso baseado em Roles

Roles disponíveis:

```java
ADMIN
CUSTOMER
```

---

## 📁 Estrutura do Projeto

```text
src/main/java/com/ighor/api/e_commerce

├── controller
├── service
├── repo
├── dto
│   ├── request
│   ├── response
│   └── entity
├── mapper
├── model
│   ├── entity
│   └── enums
├── security
└── exception
```

---

## ⚙️ Como executar

### Clone o projeto

```bash
git clone https://github.com/seu-usuario/e-commerce-api.git
```

### Entre na pasta

```bash
cd e-commerce-api
```

### Execute

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

## Banco de Dados

O projeto utiliza H2 Database para desenvolvimento.

Console H2:

```text
http://localhost:8080/h2-console
```

URL:

```text
jdbc:h2:mem:usuario
```

Usuário:

```text
sa
```

Senha:

```text
(sem senha)
```

---

## Próximos passos

- [ ] Melhorar Exception Handling
- [ ] Testes unitários com JUnit e Mockito
- [ ] Front-end em React
- [ ] Dockerização da aplicação
- [ ] Deploy

---

## Autor

**Ighor**

Estudante de Análise e Desenvolvimento de Sistemas.
