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

## Endpoints

### Criar pagamento
POST /api/transacao/pagamento

Exemplo de request:
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

### Cancelar Transação
PATCH /api/transacao/{id}/cancelamento
Exemplo: /api/transacao/4911561891215698189/cancelamento

### Consultar Transação Estornada/Cancelada
GET /api/transacao/consulta/estorno?id=4911561891215698189

### Consultar Transação por ID
GET /api/transacao/consulta/transacao?id=4911561891215698189

### Listar todas as transações
/api/transacao/consulta/todos

## Regra de Negócio
 - O valor da transaçõa deve ser maior que zero.
 - A quantidade de parcelas deve ser maior que zero.
 - Não é permitido cadastrar transações com ID duplicado.
 - Transações podem ser canceladas.
   
## Testes
Foram implementados testes unitários utilizando:
- JUnit
- Mockito
