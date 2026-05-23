# 🥋 Centro de Treinamento BJJ API

API REST desenvolvida em Java para gerenciamento de um centro de treinamento de Brazilian Jiu-Jitsu (BJJ), permitindo o cadastro e gerenciamento de alunos.

---

# 🚀 Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* JUnit 5
* Mockito
* Docker
* Docker Compose

---

# 📂 Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   └── com.centrotreinamento.bjj
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── repository
│   │       ├── service
│   │       └── exception
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── com.centrotreinamento.bjj
```

---

# ⚙️ Funcionalidades

* ✅ Cadastro de alunos
* ✅ Busca de aluno por ID
* ✅ Listagem de alunos
* ✅ Atualização de dados
* ✅ Remoção de alunos
* ✅ Validações de dados
* ✅ Tratamento global de exceções
* ✅ Testes unitários

---

# 📌 Endpoints

## Alunos

| Método | Endpoint       | Descrição             |
| ------ | -------------- | --------------------- |
| POST   | `/alunos`      | Cadastra um aluno     |
| GET    | `/alunos`      | Lista todos os alunos |
| GET    | `/alunos/{id}` | Busca aluno por ID    |
| PUT    | `/alunos/{id}` | Atualiza um aluno     |
| DELETE | `/alunos/{id}` | Remove um aluno       |

---

# ▶️ Como Executar o Projeto

## Pré-requisitos

* Java 21+
* Maven
* Docker e Docker Compose

---

## Clonando o repositório

```bash
git clone https://github.com/AniltonViganigoJr/centro-treinamento-bjj.git
```

```bash
cd centro-treinamento-bjj
```

---

## Executando com Docker

```bash
docker-compose up --build
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## Executando localmente

### 1. Configure o banco PostgreSQL

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bjj
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 2. Execute a aplicação

```bash
./mvnw spring-boot:run
```

Ou:

```bash
mvn spring-boot:run
```

---

# 🧪 Executando os Testes

```bash
mvn test
```

---

# 📖 Exemplo de Requisição

## POST `/alunos`

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "faixa": "Azul"
}
```

---

# 🛠️ Melhorias Futuras

* [ ] Autenticação com JWT
* [ ] Swagger/OpenAPI
* [ ] Paginação
* [ ] Deploy em nuvem
* [ ] CI/CD
* [ ] Cadastro de professores
* [ ] Cadastro de turmas
* [ ] Controle de mensalidades

---

# 👨‍💻 Autor

Desenvolvido por Anilton Viganigo Jr.

Projeto disponível em:

[https://github.com/AniltonViganigoJr/centro-treinamento-bjj](https://github.com/AniltonViganigoJr/centro-treinamento-bjj)

---

# 📄 Licença

Este projeto está sob a licença MIT.
