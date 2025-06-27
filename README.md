# 📚 API de Cursos e Alunos – AV2

Este projeto é uma API RESTful construída com Spring Boot para gerenciamento de `cursos` e `alunos`, com autenticação via **JWT**. O sistema possui dois tipos de usuários: `user` e `admin`, com permissões distintas de acesso aos endpoints.

---

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
