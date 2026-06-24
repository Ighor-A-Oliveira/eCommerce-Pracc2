# 🛒 E-Commerce API

Projeto backend de e-commerce construído para simular um sistema real de loja online, com autenticação JWT, controle de estoque e fluxo de pedidos completo.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Web
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Lombok
- Swagger / OpenAPI
- JUnit + Mockito (testes)

---

## 🧠 Objetivo do projeto

Este projeto foi desenvolvido com foco em aprendizado prático e portfólio, simulando um cenário real de backend para e-commerce, com:

- Arquitetura em camadas (Controller, Service, Repository)
- Uso de DTOs para entrada e saída de dados
- Validações com Bean Validation
- Segurança com Spring Security e JWT
- Testes unitários de Services com Mockito
- Documentação da API com Swagger

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
├── config
└── exception
```

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

- Criar carrinho automaticamente para o usuário
- Adicionar produtos
- Atualizar quantidade
- Remover itens
- Calcular total do carrinho

### 📍 Endereços

- Cadastro de endereços
- Definir endereço padrão
- Buscar endereços do usuário

### 💳 Métodos de pagamento

- Cadastro de método de pagamento (PIX e Cartão de Crédito)
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

```
ADMIN
CUSTOMER
```

---

## 🧪 Testes

O projeto possui testes unitários com:

- JUnit 5
- Mockito

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

## 🗄️ Banco de Dados

O projeto utiliza H2 Database (em memória) para desenvolvimento.

Console H2 disponível em:

```
http://localhost:8080/h2-console
```

| Campo | Valor            |
|-------|------------------|
| URL   | jdbc:h2:mem:usuario |
| Usuário | sa             |
| Senha | (sem senha)      |

---

## 📖 Documentação da API

A documentação interativa via Swagger está disponível após subir a aplicação:

```
http://localhost:8080/swagger-ui.html
```

---

## 🔧 Como usar a API (Postman / Insomnia)

Base URL (local): `http://localhost:8080`

> ⚠️ A maioria dos endpoints requer autenticação. Após o login, envie o token JWT no header:
> `Authorization: Bearer {seu_token}`

---

### 👤 Usuário

**Registrar usuário**
```
POST /user/register
```
```json
{
  "name": "ighor",
  "email": "ighor@email.com",
  "password": "123",
  "phone": "1234"
}
```

**Login**
```
POST /user/login
```
```json
{
  "email": "ighor@email.com",
  "password": "123"
}
```

---

### 🗂️ Categorias

**Criar categoria**
```
POST /category
```
```json
{
  "name": "Movies",
  "slug": "movies",
  "description": "Products related to movies, DVDs, Blu-ray discs, etc"
}
```

---

### 📦 Produtos

**Criar produto**
```
POST /product
```
```json
{
  "name": "Blu-ray",
  "description": "A disc that contains a movie",
  "price": 100.50,
  "stockQuantity": 50,
  "imageUrl": "https://example.com/image.jpg",
  "sku": "brd01",
  "active": true,
  "categoryId": 1
}
```

---

### 🏠 Endereços

**Adicionar endereço**
```
POST /address
```
```json
{
  "street": "Rua Usuario",
  "number": "24",
  "complement": "",
  "neighborhood": "Vila do Usuario",
  "city": "Sao Paulo",
  "state": "SP",
  "zipCode": "00010-000",
  "userId": 1
}
```

---

### 💳 Métodos de pagamento

**PIX**
```
POST /payment-method/1
```
```json
{
  "method": "PIX",
  "isDefault": true
}
```

**Cartão de crédito**
```
POST /payment-method/1
```
```json
{
  "method": "CREDIT_CARD",
  "cardHolderName": "Ighor Alves",
  "lastFourDigits": "1234",
  "expiryMonth": "12",
  "expiryYear": "2030",
  "token": "fake-token",
  "isDefault": true
}
```

---

### 🛒 Carrinho

**Adicionar item ao carrinho**
```
POST /cart/1/items
```
```json
{
  "productId": 1,
  "quantity": 2
}
```

---

### 📑 Pedidos

**Criar pedido**
```
POST /order
```
```json
{
  "addressId": 1,
  "paymentId": 1,
  "userId": 1
}
```

**Buscar pedidos do usuário**
```
GET /order/user/1
```

---

## 🔜 Próximos passos

- [ ] Expandir cobertura de testes unitários
- [ ] Criar testes de integração
- [ ] Front-end em React
- [ ] Dockerização da aplicação
- [ ] Deploy

---

## 👤 Autor

**Ighor**

Estudante de Análise e Desenvolvimento de Sistemas.
