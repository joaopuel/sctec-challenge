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

### Estrutura

```
domain/
  ├── model/
  │   ├── CompanyModel.java        ← Modelo de domínio puro
  │   ├── OwnerModel.java
  │   └── PageModel.java
  ├── gateway/                      ← Ports (interfaces de saída)
  │   ├── SaveGateway.java
  │   ├── DeleteGateway.java
  │   ├── RetrieveByIdGateway.java
  │   ├── PageableGateway.java
  │   └── ExistsByGateway.java
  ├── usecase/                      ← Ports (interfaces de casos de uso)
  │   ├── CreateUseCase.java
  │   ├── UpdateUseCase.java
  │   ├── DeleteUseCase.java
  │   ├── RetrieveByIdUseCase.java
  │   ├── PageableUseCase.java
  │   └── CreatefNotExistsUseCase.java
  ├── rest/
  │   └── GenericEntityController.java  ← Interface genérica para controllers REST
  └── utils/
      └── GenericMapper.java        ← Interface genérica para mapeamento
```

### O que deve conter

- **Modelos de domínio** (POJOs com `@Data`, `@Builder`): representam as entidades de negócio sem anotações JPA.
- **Ports (Gateways)**: interfaces que definem contratos para acesso a dados e serviços externos.
- **Ports (UseCases)**: interfaces que definem contratos para casos de uso.
- **Interfaces genéricas**: contratos reutilizáveis como `GenericMapper` e `GenericEntityController`.
- **Regras de negócio puras**: lógica que não depende de frameworks.

### O que **não** deve conter

- Anotações de persistência (`@Entity`, `@Table`, etc.).
- Dependências de frameworks Spring (exceto anotações de documentação como Swagger).
- Implementações concretas de casos de uso ou gateways.
- DTOs de comunicação externa.

### Princípios aplicados

- **Dependency Inversion Principle (DIP)**: O domínio define interfaces (ports) que são implementadas por camadas externas.
- **Independência de frameworks**: O domínio não conhece Spring, JPA ou qualquer outro framework.
- **Testabilidade**: Modelos e contratos podem ser testados sem infraestrutura.

### Exemplo: Gateway (Port)

```java
public interface SaveGateway<T> {
    T execute(T model);
}
```

Este contrato é implementado pela camada `infrastructure`, mantendo o domínio desacoplado.

---

## Camada: `application`

Orquestra os **casos de uso** e define os **DTOs** (Data Transfer Objects) para comunicação entre camadas. Esta camada implementa as interfaces de caso de uso definidas no domínio.

### Estrutura

```
application/
  ├── dto/                          ← Objetos de transferência de dados
  │   ├── company/
  │   │   ├── CompanyDTO.java
  │   │   └── CreateCompanyDTO.java
  │   ├── owner/
  │   │   ├── OwnerDTO.java
  │   │   └── CreateOwnerDTO.java
  │   ├── pageable/
  │   │   ├── PageDTO.java
  │   │   └── PaginationDTO.java
  │   └── requests/
  │       └── ErrorCategoryDTO.java
  ├── exception/
  │   └── ServiceException.java     ← Exceções de negócio
  ├── mapper/                       ← Mapeadores de DTO para Model
  │   ├── CompanyDTOMapper.java
  │   ├── OwnerDTOMapper.java
  │   └── CreateOwnerDTOMapper.java
  ├── usecase/                      ← Implementações de casos de uso
  │   ├── company/
  │   │   ├── CreateCompanyUseCaseImpl.java
  │   │   ├── UpdateCompanyUseCaseImpl.java
  │   │   ├── DeleteCompanyUseCaseImpl.java
  │   │   ├── RetrieveByIdCompanyUseCaseImpl.java
  │   │   └── PageableCompanyUseCaseImpl.java
  │   └── owner/
  │       ├── CreateOwnerUseCaseImpl.java
  │       ├── UpdateOwnerUseCaseImpl.java
  │       ├── DeleteOwnerUseCaseImpl.java
  │       ├── RetrieveByIdOwnerUseCaseImpl.java
  │       ├── PageableOwnerUseCaseImpl.java
  │       └── CreateOwnerIfNotExistsUseCaseImpl.java
  └── utils/
      └── CustomMapper.java         ← Configuração do ModelMapper
```

### O que deve conter

- **DTOs**: objetos imutáveis (records ou classes com validação Bean Validation) para transferência de dados.
- **Casos de Uso (UseCaseImpl)**: implementações que orquestram a lógica de negócio usando gateways e mappers.
- **Mappers**: classes que convertem entre DTOs e modelos de domínio.
- **Exceções de negócio**: exceções customizadas com códigos HTTP e mensagens.
- **Utilitários**: ferramentas auxiliares como `CustomMapper` (wrapper do ModelMapper).

### O que **não** deve conter

- Anotações de persistência ou entidades JPA.
- Implementações de controllers ou endpoints REST.
- Acesso direto a repositórios Spring Data.

### Princípios aplicados

- **Single Responsibility Principle (SRP)**: Cada caso de uso tem uma única responsabilidade.
- **Separation of Concerns**: DTOs separam a representação externa dos modelos internos.
- **Validação na entrada**: DTOs usam Bean Validation (`@NotNull`, `@Email`, etc.).

### Exemplo: Caso de Uso

```java
@Service
public class CreateCompanyUseCaseImpl implements CreateUseCase<CreateCompanyDTO, CompanyDTO> {
    
    CustomMapper customMapper;
    GenericMapper<CompanyModel, CompanyDTO> companyDTOMapper;
    SaveGateway<CompanyModel> saveCompanyEntityGateway;
    
    @Override
    public CompanyDTO execute(CreateCompanyDTO dto) {
        var model = customMapper.map(dto, CompanyModel.class);
        model = saveCompanyEntityGateway.execute(model);
        return companyDTOMapper.map(model);
    }
}
```

O caso de uso:
1. Mapeia o DTO de entrada para o modelo de domínio.
2. Usa o gateway (port) para persistir.
3. Mapeia o modelo de volta para DTO de saída.

---

## Camada: `infrastructure`

Implementa os **adaptadores técnicos**: persistência com JPA, gateways que se conectam ao banco de dados e mapeadores entre entidades e modelos de domínio. Esta é a camada que contém todos os detalhes de framework.

### Estrutura

```
infrastructure/
  ├── gateway/                      ← Implementações dos Gateways (Adapters)
  │   ├── company/
  │   │   ├── SaveCompanyEntityGateway.java
  │   │   ├── DeleteCompanyEntityGateway.java
  │   │   ├── RetrieveByIdCompanyEntityGateway.java
  │   │   ├── PageableCompanyEntityGateway.java
  │   │   └── ExistsByIdCompanyEntityGateway.java
  │   └── owner/
  │       ├── SaveOwnerEntityGateway.java
  │       ├── DeleteOwnerEntityGateway.java
  │       ├── RetrieveByIdOwnerEntityGateway.java
  │       ├── PageableOwnerEntityGateway.java
  │       └── ExistsByCpfOwnerEntityGateway.java
  ├── mapper/                       ← Mapeadores de Entity para Model
  │   ├── CompanyModelMapper.java
  │   ├── OwnerModelMapper.java
  │   └── PageCompanyModelMapper.java
  └── persistence/
      ├── entities/                 ← Entidades JPA
      │   ├── CompanyEntity.java
      │   └── OwnerEntity.java
      ├── enums/
      │   └── BusinessSegment.java
      └── repositories/             ← Repositórios Spring Data JPA
          ├── CompanyRepository.java
          └── OwnerRepository.java
```

### O que deve conter

- **Gateways (Adapters)**: implementações concretas dos ports definidos no domínio.
- **Entidades JPA**: classes anotadas com `@Entity`, `@Table`, etc.
- **Repositórios Spring Data**: interfaces que estendem `JpaRepository`.
- **Mapeadores de entidades**: convertem entre `Entity` (JPA) e `Model` (domínio).
- **Enums e tipos específicos de persistência**.

### O que **não** deve conter

- Regras de negócio complexas (pertencem aos casos de uso).
- DTOs de comunicação externa (pertencem à camada `application`).
- Controllers ou endpoints REST.

### Princípios aplicados

- **Adapter Pattern**: Gateways adaptam a interface do repositório JPA para os contratos do domínio.
- **Encapsulamento de detalhes técnicos**: Toda lógica JPA/Hibernate fica isolada aqui.
- **Inversão de dependência**: Infrastructure **implementa** os contratos do domínio.

### Exemplo: Gateway (Adapter)

```java
@Component
public class SaveCompanyEntityGateway implements SaveGateway<CompanyModel> {
    
    CustomMapper customMapper;
    CompanyRepository companyRepository;
    
    @Override
    public CompanyModel execute(CompanyModel model) {
        var entity = customMapper.map(model, CompanyEntity.class);
        entity = companyRepository.save(entity);
        return customMapper.map(entity, CompanyModel.class);
    }
}
```

O gateway:
1. Converte o modelo de domínio em entidade JPA.
2. Usa o repositório Spring Data para persistir.
3. Converte a entidade persistida de volta para modelo de domínio.

---

## Camada: `api`

Camada de **apresentação** que expõe endpoints REST para o mundo externo. Recebe requisições HTTP, delega para os casos de uso e retorna respostas padronizadas.

### Estrutura

```
api/
  └── rest/
      ├── CompanyController.java    ← Controller REST para empresas
      ├── OwnerController.java      ← Controller REST para responsáveis
      └── GlobalExceptionHandler.java  ← Tratamento global de exceções
```

### O que deve conter

- **Controllers REST**: classes anotadas com `@RestController` que implementam `GenericEntityController`.
- **Tratamento de exceções**: classes anotadas com `@ControllerAdvice` para capturar e padronizar erros.
- **Documentação OpenAPI/Swagger**: anotações `@Tag`, `@Operation`, `@ApiResponse`.

### O que **não** deve conter

- Regras de negócio ou lógica de validação complexa (pertencem aos casos de uso).
- Acesso direto a repositórios ou gateways.
- Mapeamento de entidades JPA.

### Princípios aplicados

- **Interface Segregation Principle (ISP)**: Controllers implementam apenas os métodos necessários via `GenericEntityController`.
- **Open/Closed Principle**: Novos endpoints podem ser adicionados estendendo a interface genérica.
- **Separação de concerns**: Controllers apenas coordenam, não executam lógica de negócio.

### Exemplo: Controller

```java
@RestController
@RequestMapping("/company")
@Tag(name = "Empresas", description = "API para gerenciar empresas")
public class CompanyController implements GenericEntityController<CreateCompanyDTO, CompanyDTO, UUID> {
    
    CreateUseCase<CreateCompanyDTO, CompanyDTO> createCompanyUseCaseImpl;
    UpdateUseCase<CompanyDTO> updateCompanyUseCaseImpl;
    // ...outros casos de uso
    
    @Override
    public CreateUseCase<CreateCompanyDTO, CompanyDTO> getCreateUseCase() {
        return createCompanyUseCaseImpl;
    }
    
    // ...outros getters para casos de uso
}
```

O controller:
1. Implementa `GenericEntityController` que já define todos os endpoints CRUD.
2. Injeta os casos de uso via construtor.
3. Retorna os casos de uso através de métodos abstratos da interface.

---

## Fluxo Completo de uma Requisição

Exemplo: criar uma `Company` via `POST /api/company`.

```
┌─────────────────────────────────────────────────────────────────┐
│ HTTP Request: POST /api/company                                  │
│ Body: CreateCompanyDTO                                           │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ CompanyController (api.rest)                                     │
│ • Valida o DTO (Bean Validation)                                 │
│ • Delega para CreateUseCase<CreateCompanyDTO, CompanyDTO>        │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ CreateCompanyUseCaseImpl (application.usecase)                   │
│ • Mapeia CreateCompanyDTO → CompanyModel                         │
│ • Executa regras de negócio (ex: validar owner)                  │
│ • Delega para SaveGateway<CompanyModel>                          │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ SaveCompanyEntityGateway (infrastructure.gateway)                │
│ • Mapeia CompanyModel → CompanyEntity                            │
│ • Persiste via CompanyRepository (Spring Data JPA)               │
│ • Mapeia CompanyEntity → CompanyModel                            │
│ • Retorna CompanyModel                                           │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ CreateCompanyUseCaseImpl (application.usecase)                   │
│ • Recebe CompanyModel persistido                                 │
│ • Mapeia CompanyModel → CompanyDTO                               │
│ • Retorna CompanyDTO                                             │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ CompanyController (api.rest)                                     │
│ • Retorna ResponseEntity<CompanyDTO> com status 200              │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ HTTP Response: 200 OK                                            │
│ Body: CompanyDTO                                                 │
└─────────────────────────────────────────────────────────────────┘
```

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

## Convenções de Nomenclatura

| Tipo de Classe             | Sufixo/Prefixo       | Exemplo                          |
|----------------------------|----------------------|----------------------------------|
| Modelo de Domínio          | `Model`              | `CompanyModel`, `OwnerModel`     |
| Entidade JPA               | `Entity`             | `CompanyEntity`, `OwnerEntity`   |
| DTO de Criação             | `CreateXXXDTO`       | `CreateCompanyDTO`               |
| DTO de Resposta            | `XXXDTO`             | `CompanyDTO`, `OwnerDTO`         |
| Caso de Uso                | `XXXUseCaseImpl`     | `CreateCompanyUseCaseImpl`       |
| Gateway (Adapter)          | `XXXGateway`         | `SaveCompanyEntityGateway`       |
| Mapper de DTO              | `XXXDTOMapper`       | `CompanyDTOMapper`               |
| Mapper de Model            | `XXXModelMapper`     | `CompanyModelMapper`             |
| Controller REST            | `XXXController`      | `CompanyController`              |
| Repositório Spring Data    | `XXXRepository`      | `CompanyRepository`              |

---

## Regras de Dependência

As dependências entre camadas seguem rigorosamente a Clean Architecture:

```
┌─────────────────────────────────────────────────────┐
│                      domain                          │
│  • Não depende de ninguém                            │
│  • Define contratos (interfaces/ports)               │
│  • Contém apenas lógica de negócio pura              │
└─────────────────────────────────────────────────────┘
                         ▲
                         │
        ┌────────────────┴────────────────┐
        │                                  │
┌───────────────────┐           ┌─────────────────────┐
│   application     │           │  infrastructure     │
│ • Depende apenas  │           │ • Depende de domain │
│   do domain       │           │   e application     │
│ • Implementa      │           │ • Implementa ports  │
│   casos de uso    │           │   do domain         │
└───────────────────┘           └─────────────────────┘
         ▲                                ▲
         │                                │
         └────────────┬───────────────────┘
                      │
              ┌───────────────┐
              │      api      │
              │ • Depende de  │
              │   application │
              │ • Expõe REST  │
              └───────────────┘
```

### Regras de ouro:

✅ **Domain** não depende de nada externo (apenas Java, Lombok e bibliotecas utilitárias).

✅ **Application** só depende de **domain**.

✅ **Infrastructure** depende de **domain** e pode usar **application** (para mappers).

✅ **API** depende de **application** e **domain** (para interfaces).

❌ **Domain** NUNCA pode depender de **application**, **infrastructure** ou **api**.

❌ **Application** NUNCA pode depender de **infrastructure** ou **api**.

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

### Configuração

A classe `OpenApiConfig` centraliza as configurações:

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("SCTEC Challenge API")
                .version("1.0.0")
                .description("API RESTful para gerenciar Empresas e Responsáveis"));
    }
}
```

### Acesso à documentação

- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/v3/api-docs`

### Anotações utilizadas

- `@Tag`: Agrupa endpoints por recurso
- `@Operation`: Descreve a operação HTTP
- `@ApiResponse`: Documenta possíveis respostas
- `@Parameter`: Descreve parâmetros de entrada
- `@Schema`: Documenta estrutura de DTOs

---

## Tecnologias Utilizadas

| Categoria           | Tecnologia                | Versão  |
|---------------------|---------------------------|---------|
| Framework           | Spring Boot               | 3.4.5   |
| Linguagem           | Java                      | 21      |
| Persistência        | Spring Data JPA           | 3.4.x   |
| Banco de Dados      | H2 Database (em memória)  | 2.4.240 |
| Mapeamento          | ModelMapper               | 3.1.0   |
| Validação           | Bean Validation           | 3.0.0   |
| Documentação        | SpringDoc OpenAPI         | 2.7.0   |
| Utilitários         | Lombok                    | 1.18.42 |
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

# Acessar a documentação Swagger
# http://localhost:8080/api/swagger-ui.html

# Acessar o console H2
# http://localhost:8080/api/h2-console
# JDBC URL: jdbc:h2:mem:testdb
# Username: sa
# Password: (vazio)
```

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

## Próximos Passos

### Melhorias Sugeridas

1. **Testes Arquiteturais**: Implementar testes com ArchUnit para garantir que as regras de dependência são respeitadas.
2. **Testes de Integração**: Adicionar testes com `@SpringBootTest` e `@DataJpaTest`.
3. **Testes Unitários**: Cobrir casos de uso, mappers e gateways com testes isolados.
4. **Segurança**: Implementar Spring Security com JWT para autenticação e autorização.
5. **Auditoria**: Adicionar `@CreatedDate`, `@LastModifiedDate` nas entidades.
6. **Paginação avançada**: Implementar ordenação customizada e filtros complexos.
7. **Cache**: Adicionar Spring Cache para otimizar consultas frequentes.
8. **Observabilidade**: Integrar Actuator, Micrometer e ferramentas de APM.
9. **Database Migration**: Usar Flyway ou Liquibase para versionamento do schema.
10. **CI/CD**: Configurar pipeline de integração e deploy contínuo.

---

## Referências

- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [OpenAPI Specification](https://swagger.io/specification/)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)

---

## Conclusão

Este projeto demonstra a aplicação prática dos princípios de **Clean Architecture** e **Hexagonal Architecture** em uma aplicação Spring Boot. A separação clara de responsabilidades entre as camadas garante:

- ✅ Código testável e manutenível
- ✅ Independência de frameworks e tecnologias
- ✅ Facilidade para adicionar novos recursos
- ✅ Conformidade com princípios SOLID e DDD
- ✅ Documentação clara e auto-explicativa

A arquitetura está preparada para escalar, seja verticalmente (adicionando mais recursos) ou horizontalmente (evoluindo para microserviços).

