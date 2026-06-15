# Simulador de Investimentos

Sistema de API REST para simulação de investimentos desenvolvido com **Quarkus**, framework Java supersônico e subatômico.

## 📋 Pré-requisitos

- **Java 25** ou superior
- **Maven 3.8.1** ou superior (ou use o wrapper incluído)

Verificar versões:
```bash
java -version
mvn -version
```

## 🚀 Começando Rápido

### 1. Compilar o projeto

**Linux/Mac:**
```bash
./mvnw clean install
```

**Windows:**
```cmd
mvnw.cmd clean install
```

### 2. Executar em desenvolvimento (com live reload)

**Linux/Mac:**
```bash
./mvnw quarkus:dev
```

**Windows:**
```cmd
mvnw.cmd quarkus:dev
```

A aplicação estará disponível em: `http://localhost:8080`

Swagger UI: `http://localhost:8080/q/swagger-ui/`

Dev UI: `http://localhost:8080/q/dev/`

## 📦 Build para Produção

### Opção 1: JAR com dependências em pasta separada

```bash
./mvnw clean package
```

Executar:
```bash
java -jar target/quarkus-app/quarkus-run.jar
```

### Opção 2: Über-JAR (um único arquivo)

```bash
./mvnw clean package -Dquarkus.package.jar.type=uber-jar
```

Executar:
```bash
java -jar target/simulador-investimentos-1.0.0-SNAPSHOT-runner.jar
```

## 🧪 Testes

```bash
./mvnw test
```

Com cobertura:
```bash
./mvnw clean test jacoco:report
```

Visualizar cobertura em: `target/jacoco-report/index.html`

## 💾 Banco de Dados

- **Tipo:** H2 Database
- **Arquivo:** `./data/h2_data_base.mv.db`
- **Usuário/Senha:** admin/admin

Para resetar:
```bash
rm -rf data/          # Linux/Mac
rmdir /s /q data      # Windows
```

## 📚 Mais Informações

- [Documentação Quarkus](https://quarkus.io/guides/)
- [OpenAPI/Swagger UI](http://localhost:8080/q/swagger-ui/) - Documentação da API

