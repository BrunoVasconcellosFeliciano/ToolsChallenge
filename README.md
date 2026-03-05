# API de Pagamentos

Projeto desenvolvido em Java utilizando Spring Boot para simular
uma API de pagamentos com autorização de transações.

## Sobre o projeto

Esta API simula o processamento de transações de pagamento,
permitindo:

- Criar uma nova transação
- Cancelar uma transação
- Consultar transação estornada/cancelada
- Consultar transações por ID
- Listar todas as transações

O projeto foi desenvolvido como parte de um desafio técnico.

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- H2 Database
- JUnit
- Mockito
- Lombok

## Como rodar o projeto

Clone o repositório:

git clone https://github.com/BrunoVasconcellosFeliciano/ToolsChallenge/tree/master

Entre na pasta do projeto:

cd toolschallenge

Execute a aplicação:

mvn spring-boot:run

## Como acessar o banco de dados em memória?
 Após a subida do código, acesse: http://localhost:8080/h2-console
### Configurações do banco
```properties
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:pagamentos
User Name: sa
Password: (vazio)
```
```markdown
O banco é recriado a cada inicialização da aplicação.
```

## Endpoints

### Criar pagamento
<strong>POST</strong> /api/transacao/pagamento

Exemplo de request:
```json
{
    "transacao": {
        "cartao": "5283********8624",
        "id": "4911561891215698189",
        "descricao": {
            "valor": "100",
            "dataHora": "04/03/2026 10:40:00",
            "estabelecimento": "Bruno Shopping"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcela": 1
        }
    }
}
```

### Cancelar Transação
<strong>PATCH</strong> /api/transacao/{id}/cancelamento<br>
Exemplo: /api/transacao/4911561891215698189/cancelamento

### Consultar Transação Estornada/Cancelada
<strong>GET</strong> /api/transacao/consulta/estorno?id=4911561891215698189

### Consultar Transação por ID
<strong>GET</strong> /api/transacao/consulta/transacao?id=4911561891215698189

### Listar todas as transações
<strong>GET</strong> /api/transacao/consulta/todos

## Regra de Negócio
 - O valor da transação deve ser maior que zero.
 - A quantidade de parcelas deve ser maior que zero.
 - Não é permitido cadastrar transações com ID duplicado.
 - Transações podem ser canceladas.
   
## Testes
Foram implementados testes unitários utilizando:
- JUnit
- Mockito
