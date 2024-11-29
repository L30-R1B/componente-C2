# componente-C2 (protótipo)

Este programa é um gerenciador de operações que integra um banco de dados SQL Server, manipulação de dados em JSON e envio de mensagens para Pub/Sub. Ele fornece funcionalidades para criar tabelas, exportar dados, gerar registros e enviá-los para sistemas de mensagens.

---

## 🛠️ Funcionalidades

### 1. Criar Tabela
Cria uma nova tabela no banco de dados SQL Server.

- **Comando**: `CRIAR_TABELA`
- **Uso**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar CRIAR_TABELA <nome-da-tabela>
  ```
- **Exemplo**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar CRIAR_TABELA Usuarios
  ```

---

### 2. Exportar Tabela para JSON
Exporta os dados de uma tabela para um arquivo JSON.

- **Comando**: `SALVAR_TABELA_COMO_JSON`
- **Uso**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar SALVAR_TABELA_COMO_JSON <nome-da-tabela> <caminho/arquivo.json>
  ```
- **Exemplo**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar SALVAR_TABELA_COMO_JSON Usuarios dados/usuarios.json
  ```

---

### 3. Preencher Tabela com Dados Aleatórios
Gera dados simulados e os insere em uma tabela.

- **Comando**: `PREENCHER_TABELA`
- **Uso**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar PREENCHER_TABELA <nome-da-tabela> <quantidade-dados-gerados>
  ```
- **Exemplo**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar PREENCHER_TABELA Usuarios 100
  ```

---

### 4. Enviar Dados para Pub/Sub
Envia um arquivo JSON para um tópico do Pub/Sub.

- **Comando**: `ENVIAR_DADOS_PARA_PUB-SUB`
- **Uso**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar ENVIAR_DADOS_PARA_PUB-SUB <TOPIC_ID> <CAMINHO_ARQUIVO_JSON>
  ```
- **Exemplo**:  
  ```bash
  java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar ENVIAR_DADOS_PARA_PUB-SUB meu-topico dados/usuarios.json
  ```

---

## 📂 Exemplo de Fluxo

### Inserção e Exportação
1. Crie uma tabela:
   ```bash
   java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar CRIAR_TABELA Produtos
   ```
2. Preencha a tabela com 50 registros:
   ```bash
   java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar PREENCHER_TABELA Produtos 50
   ```
3. Exporte a tabela para JSON:
   ```bash
   java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar SALVAR_TABELA_COMO_JSON Produtos dados/produtos.json
   ```

### Envio para Pub/Sub
4. Envie o arquivo JSON para o Pub/Sub:
   ```bash
   java -jar tunel-dados-1.0-SNAPSHOT-jar-with-dependencies.jar ENVIAR_DADOS_PARA_PUB-SUB meu-topico dados/produtos.json
   ```

---

## ✨ Autor

Desenvolvido por **Leonardo Ribeiro**.  
[GitHub](https://github.com/L30-R1B) | lr82460@gmail.com

---