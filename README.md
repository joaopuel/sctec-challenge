# SCTEC Challenge - Sistema de Gestão de Empreendimentos Catarinenses

## Descrição da Solução

Este projeto apresenta uma **API REST** desenvolvida para gerenciar informações sobre empreendimentos e seus responsáveis no estado de Santa Catarina, atendendo ao desafio prático de software do programa SCTEC - trilha IA para DEVs. A solução implementa um serviço back-end completo que permite o **cadastro, consulta, atualização e remoção** de empresas e responsáveis, seguindo os princípios da **Clean Architecture** e **Hexagonal Architecture**.

### Funcionalidades Implementadas

- **CRUD Completo**: Operações de criação, leitura, atualização e exclusão para empresas e responsáveis
- **Validações**: Garantia de integridade dos dados fornecidos para cadastro e atualização
- **Documentação Interativa**: API documentada com Swagger/OpenAPI
- **Persistência de Dados**: Utilização de banco de dados H2 em memória para prototipação rápida

---

## Tecnologias Utilizadas

| Categoria           | Tecnologia                | Versão  |
|---------------------|---------------------------|---------|
| Framework           | Spring Boot               | 3.4.5   |
| Linguagem           | Java                      | 21      |
| Persistência        | Spring Data JPA           | 3.4.x   |
| Banco de Dados      | H2 Database (em memória)  | 2.4.240 |
| Validação           | Bean Validation           | 3.0.0   |
| Documentação        | SpringDoc OpenAPI         | 2.7.0   |
| Build Tool          | Maven                     |3.9.7+     |

---

## Estrutura do Projeto

Este projeto adota os princípios da **Clean Architecture** e **Hexagonal Architecture**, garantindo que as regras de negócio sejam completamente independentes de frameworks, bancos de dados e detalhes de infraestrutura.

Em termos de organização, o código está estruturado em 4 camadas principais: api, application, domain e infrastructure, cada uma com responsabilidades bem definidas e isoladas.

### Camadas

| Camada           | Responsabilidade Principal                                                                                                                                              |
|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `domain`         | Contém os modelos de domínio puros e as abstrações (ports) que definem os contratos que outras camadas devem implementar.          |
| `application`    | Implementa as regras de negócio e validações específicas dos casos de uso, mas sem depender de frameworks ou detalhes de infraestrutura. |
| `infrastructure` | Implementa os **adaptadores técnicos**: persistência com JPA, gateways que se conectam ao banco de dados e mapeadores entre entidades e modelos de domínio. |
| `api`            | Camada de **apresentação** que expõe endpoints REST para o mundo externo. Recebe requisições HTTP, delega para os casos de uso e retorna respostas padronizadas.  |

### Entidades do Domínio

#### 1. Company (Empresa)

Representa uma empresa cadastrada no sistema.

**Atributos:**
- `id`: Identificador único (UUID)
- `name`: Nome da empresa
- `owner`: Responsável pela empresa (relacionamento com OwnerEntity)
- `municipality`: Município onde está localizada
- `cnpj`: CNPJ (14 dígitos, único)
- `businessSegment`: Segmento de negócio (enum: TECHNOLOGY, COMMERCE, INDUSTRY, etc.)
- `email`: Email de contato da empresa
- `phone`: Telefone de contato da empresa (11 dígitos)
- `foundationDate`: Data de fundação
- `isActive`: Indica o status da empresa (ativa ou inativa)

#### 2. Owner (Responsável)

Representa o responsável legal por uma ou mais empresas.

**Atributos:**
- `id`: Identificador único (UUID)
- `name`: Nome do responsável
- `email`: Email de contato do responsável
- `phone`: Telefone de contato do responsável (11 dígitos)
- `cpf`: CPF (11 dígitos, único)
- `birthDate`: Data de nascimento

---

## Como Executar o Projeto

### Pré-requisitos

- Java 21
- Maven 3.9.7 ou superior

### Comandos

* Primeira opção: com wrapper do Maven (recomendado):
```bash
# Compilar o projeto
mvnw clean install

# Executar a aplicação
mvnw spring-boot:run
```
* Segunda opção: com Maven global:
```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```
*Observação: Assim que o projeto for executado, ele estará disponível em `http://localhost:8080`. Garanta que a porta 8080 esteja livre ou ajuste a propriedade `server.port` em `application.properties`.*

---

## Documentação Swagger
Assim que a aplicação estiver em execução, a documentação dos endpoints estará disponível acessando as seguintes URLs:
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

### Link Para Vídeo de Apresentação

[![Apresentação do Projeto](https://img.youtube.com/vi/SEU_VIDEO_ID_AQUI/0.jpg)](https://www.youtube.com/watch?v=SEU_VIDEO_ID_AQUI)
