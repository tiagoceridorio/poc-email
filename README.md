# POC Email

Projeto de demonstração para envio de emails usando Java e Jakarta Mail.

## Pré-requisitos

- Java 17 ou superior
- Maven
- Conta Gmail com autenticação de duas etapas ativada
- Senha de aplicativo do Gmail configurada

## Configuração do Gmail

### Opção 1: Senha de Aplicativo

1. Acesse sua conta Gmail
2. Ative a autenticação de duas etapas
3. Gere uma senha de aplicativo:
   - Acesse Configurações da Conta > Segurança
   - Role até "Senhas de app"
   - Gere uma nova senha de aplicativo

### Opção 2: OAuth2 (Login com Google)

1. Acesse o [Google Cloud Console](https://console.cloud.google.com/)
2. Crie um novo projeto ou selecione um existente
3. Habilite a API do Gmail:
   - No menu lateral, clique em "APIs e Serviços" > "Biblioteca"
   - Procure por "Gmail API" e habilite

4. Configure as credenciais OAuth2:
   - Vá para "APIs e Serviços" > "Credenciais"
   - Clique em "Criar Credenciais" > "ID do Cliente OAuth"
   - Selecione "Aplicativo para Desktop"
   - Dê um nome para o seu cliente OAuth
   - Clique em "Criar"

5. Configure a tela de consentimento:
   - Vá para "APIs e Serviços" > "Tela de consentimento OAuth"
   - Escolha "Externo" e clique em "Criar"
   - Preencha as informações básicas (nome do app, email)
   - Em "Escopos autorizados", adicione "../auth/gmail.send"
   - Adicione seu email como usuário de teste

6. Baixe as credenciais:
   - Volte para "Credenciais"
   - Na seção "IDs do cliente OAuth 2.0", baixe o JSON
   - Renomeie para `credentials.json`
   - Coloque o arquivo em `src/main/resources`

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

3. Na interface gráfica:
   - Selecione o provedor (Gmail)
   - Escolha o tipo de autenticação:
     - "Senha de Aplicativo": use seu email e senha de aplicativo
     - "Login com Google": use seu email e na primeira execução será aberto o navegador para autorização
   - Digite o email de destino
   - Clique em "Enviar Email de Teste"

Observação para OAuth2:
- Na primeira execução com OAuth2, seu navegador abrirá automaticamente
- Faça login com sua conta Google
- Autorize o acesso ao Gmail
- Os tokens serão salvos localmente na pasta "tokens" para usos futuros

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
