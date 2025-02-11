# POC Email

Projeto de demonstração para envio de emails usando Java e Jakarta Mail.

## Pré-requisitos

- Java 17 ou superior
- Maven
- Conta Gmail com autenticação de duas etapas ativada
- Senha de aplicativo do Gmail configurada

## Configuração do Gmail

1. Acesse sua conta Gmail
2. Ative a autenticação de duas etapas
3. Gere uma senha de aplicativo:
   - Acesse Configurações da Conta > Segurança
   - Role até "Senhas de app"
   - Gere uma nova senha de aplicativo

## Como executar

1. Clone o repositório:
```bash
git clone [url-do-repositorio]
cd poc-email
```

2. Compile e execute o projeto:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.email.EmailTest"
```

3. Na interface gráfica que será aberta:
   - Digite seu email Gmail
   - Digite a senha de aplicativo do Gmail
   - Digite o email de destino
   - Clique em "Enviar Email de Teste"

## Gerando o Executável

Para gerar o arquivo JAR executável:

```bash
mvn clean package
```

O arquivo executável será gerado em `target/poc-email-1.0-SNAPSHOT.jar`

## Executando o Programa

### Opção 1: Linha de comando
```bash
java -jar target/poc-email-1.0-SNAPSHOT.jar
```

### Opção 2: Interface Gráfica
- Windows: Duplo clique no arquivo JAR
- Linux/Mac: 
  ```bash
  chmod +x target/poc-email-1.0-SNAPSHOT.jar
  double-click no arquivo ou use o comando java -jar
  ```

## Requisitos para Execução
- Java Runtime Environment (JRE) 17 ou superior instalado
- Conexão com a internet

## Observações

- Certifique-se de não compartilhar suas credenciais
- Use sempre senhas de aplicativo, nunca sua senha principal do Gmail
- Verifique a pasta spam caso não encontre o email de teste
