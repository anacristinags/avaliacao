# 📚 API de Cursos e Alunos – AV2

Este projeto é uma API RESTful construída com Spring Boot para gerenciamento de `cursos` e `alunos`, com autenticação via **JWT**. O sistema possui dois tipos de usuários: `user` e `admin`, com permissões distintas de acesso aos endpoints.

---

## 📑 Índice
1. [Sobre o Projeto](#sobre-o-projeto)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Autenticação e Perfis](#autenticação-e-perfis)
4. [Configuração do Ambiente](#configuração-do-ambiente)
5. [Como Rodar o Projeto](#como-rodar-o-projeto)
6. [Testes Automatizados](#testes-automatizados-junit)
7. [Testes de Carga com Apache JMeter](#testes-de-carga-com-apache-jmeter)
8. [Documentação Swagger](#documentação-swagger)
9. [Evidências do Swagger](#evidências-do-swagger-user-e-admin)

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- H2 Database
- Lombok
- Maven
- Swagger UI (Springdoc OpenAPI)
- JUnit + Mockito
- Docker
- Render (deploy)
- Apache JMeter (testes de carga)

---

## 🔐 Autenticação e Perfis

### 🔑 Autenticação
| Método | Rota           | Descrição                |
| ------ | -------------- | ------------------------ |
| POST   | /auth/login    | Realiza o login e gera um token JWT     |
| POST   | /auth/validade | Valida o token JWT |

### 🗄️ Credenciais de Acesso:
| Usuário | Senha    | Permissões              |
| ------- | -------- | ----------------------- |
| `user`  | password | GET, POST (aluno/curso) |
| `admin` | 123456   | GET, POST, PUT, DELETE  |

### 📘 Cursos
| Método | Rota         | Permissões  |
| ------ | ------------ | ----------- |
| GET    | /cursos      | user, admin |
| GET (id)    | /cursos/{id}      | user, admin |
| POST   | /cursos      | user, admin |
| PUT    | /cursos/{id} | admin       |
| DELETE | /cursos/{id} | admin       |

### 👥 Alunos
| Método | Rota         | Permissões  |
| ------ | ------------ | ----------- |
| GET    | /alunos      | user, admin |
| GET  (id)  | /alunos/{id}      | user, admin |
| POST   | /alunos      | user, admin |
| PUT    | /alunos/{id} | admin       |
| DELETE | /alunos/{id} | admin       |



## 🔧 Configuração do Ambiente
### `application.yml`
A configuração principal da aplicação encontra-se em `src/main/resources/application.yml`. Ela define:

- Conexão com banco H2
- Configurações do JWT 
- Caminhos do Swagger
- Configurações Docker
  
```yaml
# application.yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password: 123
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true

jwt:
  secret: umaChaveSecretaMuitoLongaEComplexaParaAssinarTokensJWT
  expiration: 3600000

springdoc:
  swagger-ui:
    path: /swagger-ui.html
    disable-swagger-default-url: true
    operationsSorter: alpha
  api-docs:
    path: /v3/api-docs

management:
  endpoints:
    web:
      exposure:
        include: [health, metrics, prometheus]
  endpoint:
    health:
      show-details: always
```

## 💾 Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/anacristinags/avaliacao
```

### 2. Configurar o banco de dados

Este projeto utiliza o banco de dados **H2**, que é um banco relacional em memória, ideal para testes e desenvolvimento local.

O banco H2 já está configurado no arquivo `src/main/resources/application.yml`

> ⚠️ Observações:
> O banco é volátil: seus dados são apagados quando a aplicação é finalizada.

#### 🧪 Acessando o Console Web do H2
1. Inicie a aplicação 
2. Acesse o console H2 no navegador:
`http://localhost:8080/h2-console`
3. Preencha os dados da conexão com:
   
| Campo         | Valor                |
| ------------- | -------------------- |
| **JDBC URL**  | `jdbc:h2:mem:testdb` |
| **User Name** | `sa`                 |
| **Password**  | `123`  |

4. Clique em Connect

## 🧪 Testes Automatizados (JUnit)
A API possui uma suíte robusta de testes de integração com **JUnit 5** e **MockMvc**, cobrindo os principais fluxos de autenticação e segurança.

### 📂 Local dos testes:
* `src/test/java/com/example/avaliacao/AlunoControllerTest.java`
* `src/test/java/com/example/avaliacao/CursoControllerTest.java`
* `src/test/java/com/example/avaliacao/AuthIntegrationTests.java`

### 🔍 O que é testado
🔸 Testes do AlunoController
| Tipo de Teste                        | Descrição                                                             |
| ------------------------------------ | --------------------------------------------------------------------- |
| Criar aluno                          | Verifica se um novo aluno é criado com sucesso                        |
| Listar alunos                        | Verifica se a listagem de alunos retorna corretamente                 |
| Buscar aluno por ID (encontrado)     | Verifica se um aluno é retornado corretamente ao buscar por ID válido |
| Buscar aluno por ID (não encontrado) | Verifica se retorna 404 ao buscar um aluno inexistente                |
| Atualizar aluno (sucesso)            | Verifica se um aluno é atualizado com sucesso                         |
| Atualizar aluno (não encontrado)     | Verifica se retorna 404 ao tentar atualizar um aluno inexistente      |
| Deletar aluno (sucesso)              | Verifica se a exclusão de aluno funciona corretamente                 |
| Deletar aluno (não encontrado)       | Verifica se retorna 404 ao tentar excluir um aluno inexistente        |

🔸 Testes do CursoController
| Tipo de Teste                        | Descrição                                                        |
| ------------------------------------ | ---------------------------------------------------------------- |
| Criar curso                          | Verifica se um novo curso é criado corretamente                  |
| Listar cursos                        | Verifica se a listagem de cursos retorna os dados esperados      |
| Buscar curso por ID (encontrado)     | Verifica se um curso é retornado ao buscar por um ID válido      |
| Buscar curso por ID (não encontrado) | Verifica se retorna 404 ao buscar um curso inexistente           |
| Atualizar curso (sucesso)            | Verifica se a atualização de um curso funciona corretamente      |
| Atualizar curso (não encontrado)     | Verifica se retorna 404 ao tentar atualizar um curso inexistente |
| Deletar curso (sucesso)              | Verifica se um curso é excluído com sucesso                      |
| Deletar curso (não encontrado)       | Verifica se retorna 404 ao tentar excluir um curso inexistente   |

🔸 Testes de Integração de Autenticação
| Tipo de Teste               | Descrição                                                                  |
| --------------------------- | -------------------------------------------------------------------------- |
| Login válido                | Verifica se o login com credenciais corretas retorna um JWT válido         |
| Login inválido              | Verifica se login com senha incorreta retorna HTTP 401                     |
| Acesso sem token            | Verifica se endpoints protegidos bloqueiam requisições sem autenticação    |
| Acesso com token válido     | Verifica se endpoints respondem corretamente com token válido              |
| Acesso ADMIN com role USER  | Verifica se usuários sem permissão são bloqueados nos endpoints restritos  |
| Acesso ADMIN com role ADMIN | Verifica se usuário com permissão adequada acessa endpoint restrito        |
| Endpoint público            | Garante que endpoints públicos como Swagger podem ser acessados livremente |
| Token expirado              | Simula um token expirado e valida que ele é rejeitado                      |
| Token com role inválida     | Gera um token com role inválida e verifica que o acesso é negado           |
| Claims do token             | Verifica se o JWT contém o `username` e a `role` nos claims                |


##  📈 Testes de Carga com Apache JMeter
Para avaliar o desempenho da API sob múltiplas requisições simultâneas.

Etapas:
Instale o Apache JMeter

Abra o arquivo `Plano de Teste AV2.jmx` (salvo na pasta `testes_JMeter`)

Com a aplicação rodando, clique em Start

Analise os resultados via **Summary Report** e **View Results Tree**
![Image](https://github.com/user-attachments/assets/32ac1f6d-f74d-4115-b712-059c0f0e38ed)
![Image](https://github.com/user-attachments/assets/f38c8c54-d03f-4d4a-a240-24af8096870c)

## 📖 Documentação Swagger
O projeto possui documentação automática com o Swagger UI, gerada via springdoc-openapi.
#### Local: `http://localhost:8080/swagger-ui.html`
#### Render: `https://av2-ana-cristina.onrender.com/swagger-ui/index.html`
![Image](https://github.com/user-attachments/assets/dcadcb80-107b-4c6d-8d54-fab68713ca87)

## 🧾 Evidências do Swagger (User e Admin)
Abaixo estão exemplos de prints do **Swagger UI**, demonstrando o comportamento esperado dos principais endpoints quando acessados por usuários com **diferentes níveis de permissão** (`admin` e `user`).
### 🔐 Loggin como `admin`
#### POST:
![Image](https://github.com/user-attachments/assets/424c6f3b-d647-4022-be46-343be8516681)
#### GET:
![Image](https://github.com/user-attachments/assets/f5f3976a-fb24-4599-981f-91f0bdef9a84)
#### PUT:
![Image](https://github.com/user-attachments/assets/8ab03e5f-8c24-4a4c-aa8d-498a62949987)
#### DELETE:
![Image](https://github.com/user-attachments/assets/4a514201-454e-445e-8725-62ea6a2343d1)

### 🔒 Loggin como `user`
#### POST e GET (**LIBERADO**):
![Image](https://github.com/user-attachments/assets/045b0d9c-9650-4754-bdcd-71d170b907ca)
#### PUT (**Sem Autorização**):
![Image](https://github.com/user-attachments/assets/c852c53b-8a95-4287-8b9b-fff5435aeebe)
#### DELETE (**Sem Autorização**):
![Image](https://github.com/user-attachments/assets/0dcce80b-ed84-41c9-806f-d78a32ecb2de)



