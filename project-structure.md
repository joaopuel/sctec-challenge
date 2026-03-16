# Arquitetura do Projeto SCTEC Challenge

Este projeto adota os princípios da **Clean Architecture** e **Hexagonal Architecture** (Ports and Adapters), garantindo que as regras de negócio sejam completamente independentes de frameworks, bancos de dados e detalhes de infraestrutura.

![The Clean Architecture by Robert C. Martin](https://miro.medium.com/v2/resize:fit:720/format:webp/0*iU9Ks05_GTtGh6zV.jpg)

---

## Visão Geral

A arquitetura é organizada em **quatro camadas** com responsabilidades bem definidas. O sentido das dependências segue uma única direção: de fora para dentro.

```
[api] ──► [application] ──► [domain]
              ▲
[infrastructure] ────────────────────►
```

> `infrastructure` e `api` dependem de `application`. `application` depende de `domain`. `domain` não depende de ninguém.

### Camadas

| Camada           | Responsabilidade Principal                                        |
|------------------|-------------------------------------------------------------------|
| `domain`         | Modelos de negócio puros, contratos (ports) e regras de domínio   |
| `application`    | Orquestração de casos de uso, DTOs e mapeadores                   |
| `infrastructure` | Implementações técnicas: persistência, gateways, mapeadores       |
| `api`            | Camada de apresentação: controllers REST e tratamento de exceções |

---

## Estrutura de Pacotes

```
com.example.sctec_challenge/
  ├── domain/              ← Núcleo do negócio (independente)
  ├── application/         ← Casos de uso e DTOs
  ├── infrastructure/      ← Adaptadores de persistência
  └── api/                 ← Controllers REST
```

---

## Camada: `domain`

Representa o **coração do negócio**. Contém os modelos de domínio puros e as abstrações (ports) que definem os contratos que outras camadas devem implementar.

### Princípios aplicados

- **Dependency Inversion Principle (DIP)**: O domínio define interfaces (ports) que são implementadas por camadas externas.
- **Independência de frameworks**: O domínio não conhece Spring, JPA ou qualquer outro framework.
- **Testabilidade**: Modelos e contratos podem ser testados sem infraestrutura.

---

## Camada: `application`

Orquestra os **casos de uso** e define os **DTOs** (Data Transfer Objects) para comunicação entre camadas. Esta camada implementa as interfaces de caso de uso definidas no domínio.

### Princípios aplicados

- **Single Responsibility Principle (SRP)**: Cada caso de uso tem uma única responsabilidade.
- **Separation of Concerns**: DTOs separam a representação externa dos modelos internos.
- **Validação na entrada**: DTOs usam Bean Validation (`@NotNull`, `@Email`, etc.).

---

## Camada: `infrastructure`

Implementa os **adaptadores técnicos**: persistência com JPA, gateways que se conectam ao banco de dados e mapeadores entre entidades e modelos de domínio. Esta é a camada que contém todos os detalhes de framework.

### Princípios aplicados

- **Adapter Pattern**: Gateways adaptam a interface do repositório JPA para os contratos do domínio.
- **Encapsulamento de detalhes técnicos**: Toda lógica JPA/Hibernate fica isolada aqui.
- **Inversão de dependência**: Infrastructure **implementa** os contratos do domínio.

---

## Camada: `api`

Camada de **apresentação** que expõe endpoints REST para o mundo externo. Recebe requisições HTTP, delega para os casos de uso e retorna respostas padronizadas.

### Princípios aplicados

- **Interface Segregation Principle (ISP)**: Controllers implementam apenas os métodos necessários via `GenericEntityController`.
- **Open/Closed Principle**: Novos endpoints podem ser adicionados estendendo a interface genérica.
- **Separação de concerns**: Controllers apenas coordenam, não executam lógica de negócio.

---

## Benefícios da Arquitetura

### 1. **Testabilidade**
- Cada camada pode ser testada isoladamente.
- Mocks podem ser facilmente criados usando as interfaces (ports).
- Testes unitários não precisam de banco de dados ou Spring Context.

### 2. **Manutenibilidade**
- Mudanças em frameworks ou bibliotecas afetam apenas as camadas externas.
- Regras de negócio estão centralizadas e bem definidas.
- Código é auto-documentado pela estrutura de pacotes.

### 3. **Escalabilidade**
- Novos recursos seguem o mesmo padrão estabelecido.
- Facilita a divisão de trabalho entre desenvolvedores.
- Preparado para evoluir para microserviços (cada domínio já é independente).

### 4. **Independência de Tecnologia**
- O domínio não conhece JPA, Spring ou qualquer framework.
- Trocar o banco de dados requer apenas alterar a camada `infrastructure`.
- Trocar de REST para GraphQL requer apenas alterar a camada `api`.

### 5. **Conformidade com Princípios SOLID**
- **S**ingle Responsibility: Cada classe tem uma única responsabilidade.
- **O**pen/Closed: Aberto para extensão via interfaces, fechado para modificação.
- **L**iskov Substitution: Implementações podem ser substituídas sem quebrar o código.
- **I**nterface Segregation: Interfaces pequenas e específicas (ports).
- **D**ependency Inversion: Dependências apontam para abstrações, não para implementações.

---

## Padrões de Design Utilizados

| Padrão                  | Onde é aplicado                                          |
|-------------------------|----------------------------------------------------------|
| **Hexagonal Architecture** | Estrutura geral: ports (domain) e adapters (infrastructure) |
| **Repository Pattern**  | Repositórios Spring Data isolados na camada infrastructure |
| **Dependency Injection** | Spring Boot injeta dependências via construtor           |
| **DTO Pattern**         | DTOs separam representação externa dos modelos internos  |
| **Mapper Pattern**      | Conversões entre camadas são isoladas em classes Mapper  |
| **Template Method**     | `GenericEntityController` define estrutura de endpoints  |
| **Adapter Pattern**     | Gateways adaptam JPA Repositories para interfaces do domínio |
| **Exception Handler**   | `GlobalExceptionHandler` centraliza tratamento de erros  |

---
## Entidades do Domínio

### Company (Empresa)

Representa uma empresa cadastrada no sistema.

**Atributos:**
- `id`: Identificador único (UUID)
- `name`: Nome da empresa
- `owner`: Responsável pela empresa (relacionamento com Owner)
- `municipality`: Município onde está localizada
- `cnpj`: CNPJ (14 dígitos, único)
- `businessSegment`: Segmento de negócio (enum: TECHNOLOGY, HEALTH, EDUCATION, etc.)
- `email`: Email da empresa
- `phone`: Telefone (11 dígitos)
- `foundationDate`: Data de fundação
- `isActive`: Indica se está ativa

### Owner (Responsável)

Representa o responsável legal por uma ou mais empresas.

**Atributos:**
- `id`: Identificador único (UUID)
- `name`: Nome completo
- `email`: Email
- `phone`: Telefone (11 dígitos)
- `cpf`: CPF (11 dígitos, único)
- `birthDate`: Data de nascimento
- `companies`: Lista de empresas associadas (relacionamento bidirecional)

---

## Tratamento de Exceções

O sistema implementa um tratamento global de exceções através do `GlobalExceptionHandler`:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorCategoryDTO> handleServiceException(ServiceException ex) {
        // Retorna erro customizado com status HTTP apropriado
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Retorna erros de validação Bean Validation
    }
}
```

**Tipos de exceções:**

| Exceção                         | Quando ocorre                           | Status HTTP |
|---------------------------------|-----------------------------------------|-------------|
| `ServiceException`              | Erro de negócio customizado             | Variável    |
| `MethodArgumentNotValidException` | Falha na validação do DTO             | 400         |
| `HttpMessageNotReadableException` | JSON malformado                       | 400         |
| `DataIntegrityViolationException` | Violação de constraint (CNPJ/CPF duplicado) | 409    |

---

## Documentação da API

O projeto usa **Swagger/OpenAPI 3.0** para documentação automática da API REST.

### Acesso à documentação

- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/v3/api-docs`

## Tecnologias Utilizadas

| Categoria           | Tecnologia                | Versão  |
|---------------------|---------------------------|---------|
| Framework           | Spring Boot               | 3.4.5   |
| Linguagem           | Java                      | 21      |
| Persistência        | Spring Data JPA           | 3.4.x   |
| Banco de Dados      | H2 Database (em memória)  | 2.4.240 |
| Validação           | Bean Validation           | 3.0.0   |
| Documentação        | SpringDoc OpenAPI         | 2.7.0   |
| Build Tool          | Maven                     | 3.x     |

---

## Como Executar o Projeto

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6+

### Comandos

```bash
# Compilar o projeto
./mvnw clean install

# Executar a aplicação
./mvnw spring-boot:run
```

### Documentação Swagger
A documentação da API estará disponível em:
- `http://localhost:8080/api/swagger-ui.html` (interface gráfica)
- `http://localhost:8080/api/v3/api-docs` (JSON OpenAPI)

---

## Endpoints Disponíveis

### Company (Empresas)

| Método | Endpoint                    | Descrição                       |
|--------|-----------------------------|---------------------------------|
| POST   | `/api/company`              | Criar uma nova empresa          |
| PUT    | `/api/company`              | Atualizar empresa existente     |
| GET    | `/api/company/{id}`         | Buscar empresa por ID           |
| DELETE | `/api/company/{id}`         | Excluir empresa                 |
| GET    | `/api/company?page=0&size=10` | Listar empresas (paginado)    |
| POST   | `/api/company/all`          | Listar empresas (POST/paginado) |

### Owner (Responsáveis)

| Método | Endpoint                  | Descrição                          |
|--------|---------------------------|------------------------------------|
| POST   | `/api/owner`              | Criar novo responsável             |
| PUT    | `/api/owner`              | Atualizar responsável existente    |
| GET    | `/api/owner/{id}`         | Buscar responsável por ID          |
| DELETE | `/api/owner/{id}`         | Excluir responsável                |
| GET    | `/api/owner?page=0&size=10` | Listar responsáveis (paginado)   |
| POST   | `/api/owner/all`          | Listar responsáveis (POST/paginado)|

---

## Conclusão

Este projeto demonstra a aplicação prática dos princípios de **Clean Architecture** e **Hexagonal Architecture** em uma aplicação Spring Boot. A separação clara de responsabilidades entre as camadas garante:

- ✅ Código testável e manutenível
- ✅ Independência de frameworks e tecnologias
- ✅ Facilidade para adicionar novos recursos
- ✅ Conformidade com princípios SOLID e DDD
- ✅ Documentação clara e auto-explicativa

A arquitetura está preparada para escalar, seja verticalmente (adicionando mais recursos) ou horizontalmente (evoluindo para microserviços).

