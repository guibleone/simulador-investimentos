# Simulador de Investimentos

Sistema de API REST para simulação de investimentos desenvolvido com **Quarkus**, framework Java supersônico e subatômico.

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

- **Java 25** ou superior ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.8.1** ou superior ([Download](https://maven.apache.org/download.cgi)) - OU use o wrapper incluído
- **Git** (para clonar o repositório)

Para verificar as versões instaladas, execute:

```bash
java -version
mvn -version
```

## 🚀 Começando Rápido

### 1. Clonar o repositório

```bash
git clone <seu-repositorio-url>
cd simulador-investimentos
```

### 2. Compilar o projeto

**Linux/Mac:**
```bash
./mvnw clean install
```

**Windows:**
```cmd
mvnw.cmd clean install
```

> A compilação pode levar alguns minutos na primeira execução, pois o Maven baixará todas as dependências necessárias.

## 🏃 Executando a Aplicação

### Opção 1: Modo Desenvolvimento (Com Live Reload)

Ideal para desenvolvimento com recarga automática de código:

**Linux/Mac:**
```bash
./mvnw quarkus:dev
```

**Windows:**
```cmd
mvnw.cmd quarkus:dev
```

A aplicação estará disponível em: `http://localhost:8080`

O Quarkus Dev UI estará acessível em: `http://localhost:8080/q/dev/`

**Vantagens:**
- Recarga automática de código (live coding)
- Melhor feedback durante o desenvolvimento
- Logs em tempo real
- Hot reload de testes

### Opção 2: Executar com JAR Compilado (Produção)

#### 2.1 Compilar o projeto

**Linux/Mac:**
```bash
./mvnw clean package
```

**Windows:**
```cmd
mvnw.cmd clean package
```

Este comando gera um JAR executável com todas as dependências incluídas.

#### 2.2 Executar o JAR

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

A aplicação estará disponível em: `http://localhost:8080`

**Estrutura dos arquivos gerados:**
- `target/quarkus-app/quarkus-run.jar` - JAR principal da aplicação
- `target/quarkus-app/lib/` - Diretório com todas as dependências
- `target/quarkus-app/app/` - Aplicação compilada

> ⚠️ **Importante:** A pasta `lib/` é necessária para executar o JAR. Não distribua apenas o JAR sem as dependências.

### Opção 3: Executar como Über-JAR (Single JAR - Recomendado para Distribuição)

Se preferir um único arquivo JAR sem dependências externas:

#### 3.1 Compilar como Über-JAR

**Linux/Mac:**
```bash
./mvnw clean package -Dquarkus.package.jar.type=uber-jar
```

**Windows:**
```cmd
mvnw.cmd clean package -Dquarkus.package.jar.type=uber-jar
```

#### 3.2 Executar o Über-JAR

```bash
java -jar target/simulador-investimentos-1.0.0-SNAPSHOT-runner.jar
```

**Vantagens:**
- Um único arquivo para distribuição
- Sem dependência de pasta `lib/`
- Mais fácil para deployment

## 📊 Acessar a Documentação API

A documentação OpenAPI/Swagger fica disponível em:

- **Swagger UI:** `http://localhost:8080/q/swagger-ui/`
- **Swagger UI (JSON):** `http://localhost:8080/q/swagger-ui/?urls.primaryName=openapi.json`
- **OpenAPI JSON:** `http://localhost:8080/openapi.json`
- **OpenAPI YAML:** `http://localhost:8080/openapi.yaml`

## 🧪 Executar Testes

### Rodar todos os testes

**Linux/Mac:**
```bash
./mvnw test
```

**Windows:**
```cmd
mvnw.cmd test
```

### Rodar apenas uma classe de testes

```bash
./mvnw test -Dtest=SimulacaoResourceTest
```

### Rodar testes com cobertura (JaCoCo)

```bash
./mvnw clean test jacoco:report
```

A cobertura será gerada em: `target/jacoco-report/index.html`

Abra o arquivo em um navegador para visualizar a cobertura detalhada.

## 💾 Banco de Dados

A aplicação utiliza **H2 Database** (banco de dados em arquivo local).

**Configuração:**
- **Tipo:** H2 JDBC
- **Arquivo:** `./data/h2_data_base.mv.db`
- **Usuário:** `admin`
- **Senha:** `admin`

Os dados são persistidos em arquivo, portanto **sobrevivem ao reinício** da aplicação.

**Inicialização:**
- DDL automático: `quarkus.hibernate-orm.schema-management.strategy=drop-and-create`
- Script SQL inicial: `src/main/resources/import.sql`

**Resetar o banco de dados:**

Para limpar todos os dados e começar do zero:

```bash
rm -rf data/                   # Linux/Mac
rmdir /s /q data               # Windows
```

Na próxima execução, o banco será recriado vazio e o script `import.sql` será executado.

## ⚙️ Configuração da Aplicação

As configurações da aplicação estão em `src/main/resources/application.properties`:

### Configurações Padrão

```properties
# REST API
quarkus.rest.path=api

# Porta
quarkus.http.port=8080

# Banco de Dados
quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:file:./data/h2_data_base
quarkus.datasource.username=admin
quarkus.datasource.password=admin

# Hibernate
quarkus.hibernate-orm.schema-management.strategy=drop-and-create

# OpenAPI
quarkus.smallrye-openapi.store-schema-directory=src/main/resources/openapi

# Desabilita dev services (H2 em memória)
quarkus.devservices.enabled=false
```

### Alterar a Porta

**Opção 1:** Editar `application.properties`

```properties
quarkus.http.port=8081
```

**Opção 2:** Passar como parâmetro ao executar

```bash
java -jar target/quarkus-app/quarkus-run.jar -Dquarkus.http.port=8081
```

**Opção 3:** Variável de ambiente

```bash
export QUARKUS_HTTP_PORT=8081
java -jar target/quarkus-app/quarkus-run.jar
```

### Alterar Estratégia de Banco de Dados

Por padrão, o banco é recriado a cada inicialização (`drop-and-create`). Para manter os dados:

```properties
quarkus.hibernate-orm.schema-management.strategy=update
```

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Quarkus** | 3.33.1 | Framework Java cloud-native |
| **Jakarta REST** | - | Especificação RESTful moderna |
| **Hibernate ORM** | - | Mapeamento objeto-relacional |
| **Hibernate Panache** | - | Simplificação do Hibernate |
| **H2 Database** | - | Banco de dados em arquivo |
| **Jackson** | - | Serialização/desserialização JSON |
| **MapStruct** | 1.6.3 | Mapeamento automático entre objetos |
| **Hibernate Validator** | - | Validação de anotações |
| **SmallRye OpenAPI** | - | Geração de documentação OpenAPI/Swagger |
| **JUnit 5** | - | Framework de testes |
| **REST Assured** | - | Testes de API REST |
| **JaCoCo** | - | Análise de cobertura de testes |

## 📁 Estrutura do Projeto

```
simulador-investimentos/
├── src/
│   ├── main/
│   │   ├── java/br/gov/caixa/
│   │   │   ├── dto/                     # Data Transfer Objects
│   │   │   │   ├── request/             # Requisições
│   │   │   │   └── response/            # Respostas
│   │   │   ├── entity/                  # Entidades JPA/Hibernate
│   │   │   ├── resource/                # Endpoints REST (Controllers)
│   │   │   ├── service/                 # Lógica de negócio
│   │   │   ├── mapper/                  # Mapeadores MapStruct
│   │   │   └── comum/                   # Classes utilitárias
│   │   └── resources/
│   │       ├── application.properties   # Configurações da aplicação
│   │       ├── import.sql               # Script SQL inicial
│   │       └── openapi/                 # Schemas OpenAPI gerados
│   ├── test/
│   │   ├── java/br/gov/caixa/
│   │   │   ├── resource/                # Testes de endpoints
│   │   │   ├── service/                 # Testes de serviços
│   │   │   └── mapper/                  # Testes de mapeadores
│   │   └── resources/
│   │       ├── application.properties   # Config de testes
│   │       └── import.sql               # Dados de teste
│   └── docker/                          # Dockerfiles (não usado neste guia)
├── data/                                # Arquivos de banco de dados
│   ├── h2_data_base.mv.db              # Dados do H2
│   └── h2_data_base.trace.db           # Logs do H2
├── target/                              # Arquivos compilados
│   ├── classes/                         # Classes compiladas
│   ├── quarkus-app/                     # JAR executável
│   ├── jacoco-report/                   # Relatório de cobertura
│   └── surefire-reports/                # Relatórios de testes
├── .mvn/                                # Wrapper Maven
├── pom.xml                              # Configuração Maven
├── mvnw                                 # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                             # Maven Wrapper (Windows)
└── README.md                            # Este arquivo
```

## 🐛 Troubleshooting

### ❌ "Command 'mvn' not found"

Execute o Maven via wrapper incluído no projeto:

**Linux/Mac:**
```bash
./mvnw clean install
```

**Windows:**
```cmd
mvnw.cmd clean install
```

Se mesmo assim não funcionar, adicione permissão de execução:

```bash
chmod +x mvnw
./mvnw clean install
```

---

### ❌ Porta 8080 já em uso

Altere a porta da aplicação:

**Opção 1:** Editar `src/main/resources/application.properties`

```properties
quarkus.http.port=8081
```

**Opção 2:** Passar como parâmetro

```bash
./mvnw quarkus:dev -Dquarkus.http.port=8081
```

**Opção 3:** Encontrar e matar o processo usando a porta (Linux/Mac)

```bash
lsof -i :8080
kill -9 <PID>
```

---

### ❌ Erro ao compilar

Limpe o cache do Maven e tente novamente:

```bash
./mvnw clean install -U
```

Se o erro persistir, exclua o cache local:

```bash
rm -rf ~/.m2/repository              # Linux/Mac
rmdir /s %USERPROFILE%\.m2\repository # Windows
./mvnw clean install
```

---

### ❌ Banco de dados corrompido

Delete a pasta de dados e deixe recriar:

**Linux/Mac:**
```bash
rm -rf data/
./mvnw quarkus:dev
```

**Windows:**
```cmd
rmdir /s /q data
mvnw.cmd quarkus:dev
```

---

### ❌ Erro "OutOfMemoryException" ao compilar

Aumente a memória da JVM:

**Linux/Mac:**
```bash
export MAVEN_OPTS="-Xmx1024m"
./mvnw clean package
```

**Windows:**
```cmd
set MAVEN_OPTS=-Xmx1024m
mvnw.cmd clean package
```

---

### ❌ Testes falhando com "Address already in use"

Aplicação anterior não foi encerrada corretamente. Limpe os processos Java:

**Linux/Mac:**
```bash
pkill -f quarkus
```

**Windows:**
```cmd
taskkill /F /IM java.exe
```

---

## 📚 Referências Úteis

- [Documentação Quarkus](https://quarkus.io/guides/)
- [Guia REST no Quarkus](https://quarkus.io/guides/rest)
- [Guia Hibernate ORM](https://quarkus.io/guides/hibernate-orm)
- [Guia Hibernate Panache](https://quarkus.io/guides/hibernate-orm-panache)
- [Guia OpenAPI/Swagger](https://quarkus.io/guides/openapi-swaggerui)
- [Guia de Testes](https://quarkus.io/guides/getting-started-testing)
- [H2 Database Documentation](http://www.h2database.com/html/main.html)

## 🔄 Fluxo de Desenvolvimento

1. **Desenvolvimento:** Use `./mvnw quarkus:dev` para trabalhar com live reload
2. **Testes:** Execute `./mvnw test` frequentemente
3. **Build:** Use `./mvnw clean package` antes de fazer commit
4. **Cobertura:** Execute `./mvnw clean test jacoco:report` regularmente
5. **Produção:** Distribua o Über-JAR gerado com `-Dquarkus.package.jar.type=uber-jar`

## 📝 Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Contribuidores

Este projeto é mantido pelo time de desenvolvimento.

---

**Última atualização:** Junho 2026

