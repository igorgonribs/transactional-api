# Transactional API
Api de transações financeiras.

## Como executar
Primeiro, clone o projeto em sua máquina.

Para executar o projeto execute os seguintes comandos
```
cd transactional-api
docker build -t transaction-api:local .
docker-compose up -d mysql transactional-api
```

## Modelo de dados
O modelo de dados final está apresentado no arquivo ```modelo_dados.drawio```, na pasta ```documentation_files```.
Foi seguido o modelo sugerido na descrição do desafio, com exceção da adição de uma coluna chamada ```operation_signal``` à tabela ```operation_types``` para indicar se a operação request um valor positivo ou negativo.

## Arquitetura
O desenho da arquitetura está apresentado no arquivo ```arquitetura.drawio```, na pasta ```documentation_files```.

A arquitetura foi feita da forma mais simples possível, com apenas um serviço. Há possibilidade de quebrar em serviços menores de acordo com a necessidade, volumetria de cada api, etc.
A API de contas, por exemplo, pode ser interessante isolá-la em um serviço apartado devido à alta volumetria do serviço de transações. Por isso a estrutura interna dos pacotes foi organizada por domínios ao invés de usar a estrutura de pacotes tradicional do java, pois dessa forma fica mais fácil extrair as classes das quais o domínio de contas depende e levá-las a outro serviço.
Podemos discutir essas possibilidades durante a apresentação.

## Endpoints

Para testar a aplicação, importe no postman o arquivo `TransactionalAPI.postman_collection.json`, na pasta ```documentation_files``` e execute as chamadas. 

### Get Account by Id
Rota para buscar conta por id:
```
Request:  
    GET /transactional/api/accounts/{id}

Response
    {
        "id": 1,
        "documentNumber": "12345678900"
    }
```

### Create Account
Rota para cadastrar nova conta:
```
Request:
    POST /transactional/api/accounts
    {
        "documentNumber": "11094463655"
    }

Response:
    Status-Code: 201
    {
        "id": 2,
        "documentNumber": "11094463655"
    }
```

### Create Transaction
Rota para criar uma nova transação:
```
Request:
    POST /transactional/api/transactions
    {
        "accountId": 1,
        "operationTypeId": 1,
        "amount": 10.0
    }

Response:
  Status-Code: 201
    {
        "amount": -10.0,
        "eventDate": "2023-10-27T03:07:21.685951218",
        "operationTypeId": 1,
        "accountId": 1
    }
```

## Cobertura de testes
![alt text](https://github.com/igorgonribs/transactional-api/blob/main/documentation_files/CoverageEvidence.png?raw=true)
