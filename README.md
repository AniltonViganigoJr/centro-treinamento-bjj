# 🥋 Centro de Treinamento BJJ API

API REST desenvolvida com Java e Spring Boot para gerenciamento de alunos e treinos de um centro de treinamento de Jiu-Jitsu Brasileiro (BJJ).

O projeto foi criado com foco em boas práticas de desenvolvimento backend, arquitetura em camadas, validações, testes automatizados e conteinerização com Docker.

---

# 🚀 Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Docker & Docker Compose
* JUnit 5
* Mockito
* Maven
* Swagger / OpenAPI

---

# 📚 Funcionalidades

## 👨‍🎓 Alunos

* Cadastro de alunos
* Busca de alunos por ID
* Listagem de alunos
* Atualização de dados
* Remoção de alunos
* Métricas de treinos do aluno

## 🥋 Treinos

* Cadastro de treinos
* Associação de treino ao aluno
* Controle de:

  * Tipo de treino
  * Duração
  * Descrição
  * Data

---

# 🏗️ Estrutura do Projeto

```bash
src
 ┣ main
 ┃ ┣ java
 ┃ ┃ ┗ com.centrotreinamento.bjj
 ┃ ┃   ┣ controller
 ┃ ┃   ┣ domain
 ┃ ┃   ┣ dto
 ┃ ┃   ┣ mapper
 ┃ ┃   ┣ repository
 ┃ ┃   ┣ service
 ┃ ┃   ┗ exception
 ┃ ┗ resources
 ┃   ┗ application.properties
 ┗ test
```

---

# ⚙️ Como executar o projeto

## ✅ Pré-requisitos

* Java 21+
* Docker
* Docker Compose

---

# 🐳 Executando com Docker

## 1. Clone o repositório

```bash
git clone https://github.com/AniltonViganigoJr/centro-treinamento-bjj.git
```

## 2. Acesse a pasta do projeto

```bash
cd centro-treinamento-bjj
```

## 3. Suba os containers

```bash
docker compose up --build
```

Para executar em background:

```bash
docker compose up -d --build
```

---

# 🗄️ Banco de Dados

O projeto utiliza PostgreSQL.

As variáveis de ambiente são configuradas no `docker-compose.yml`.

Exemplo:

```yaml
environment:
  DB_URL: jdbc:postgresql://postgres:5432/bjj
  DB_USER: postgres
  DB_PASSWORD: postgres
```

---

# 🔧 Configuração local sem Docker

Configure o arquivo `application.properties`:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/bjj}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
```

---

# 📄 Documentação da API

Após iniciar a aplicação, acesse:

```bash
http://localhost:8080/swagger-ui.html
```

ou

```bash
http://localhost:8080/swagger-ui/index.html
```

---

# 🧪 Executando os testes

```bash
./mvnw test
```

ou

```bash
mvn test
```

---

# 📌 Exemplo de requisição

## Criar aluno

```http
POST /alunos
```

### Body

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "faixa": "BRANCA"
}
```

---

# ✅ Boas práticas aplicadas

* Arquitetura em camadas
* DTOs para entrada e saída
* Mapper pattern
* Tratamento global de exceções
* Testes unitários
* Separação de responsabilidades
* Uso de variáveis de ambiente
* Conteinerização da aplicação

---

# 📈 Melhorias futuras

* Autenticação com JWT
* Controle de graduação
* Histórico de evolução do aluno
* Dashboard de métricas
* Deploy em cloud
* CI/CD com GitHub Actions

---

# 👨‍💻 Autor

Desenvolvido por Anilton Viganigo Jr.

* GitHub:
  https://github.com/AniltonViganigoJr

---

# 📜 Licença

Este projeto está sob a licença MIT.
