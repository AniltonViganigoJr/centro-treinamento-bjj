Aqui está o conteúdo completo em um único bloco Markdown:

````md
# 🥋 Centro de Treinamento BJJ API

API REST desenvolvida com Java e Spring Boot para gerenciamento de alunos e treinos de um centro de treinamento de Brazilian Jiu-Jitsu (BJJ).

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- JUnit 5
- Mockito
- Swagger / OpenAPI
- Maven

---

## 📌 Funcionalidades

### 👨‍🎓 Alunos
- Cadastro de alunos
- Busca de alunos por ID
- Listagem de alunos
- Atualização de dados
- Ativação e desativação de matrícula
- Controle de faixa e graus
- Métricas de treino:
  - Total de treinos
  - Quantidade semanal
  - Quantidade mensal

### 🥋 Treinos
- Registro de treinos
- Busca de treino por ID
- Listagem de treinos
- Remoção de treinos
- Associação de treinos a alunos

---

## 📂 Estrutura do Projeto

```bash
src
├── controller
├── service
├── repository
├── domain
├── dto
├── mapper
├── config
└── tests
````

---

## ⚙️ Configuração do Ambiente

### Pré-requisitos

* Java 21+
* Maven
* Docker
* Docker Compose

---

## 🐳 Executando com Docker

### Subir aplicação + banco PostgreSQL

```bash
docker compose up --build -d
```

### Parar containers

```bash
docker compose down
```

---

## ▶️ Executando localmente

### 1. Clone o repositório

```bash
git clone https://github.com/AniltonViganigoJr/centro-treinamento-bjj.git
```

### 2. Entre na pasta do projeto

```bash
cd centro-treinamento-bjj
```

### 3. Configure as variáveis de ambiente

Exemplo:

```properties
DB_URL=jdbc:postgresql://localhost:5432/bjj
DB_USER=postgres
DB_PASSWORD=postgres
```

---

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

---

## 🧪 Executando os testes

```bash
./mvnw test
```

---

## 📖 Documentação da API

Após subir a aplicação:

```bash
http://localhost:8080/swagger-ui/index.html
```

---

## 📌 Exemplos de Endpoints

### Criar aluno

```http
POST /alunos
```

### Listar alunos

```http
GET /alunos
```

### Registrar treino

```http
POST /treinos
```

### Buscar treino por ID

```http
GET /treinos/{id}
```

---

## 📈 Melhorias futuras

* Autenticação com JWT
* Controle de permissões
* Paginação
* Filtros de busca
* Relatórios de desempenho
* Deploy em cloud
* CI/CD com GitHub Actions

---

## 👨‍💻 Autor

Desenvolvido por Júnior Viganigo.

* GitHub: [https://github.com/AniltonViganigoJr](https://github.com/AniltonViganigoJr)
* LinkedIn: adicionar-linkedin-aqui

```
```
