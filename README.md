## Sistema Acadêmico – Trabalho Prático de Banco de Dados e Aplicação Conectada

Aplicação acadêmica desenvolvida com **Spring Boot**, **MySQL** e **JavaScript (frontend simples)** para demonstrar:

- **Modelo de dados relacional** (modelado e normalizado até 3FN);
- **Banco de dados SQL** com script completo de criação e carga de dados;
- **Aplicação conectada** capaz de executar **consultas complexas** (JOINs, agregações, subconsultas etc.) a partir da interface web.

---

### Visão Geral do Domínio (Etapa 1 – Definição do Domínio)

O sistema simula um **ambiente acadêmico** de uma instituição de ensino superior, permitindo controlar:

- **Alunos** e seus dados cadastrais;
- **Professores** e departamentos;
- **Cursos** e **disciplinas**;
- **Turmas** (ofertas de disciplinas em períodos específicos, com professor e turno);
- **Matrículas** de alunos nas turmas (com notas, frequência e situação);
- **Histórico de coordenação** de cursos (qual professor coordenou qual curso e em qual período).

Principais entidades do domínio:

- **Aluno**: representa o estudante, com CPF, nome e data de nascimento;
- **Professor**: docente com CPF, nome e departamento;
- **Disciplina**: componente curricular com nome e carga horária;
- **Curso**: curso de graduação com carga horária total;
- **Turma**: oferta de uma disciplina em um determinado período, turno e horário, ministrada por um professor;
- **Matrícula_Turma**: associação entre aluno e turma (nota, frequência, situação – aprovado, reprovado, em curso);
- **Curso_Disciplina**: relacionamento M:N entre curso e disciplina;
- **Coordenacao**: histórico de coordenação de cursos por professores ao longo do tempo.

---

### Modelagem Conceitual e Lógica (Etapa 2)

O projeto está mapeado com **JPA/Hibernate** (anotações `@Entity`, `@ManyToOne`, `@OneToMany`, `@EmbeddedId` etc.), refletindo o modelo relacional utilizado no MySQL.

- As chaves compostas das tabelas de relacionamento (`matricula_turma`, `curso_disciplina`, `coordenacao`) são modeladas com classes `@Embeddable`.
- Os relacionamentos são explicitados via anotações (`@ManyToOne`, `@OneToMany`, `@JoinColumn`), em consonância com o esquema SQL.

#### Imagens do MER e Modelo Relacional

Substitua os itens abaixo pelos diagramas gerados na ferramenta escolhida (draw.io, MySQL Workbench, Lucidchart, etc.):

- **MER (Modelo Entidade-Relacionamento)**  

<img width="1111" height="765" alt="Image" src="https://github.com/user-attachments/assets/2756e007-50ac-41f0-a10c-a9f7de7e4cb7" />

- **Modelo Relacional (Esquema de Tabelas)**  
 
<img width="1011" height="432" alt="Image" src="https://github.com/user-attachments/assets/7bf240ea-dc3b-457c-bb24-a6160149d630" />

Sugestão de pasta no repositório: `docs/imagens/`.

---

### Implementação do Banco de Dados (Etapa 3)

Scripts SQL localizados em `SistemaAcademico/DBSQL`:

- `Criacao_database.txt` – criação completa do banco de dados:
  - tabelas base (`aluno`, `professor`, `disciplina`, `curso`);
  - tabelas de relacionamento (`curso_disciplina`, `coordenacao`, `turma`, `matricula_turma`);
  - definição de **PKs**, **FKs** e **restrições de integridade**;
  - renomeação de tabelas para nomes em minúsculo ao final do script (padrão do modelo relacional).
- `Inserts_data.txt` – carga de dados de exemplo:
  - no mínimo 10 registros para cada tabela principal;
  - diversidade de situações de matrícula (aprovado, reprovado, em curso);
  - dados suficientes para alimentar consultas complexas.
- `Select_data.txt` – exemplos de consultas SQL no banco:
  - histórico de notas por aluno;
  - professores por curso e disciplina;
  - coordenadores por ano;
  - médias por turma, disciplinas com maior reprovação, alunos regulares, disciplinas sem turma, etc.

> **Nota:** dependendo da configuração do MySQL (sensibilidade a maiúsculas/minúsculas em nomes de tabela), os `INSERTs` podem funcionar tanto com nomes capitalizados (`Aluno`) quanto com minúsculos (`aluno`). Em ambiente sensível a case, ajuste os nomes se necessário.

---

### Arquitetura da Aplicação (Etapa 4)

Tecnologias principais:

- **Backend**: Java 17+, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Lombok;
- **Banco de Dados**: MySQL (suportado via Docker);
- **Frontend**: página estática `index.html` + `app.js` (JavaScript puro) consumindo a API REST;
- **Autenticação**: HTTP Basic (usuário em memória configurado no `SecurityConfig`).

#### Estrutura de Pastas (resumo)

- `SistemaAcademico/` – projeto Spring Boot (backend)
  - `src/main/java/com/projeto/bd/SistemaAcademico/`
    - `models/` – entidades JPA (Aluno, Professor, Curso, Disciplina, Turma, Matrícula, etc.);
    - `DTO/` – objetos de transferência de dados para consultas complexas;
    - `Repositories/` – interfaces Spring Data JPA com consultas personalizadas (`@Query`);
    - `Services/` – regras de negócio e orquestração das consultas;
    - `Controllers/` – endpoints REST (`/api/...`) consumidos pelo frontend;
    - `configs/` – configuração de CORS e segurança (Spring Security).
  - `src/main/resources/application.properties` – configuração de conexão com o banco.
- `SistemaAcademico(Front)/` – frontend simples em HTML/JS
  - `index.html` – página com tela de login, botões e tabela para exibir resultados;
  - `app.js` – código JavaScript responsável pelas chamadas à API e renderização.
- `SistemaAcademico/DBSQL/` – scripts SQL (DDL + inserts + selects).

---

### Funcionalidades da Aplicação Conectada

#### Tela de Login

- Local: `SistemaAcademico(Front)/index.html`.
- Autenticação via **HTTP Basic**:
  - Usuário padrão: `admin`
  - Senha padrão: `senha123`
- O JavaScript (`app.js`) monta o cabeçalho `Authorization: Basic <token>` e testa o login com uma chamada protegida.

#### Telas / Áreas Principais

- **Área de Consultas e Manipulação**:
  - Seletor de ID de aluno para histórico detalhado;
  - Campo de ano para ranking de professores;
  - Campo de texto para busca parcial de disciplina;
  - Botões para consultas complexas (Etapa 5);
  - Botões de listagem básica (CRUD de leitura) para Alunos, Professores, Disciplinas, Cursos e Turmas.

Os resultados são exibidos em uma **tabela dinâmica**, cujo cabeçalho e corpo são montados conforme o retorno da API (objetos ou listas de strings).

---

### Consultas SQL Complexas (Etapa 5)

Abaixo, a lista das consultas complexas exigidas, com indicação do **tipo** e **endpoint** correspondente no backend.

#### 1. Junções múltiplas (JOINs entre 3 ou mais tabelas)

- **Histórico detalhado de um aluno**:
  - Tabelas: `Aluno`, `Matricula_Turma`, `Turma`, `Disciplina`, `Professor`;
  - Tipo: múltiplos `JOINs`;
  - Backend:
    - Repositório: `AlunoRepository.buscarHistoricoDetalhado(...)`;
    - Serviço: `AlunoService.obterHistoricoDetalhado(...)`;
    - Endpoint: `GET /api/alunos/{id}/historico`;
  - Frontend: botão **“1. Histórico Detalhado (JOINs)”**.

#### 2. Consultas agregadas (GROUP BY, HAVING, AVG, COUNT, etc.)

- **Média geral por turma**:
  - Tabelas: `Turma`, `Matricula_Turma`, `Disciplina`;
  - Operações: `AVG`, `COUNT`, `GROUP BY`, `ORDER BY`;
  - Backend:
    - Repositório: `TurmaRepository.calcularMediaGeralPorTurma()`;
    - Serviço: `TurmasService.calcularMediaGeral()`;
    - Endpoint: `GET /api/turmas/media-geral`;
  - Frontend: botão **“3. Média Geral por Turma (Agregada)”**.

- **Ranking de professores por número de turmas**:
  - Tabelas: `Professor`, `Turma`;
  - Operações: `COUNT`, `GROUP BY`, `ORDER BY` (ranking por ano/período);
  - Backend:
    - Repositório: `ProfessorRepository.buscarTopProfessoresPorAno(...)`;
    - Endpoint: `GET /api/professores/ranking?ano=2025`;
  - Frontend: botão **“4. Ranking Profs. por Turma (Agregada/LIMIT)”**.

#### 3. Consultas com subconsultas (subqueries, IN, NOT IN)

- **Alunos com múltiplas matrículas** (mais de uma turma):
  - Subconsulta com `IN` + `GROUP BY` + `HAVING COUNT > 1`;
  - Backend:
    - Repositório: `AlunoRepository.buscarAlunosComMultiplasMatriculas()`;
    - Serviço: `AlunoService.buscarAlunosComMultiplasMatriculas()`;
    - Endpoint: `GET /api/alunos/regulares`;
  - Frontend: botão **“2. Alunos com Múltiplas Matrículas (Subquery IN)”**.

- **Disciplinas sem nenhuma turma**:
  - Subconsulta com `NOT IN` para encontrar disciplinas que não aparecem em `Turma`;
  - Backend:
    - Repositório: `DisciplinaRepository.buscarDisciplinasSemTurmaAberta()`;
    - Serviço: `DisciplinaService.obterDisciplinasSemTurma()`;
    - Endpoint: `GET /api/disciplinas/sem-turma`;
  - Frontend: botão **“5. Disciplinas Sem Turma (Subquery NOT IN)”**.

#### 4. Comparação de strings (LIKE) e consultas de filtragem

- **Busca de disciplinas por nome parcial**:
  - Cláusula `LIKE '%termo%'` em nome de disciplina;
  - Backend:
    - Repositório: `DisciplinaRepository.buscarPorNomeParcial(...)`;
    - Serviço: `DisciplinaService.buscarPorNome(...)`;
    - Endpoint: `GET /api/disciplinas/buscar?termo=...`;
  - Frontend: botão **“6. Buscar por Nome (Strings LIKE)”**.

#### 5. Ordenação e limitação de resultados (ORDER BY, LIMIT / Top N)

- **Ranking de professores (Top N)**:
  - Ordenação por quantidade de turmas (`ORDER BY COUNT(t.id) DESC`);
  - No SQL puro (arquivo `Select_data.txt`) há uso explícito de `LIMIT 1` para pegar o top 1;
  - No JPA, o ranking é retornado em lista já ordenada, simulando comportamento de “Top N”.

Outras consultas complexas adicionais também estão presentes nos arquivos SQL (`Select_data.txt`), reforçando o uso de:

- `HAVING`, `GROUP BY`, `MAX`, `AVG`, `DISTINCT`, `LIKE`, `IN`, `NOT IN`.

---

### Endpoints Principais da API

Prefixo base: `http://localhost:8080/api`

- `GET /alunos` – lista todos os alunos;
- `GET /alunos/{id}/historico` – histórico detalhado de um aluno (JOINs múltiplos);
- `GET /alunos/regulares` – alunos com múltiplas matrículas (subquery + HAVING);
- `POST /alunos/add` – cadastro de aluno;
- `PUT /alunos/{id}` – atualização de aluno.

- `GET /professores` – lista de professores;
- `GET /professores/{id}` – busca de professor por ID;
- `POST /professores/add` – cadastro de professor;
- `PUT /professores/{id}` – atualização de professor;
- `GET /professores/ranking?ano=2025` – ranking de professores por ano/período.

- `GET /cursos` – lista de cursos.

- `GET /disciplinas` – lista de disciplinas;
- `GET /disciplinas/sem-turma` – disciplinas sem nenhuma turma (NOT IN);
- `GET /disciplinas/buscar?termo=...` – busca por nome parcial (LIKE);
- `POST /disciplinas/add` – cadastro de disciplina;
- `PUT /disciplinas/{id}` – atualização de disciplina;
- `DELETE /disciplinas/{id}/dell` – remoção de disciplina.

- `GET /turmas` – lista de turmas;
- `GET /turmas/media-geral` – médias e totais de alunos por turma (consulta agregada).

> Todos os endpoints são protegidos por autenticação HTTP Basic configurada em `SecurityConfig`.

---

### Como Executar o Projeto

#### 1. Subir o banco de dados (MySQL via Docker)

Na pasta `SistemaAcademico/docker` (caso exista `docker-compose.yml` configurado):

```bash
docker compose up -d
```

Certifique-se de que:

- Porta configurada no `application.properties` (`3307`) está livre;
- Usuário, senha e nome do banco (**academico_db**) correspondem ao script de criação.

Depois, execute os scripts em ordem:

1. `Criacao_database.txt`
2. `Inserts_data.txt`

Opcionalmente, use `Select_data.txt` para validar manualmente as consultas.

#### 2. Rodar o backend (Spring Boot)

Na pasta raiz `SistemaAcademico/`:

```bash
mvn spring-boot:run
```

O backend subirá por padrão em `http://localhost:8080`.

#### 3. Rodar o frontend

- Abra o arquivo `SistemaAcademico(Front)/index.html` diretamente no navegador  
  (ou sirva via uma extensão simples de “Live Server”).
- Informe as credenciais:
  - Usuário: `admin`
  - Senha: `senha123`
- Execute as consultas clicando nos botões e observando os resultados na tabela.

---

### Observações Finais para Avaliação

- **Complexidade do banco**: o modelo contempla múltiplos relacionamentos 1:N e N:N, além de chaves compostas, histórico de coordenação e enumerações (`Turno`, `Situacao`).
- **Relevância das operações**: as consultas complexas são diretamente relacionadas ao domínio acadêmico (histórico de aluno, média de turmas, ranking de professores, disciplinas ociosas, alunos com muitas matrículas, etc.).
- **Separação em camadas**: controllers, services, repositories, models e DTOs, evidenciando boas práticas de arquitetura.

Este README foi estruturado para apoiar a **apresentação ao professor**, servindo como guia de navegação do código, do banco de dados e das consultas complexas exigidas no trabalho.
